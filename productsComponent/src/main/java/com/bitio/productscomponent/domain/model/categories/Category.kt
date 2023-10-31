package com.bitio.productscomponent.domain.model.categories


interface Category {
    val id: String
    val name: String
    val image: String
    val genderType:GenderType
}
