package com.bitio.infrastructure.workManager

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bitio.infrastructure.retrofitConfiguration.DURATION_TO_UPDATE_ACCESS_TOKEN_MIN
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.time.Duration
import java.util.concurrent.TimeUnit

object AuthWorkManager : KoinComponent {
    private val constraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
    private val policy = BackoffPolicy.LINEAR
    private val authWorkRequest =
        PeriodicWorkRequestBuilder<AuthWorker>(
            DURATION_TO_UPDATE_ACCESS_TOKEN_MIN,
            TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setBackoffCriteria(policy, Duration.ofSeconds(2L))
            .build()

    fun enqueueWork() {
        WorkManager.getInstance(get()).enqueueUniquePeriodicWork(
            "AuthWork",
            ExistingPeriodicWorkPolicy.KEEP,
            authWorkRequest
        )
    }
}