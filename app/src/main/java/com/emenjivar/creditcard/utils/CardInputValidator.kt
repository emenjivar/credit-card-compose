package com.emenjivar.creditcard.utils

class CardInputValidator {

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

        fun parseExpiration(inputExpiration: String, viewModelExpiration: String): String? = when {
            // Before slash (00/) has been typed
            parseExpirationBeforeSlash(inputExpiration) ->
                if(inputExpiration.length == 2) {
                    "$inputExpiration/"
                } else {
                    inputExpiration
                }
            // When slash is deleted from expiration value
            parseExpirationWhenDeleteSlash(inputExpiration, viewModelExpiration) -> inputExpiration.substring(0, 2)
            inputExpiration.isEmpty() -> ""
            else -> null
        }

        private fun checkNumber(number: String): Boolean =
            number.isNotEmpty() && number.length <= 16 && number.last().isDigit()

        private fun checkHolderName(name: String): Boolean =
            name.isNotEmpty() && (name.last().isLetter() || name.last() == ' ')

        private fun checkCVC(cvc: String): Boolean =
            cvc.isNotEmpty() && cvc.last().isDigit() && cvc.length <= 3

        private fun parseExpirationBeforeSlash(expiration: String) =
            expiration.isNotEmpty() && expiration.last().isDigit() && expiration.length <= 5

        private fun parseExpirationWhenDeleteSlash(inputExpiration: String, viewModelExpiration: String) =
            inputExpiration.isNotEmpty() && inputExpiration.last() == '/' && viewModelExpiration.length > inputExpiration.length
    }
}