package com.bitio.productscomponent.domain.entities.selectable

interface SelectedProperty<T:Any> {
    val name: String
    val selectedOption: T
}