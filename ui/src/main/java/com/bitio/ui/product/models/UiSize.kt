package com.bitio.ui.product.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable

@Stable
data class UiSize(val name: String, val isSelected: MutableState<Boolean>)
