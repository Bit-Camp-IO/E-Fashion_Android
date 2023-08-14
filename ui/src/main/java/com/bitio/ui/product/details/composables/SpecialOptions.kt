package com.bitio.ui.product.details.composables

enum class SpecialOptions(val id: Int) {
    COLOR(1),
    IMAGE(2);

    companion object {
        fun isSpecialSelectable(selectableId: Int) =
            SpecialOptions.values().any { it.id == selectableId }

        fun getCorrespondingSpecialSelectable(selectableId: Int): SpecialOptions {
            return SpecialOptions.values().first { it.id == selectableId }
        }
    }

}