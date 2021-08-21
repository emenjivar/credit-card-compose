package com.emenjivar.creditcard.utils

import com.emenjivar.creditcard.model.CardIssuer

/**
 * identify the issuer of credit card using the following table
 * https://en.wikipedia.org/wiki/Payment_card_number#Issuer_identification_number_(IIN)
 */
class CardIssuerFinder {

    companion object {
        fun detect(number: String): CardIssuer = when {
            isVisa(number) -> CardIssuer.VISA
            isMastercard(number) -> CardIssuer.MASTERCARD
            isAmericanExpress(number) -> CardIssuer.AMERICAN_EXPRESS
            else -> CardIssuer.OTHER
        }

        private fun isVisa(number: String) = number.isNotEmpty() && number.first() == '4'

        /**
         * from 51 to 55, until excludes 56
         * */
        private fun isMastercard(number: String) = number.length >= 2 && number.substring(0, 2).toIntOrNull() in 51 until 56

        /**
         * from 34 to 37, until excludes 38
         * */
        private fun isAmericanExpress(number: String) = number.length >= 2 && number.substring(0, 2).toIntOrNull() in 34 until 38
    }

}