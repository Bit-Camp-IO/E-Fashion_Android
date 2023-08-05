package com.bitio.productscomponent.domain.entities.selectable

interface SelectableProperty<T : Any> {
    val name: String
    val options: List<T>
}