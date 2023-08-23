package com.bitio.infrastructure.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bitio.authcomponent.domain.useCases.RefreshAccessTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AuthWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(
    appContext,
    workerParams
), KoinComponent {

    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase by inject()
    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            val result = refreshAccessTokenUseCase()
            return@withContext if (result.isSuccess) Result.success() else Result.retry()
        }


}

