package com.emenjivar.creditcard.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Apply mask on edittext
 */
class InputTransformation(private val fieldType: FieldType) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText = when(fieldType) {
        FieldType.EXPIRATION -> expirationFilter(text)
        FieldType.CARD_NUMBER -> cardNumberFilter(text)
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
                if(offset <= 5) return offset - 1
                return 4
            }
        }

        return TransformedText(
            AnnotatedString(out),
            offsetTranslator
        )
    }

    /**
     * Convert the input of cardNumber field to "xxxx xxxx xxxx xxxx" format
     */
    private fun cardNumberFilter(text: AnnotatedString): TransformedText {
        val trimmer = if(text.text.length >= 16) text.text.substring(0..15) else text.text
        var out = ""

        for(i in trimmer.indices) {
            out += trimmer[i]
            if(i == 3) out += " "
            if(i == 7) out += " "
            if(i == 11) out += " "
        }

        val offsetTranslator = object: OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if(offset <= 3) return offset
                if(offset <= 7) return offset + 1
                if(offset <= 11) return offset + 2
                if(offset <= 16) return offset + 3
                return 19
            }

            override fun transformedToOriginal(offset: Int): Int {
                if(offset <= 4) return offset
                if(offset <= 8) return offset - 1
                if(offset <= 12) return offset - 2
                if(offset <= 17) return offset - 3
                return 16
            }
        }

        return TransformedText(
            AnnotatedString(out),
            offsetTranslator
        )
    }
}