package com.bitio.productscomponent.domain.entities.categories

enum class GenderType(val id: Int) {
    MALE(0),
    FEMALE(1),
    BOTH(2);

    companion object {
        fun getFromId(id: Int): GenderType {
            return when (id) {
                0 -> MALE
                1 -> FEMALE
                2 -> BOTH
                else -> BOTH
            }
        }
    }
}