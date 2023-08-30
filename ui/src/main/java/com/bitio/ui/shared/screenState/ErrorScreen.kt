package com.bitio.ui.shared.screenState

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorScreen( message:String){
    Box(modifier = Modifier.fillMaxSize().background(Color.Red)){
        Text(message)
    }

}