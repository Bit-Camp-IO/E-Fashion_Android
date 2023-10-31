package com.bitio.productscomponent.domain.model.categories

enum class GenderType(val id: Int) {
    BOTH(0),
    MALE(1),
    FEMALE(2);


    companion object {
        fun getFromId(id: Int): GenderType {
            return when (id) {
                0 -> BOTH
                1 -> MALE
                2 -> FEMALE
                else -> BOTH

            }
        }
    }
}