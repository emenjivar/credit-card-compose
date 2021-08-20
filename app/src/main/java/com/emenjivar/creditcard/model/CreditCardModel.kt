package com.emenjivar.creditcard.model

data class CreditCardModel(
    var bankName: String = "Bank name",
    var number: String,
    var expiration: String = "00/00",
    var holderName: String = "",
    var cvc: String = "000",
    var cardEntity: String = "VISA"
)