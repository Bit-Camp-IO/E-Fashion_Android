package com.bitio.productscomponent.domain.model.selectable

interface SelectableProperty {
    val id:Int
    val name: String
    val options: List<Option>
}