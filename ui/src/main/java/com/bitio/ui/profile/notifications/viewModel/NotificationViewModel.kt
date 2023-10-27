package com.bitio.ui.profile.notifications.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitio.notificationscomponent.domain.usecase.AddDeviceTokenToNotificationUseCase
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NotificationViewModel(
    private val addDeviceTokenToNotificationUseCase: AddDeviceTokenToNotificationUseCase
) : ViewModel(){

    fun addDeviceTokenToNotification(deviceToken:String){
        viewModelScope.launch {
            addDeviceTokenToNotificationUseCase(deviceToken)
        }
    }
}