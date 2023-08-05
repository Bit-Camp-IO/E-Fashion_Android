package com.bitio.productscomponent.domain.entities.categories

interface CategoryFilter {
    val genderCategoryId: Int
    val clothesCategories: Set<Int>
}