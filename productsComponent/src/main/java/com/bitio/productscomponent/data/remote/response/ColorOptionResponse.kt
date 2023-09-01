package com.bitio.productscomponent.data.remote.response

import com.bitio.productscomponent.domain.entities.selectable.ColorOption
import kotlinx.serialization.Serializable

@Serializable
data class ColorOptionResponse(override val hex: String, override val name: String) : ColorOption