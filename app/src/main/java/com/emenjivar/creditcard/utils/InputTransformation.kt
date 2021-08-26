package com.emenjivar.creditcard.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class InputTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        return expirationFilter(text)
    }

    /**
     * Convert the input of expiration field to mm/yy
     * The transformation is just inside of the input, the actual value doesn't change
     */
    private fun expirationFilter(text: AnnotatedString): TransformedText {
        val trimmed = if(text.text.length >= 4) text.text.substring(0..3) else text.text
        var out = ""

        for(i in trimmed.indices) {
            out += trimmed[i]
            if(i == 1) out += "/" // Adding slash after second character
        }

        val offsetTranslator = object: OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if(offset <= 1) return offset // From 0 to 1, offset doesn't change
                if(offset <= 4) return offset + 1 // From 2 to 4, offset take into account the added slash
                return 5
            }

            override fun transformedToOriginal(offset: Int): Int {
                if(offset <= 2) return offset
                if(offset <= 5) return offset + 1
                return 4
            }
        }

        return TransformedText(
            AnnotatedString(out),
            offsetTranslator
        )
    }
}