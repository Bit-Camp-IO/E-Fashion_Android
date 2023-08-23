package com.bitio.productscomponent.domain.entities.categories


interface Category {
    val id: String
    val name: String
    val image: String
    val gender:GenderType
}
