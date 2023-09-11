package com.bitio.ui.shared

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState


class GenericPagingSource<T : Any>(
    val dataProvider: suspend (Int) -> Result<List<T>>,
) : PagingSource<Int, T>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, T> {
        val nextPageNumber = params.key ?: 1
        val result = dataProvider(nextPageNumber)

        return result.fold({
            LoadResult.Page(
                data = it,
                prevKey = null,
                nextKey =if(it.size<20) null else nextPageNumber + 1
            )
        }) { LoadResult.Error(it) }

    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}