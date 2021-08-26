package com.emenjivar.creditcard.model

import com.emenjivar.creditcard.R
import org.junit.Assert
import org.junit.Test

class CreditCardModelTest {

    @Test
    fun `logoCardIssuer should return logo_visa image when number has the minimum required visa format`() {
        val model = CreditCardModel(
            number = "4"
        )
        Assert.assertEquals(R.drawable.logo_visa, model.logoCardIssuer)
    }

    @Test
    fun `logoCardIssuer should return logo_mastercard image when number has the minimum required mastercard format`() {
        val model = CreditCardModel(
            number = "52"
        )
        Assert.assertEquals(R.drawable.logo_mastercard, model.logoCardIssuer)
    }

    @Test
    fun `formattedExpiration should return the expiration value with a concat forward slash in the middle of the string`() {
        val model = CreditCardModel(
            expiration = "2522"
        )
        Assert.assertEquals("25/22", model.formattedExpiration)
    }

    @Test
    fun `formattedExpiration should return the expiration value with a concat forward slash an the end of whe string, when expiration has 2 characters`() {
        val model = CreditCardModel(
            expiration = "25"
        )
        Assert.assertEquals("25/", model.formattedExpiration)
    }

    @Test
    fun `formattedExpiration should return an empty string when expiration value is empty`() {
        val model = CreditCardModel(
            expiration = ""
        )
        Assert.assertEquals("", model.formattedExpiration)
    }

    @Test
    fun `formattedExpiration should return a default value when expiration value has not been set`() {
        val model = CreditCardModel()
        Assert.assertEquals("00/00", model.formattedExpiration)
    }
}