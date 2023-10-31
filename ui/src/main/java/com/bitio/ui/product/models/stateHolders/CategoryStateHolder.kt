package com.bitio.ui.product.models.stateHolders

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.bitio.productscomponent.domain.model.categories.GenderType
import com.bitio.productscomponent.domain.useCase.GetCategoryByGenderUseCase
import com.bitio.ui.product.models.UiCategory
import com.bitio.ui.product.models.toUiCategory
import com.bitio.ui.shared.screenState.UiDataState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs


@SuppressLint("MutableCollectionMutableState")
class CategoryStateHolder(
    private val coroutineScope: CoroutineScope,
    private val getCategoryByGenderUseCase: GetCategoryByGenderUseCase,
    private val updateUiDataState:suspend (UiDataState) -> Unit
) {
    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }
    var gendersState: List<MutableState<Boolean>> = List(2) { mutableStateOf(true) }
        private set
    private var selectedGenderType = GenderType.BOTH


    @Stable
    val categoryFlow = MutableStateFlow<List<UiCategory>>(listOf())

    private val selectedCategoriesIds = HashSet<String>()

    init {
        updateCategories()
    }

    fun getSelectedCategories(): List<String> = selectedCategoriesIds.toList()
    fun changeGenderState(genderIndex: Int) {

        if (!gendersState.contains(mutableStateOf(true))) {
            gendersState[abs(genderIndex - 1)].value = true
        }
        selectedGenderType = when {
            gendersState[0].value && gendersState[1].value -> GenderType.BOTH
            gendersState[0].value -> GenderType.MALE
            else -> GenderType.FEMALE
        }
        selectedCategoriesIds.clear()
        updateCategories()
    }

    private fun updateCategories() {
        coroutineScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
            val result = getCategoryByGenderUseCase(selectedGenderType)
            result.onSuccess {
                val uiCategories = it.map { it.toUiCategory(false, ::onCategoryClicked) }
                withContext(Dispatchers.Main) {
                    categoryFlow.value = uiCategories
                    updateUiDataState(UiDataState.Success)
                }

            }
            result.onFailure { updateUiDataState(UiDataState.Error(it.message)) }
        }


    }

    private fun onCategoryClicked(categoryId: String) {
        if (selectedCategoriesIds.contains(categoryId))
            selectedCategoriesIds.remove(categoryId) else selectedCategoriesIds.add(categoryId)

    }

}