package com.bitio.productscomponent.domain.model.selectable

interface SelectedProperty {
    val id: Int
    val name: String
    val selectedOption: Option
}