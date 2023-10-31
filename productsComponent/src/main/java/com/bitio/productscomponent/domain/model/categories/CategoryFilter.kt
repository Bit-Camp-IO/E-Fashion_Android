package com.bitio.productscomponent.domain.model.categories

interface CategoryFilter {
    val genderCategoryId: Int
    val clothesCategories: Set<Int>
}