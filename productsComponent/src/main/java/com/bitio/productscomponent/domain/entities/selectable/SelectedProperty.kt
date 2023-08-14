package com.bitio.productscomponent.domain.entities.selectable

interface SelectedProperty {
    val id: Int
    val name: String
    val selectedOption: Option
}