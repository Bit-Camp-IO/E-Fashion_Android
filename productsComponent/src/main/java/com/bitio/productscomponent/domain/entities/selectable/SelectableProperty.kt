package com.bitio.productscomponent.domain.entities.selectable

interface SelectableProperty {
    val id:Int
    val name: String
    val options: List<Option>
}