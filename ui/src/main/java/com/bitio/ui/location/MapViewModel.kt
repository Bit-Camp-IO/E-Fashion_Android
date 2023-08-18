package com.bitio.ui.location

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel



class MapViewModel  :ViewModel(){

    var state by mutableStateOf(MapState())
}