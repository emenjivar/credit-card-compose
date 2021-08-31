package com.emenjivar.creditcard.utils

class InputValidator {

    companion object {
        fun parseNumber(number: String): String? = when {
            checkNumber(number) -> number
            number.isEmpty() -> ""
            else -> null
        }

        fun parseHolderName(name: String): String? = when {
            checkHolderName(name) -> name
            name.isEmpty() -> ""
            else -> null
        }

        fun parseCVC(cvc: String): String? = when {
            checkCVC(cvc) -> cvc
            cvc.isEmpty() -> ""
            else -> null
        }

        private fun checkNumber(number: String): Boolean =
            number.isNotEmpty() && number.length <= 16 && number.last().isDigit()

        private fun checkHolderName(name: String): Boolean =
            name.isNotEmpty() && (name.last().isLetter() || name.last() == ' ')

        private fun checkCVC(cvc: String): Boolean =
            cvc.isNotEmpty() && cvc.last().isDigit() && cvc.length <= 3
    }
}