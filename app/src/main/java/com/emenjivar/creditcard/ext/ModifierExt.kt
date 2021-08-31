package com.emenjivar.creditcard.ext

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layout

/**
 * Provides rectangular size to the card
 * This percent is calculated using the real size of a credit card: 85.6mm x 53.98mm
 * Height layout is 63.06% of width
 */
fun Modifier.dynamicCardHeight() = this.then(
    layout { measurable, constraints ->
        var placeable: Placeable

        measurable.measure(constraints).apply {
            placeable = measurable.measure(
                constraints.copy(
                    minWidth = width,
                    maxWidth = width,
                    minHeight = (width * 0.6306).toInt(),
                    maxHeight = (width * 0.6306).toInt()
                )
            )
        }

        layout(
            width = placeable.width,
            height = placeable.height
        ) {
            placeable.place(
                x = 0,
                y = 0,
                zIndex = 0f
            )
        }
    }
)