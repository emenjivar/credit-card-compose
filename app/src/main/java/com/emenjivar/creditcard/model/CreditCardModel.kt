package com.emenjivar.creditcard.model

import com.emenjivar.creditcard.R
import com.emenjivar.creditcard.utils.CardIssuerFinder

data class CreditCardModel(
    var bankName: String = "Bank name",
    var number: String,
    var expiration: String = "00/00",
    var holderName: String = "",
    var cvc: String = "000",
    var cardEntity: String = "VISA"
) {
    val logoCardIssuer = when(CardIssuerFinder.detect(number)) {
        CardIssuer.VISA -> R.drawable.logo_visa
        CardIssuer.MASTERCARD -> R.drawable.logo_mastercard
        CardIssuer.AMERICAN_EXPRESS -> R.drawable.ic_baseline_credit_card_24
        CardIssuer.OTHER -> R.drawable.ic_baseline_credit_card_24
    }
}