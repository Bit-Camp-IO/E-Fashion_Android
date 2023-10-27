package com.bitio.ui.profile.notifications.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.notificationscomponent.domain.usecase.AddDeviceTokenToNotificationUseCase
import com.bitio.utils.TAG_APP
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NotificationViewModel(
    private val addDeviceTokenToNotificationUseCase: AddDeviceTokenToNotificationUseCase
) : ViewModel() {

    fun addDeviceTokenToNotification(deviceToken: String) {
        viewModelScope.launch {
            val result = addDeviceTokenToNotificationUseCase(deviceToken)
            result.onSuccess {
                Log.d(TAG_APP, "initialNotification onSuccess: $it")
            }
            result.onFailure {
                Log.d(TAG_APP, "initialNotification onFailure: $it")
            }
        }
    }
}