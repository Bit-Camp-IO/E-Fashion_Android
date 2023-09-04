package com.bitio.ui.profile.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PermissionViewModel : ViewModel() {
    val visiblePermissionDialogQueue = mutableStateOf("")

    fun dismissDialog() {
        visiblePermissionDialogQueue.value = ""
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && visiblePermissionDialogQueue.value != permission) {
            visiblePermissionDialogQueue.value = permission
        }
    }
}