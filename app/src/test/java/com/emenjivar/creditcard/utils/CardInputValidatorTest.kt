package com.emenjivar.creditcard.utils

import org.junit.Assert
import org.junit.Test

class CardInputValidatorTest {

    @Test
    fun `parseNumber when input is valid, should return the same input`() {
        // Given a valid credit card number
        val creditCardNumber = "1234"

        // Then verify the output is the same as the input
        Assert.assertEquals(creditCardNumber, CardInputValidator.parseNumber(creditCardNumber))
    }

    @Test
    fun `parseNumber when input is empty, should return an empty string`() {
        // Given an empty card number
        val creditCardNumber = ""

        // Then verify the output is an empty string
        Assert.assertEquals("", CardInputValidator.parseNumber(creditCardNumber))
    }

    @Test
    fun `parseNumber when input is invalid, should return null`() {
        // Given an invalid credit card number
        val creditCardNumber = "1234a"

        // Then verify that the output is null
        Assert.assertNull(CardInputValidator.parseNumber(creditCardNumber))
    }

    @Test
    fun `parseHolderName when input is valid, should return the same input`() {
        // Given a valid holder name with letter and spaces
        val holderName = "carlos menjivar"

        // Then verify the output is the same as the input
        Assert.assertEquals(holderName, CardInputValidator.parseHolderName(holderName))
    }

    @Test
    fun `parseHolderName when input is empty, should return an empty string`() {
        // Given an empty holder name
        val holderName = ""

        // Then verify the output is an empty string
        Assert.assertEquals("", CardInputValidator.parseHolderName(holderName))
    }

    @Test
    fun `parseHolderName when input is invalid, should return null`() {
        // Given an invalid holder name
        val holderName = "carlo$"

        // Then verify that the output is null
        Assert.assertNull(CardInputValidator.parseHolderName(holderName))
    }

    @Test
    fun `parseCVC when input is valid, should return the same input`() {
        // Given a valid CVC
        val cvc = "192"

        // Then verify the output is the same as the input
        Assert.assertEquals(cvc, CardInputValidator.parseCVC(cvc))
    }

    @Test
    fun `parseCVC when input is empty, should return an empty string`() {
        // Given an empty CVC
        val cvc = ""

        // Then verify the output is an empty string
        Assert.assertEquals("", CardInputValidator.parseCVC(cvc))
    }

    @Test
    fun `parseCVC when input is invalid, should return null`() {
        // Given an invalid cvc
        val cvc = "12a"

        // Then verify that the output is null
        Assert.assertNull(CardInputValidator.parseCVC(cvc))
    }

    @Test
    fun `parseExpiration when the first valid character is typed, should return the same input`() {
        // Given a valid and incomplete expiration
        val expiration = "0"

        // Then verify that the output is the same as the input
        Assert.assertEquals(expiration, CardInputValidator.parseExpiration(expiration, expiration))
    }

    @Test
    fun `parseExpiration when second character is typed, should concat slash character on the output`() {
        // Given a valid and incomplete expiration
        val expiration = "08"

        // Then verify that the output is the input string with "/" concat
        Assert.assertEquals("08/", CardInputValidator.parseExpiration(expiration, expiration))
    }

    @Test
    fun `parseExpiration when third character is typed, should return the same output`() {
        // Given a valid and incomplete expiration, with / character
        val expiration = "08/2"

        // Then verify that the output is the same as the input
        Assert.assertEquals("08/2", CardInputValidator.parseExpiration(expiration, expiration))
    }

    @Test
    fun `parseExpiration when last fourth char is typed, should return the same output`() {
        // Given a complete expiration
        val expiration = "08/22"

        // Then verify the output is the same as the input
        Assert.assertEquals("08/22", CardInputValidator.parseExpiration(expiration, expiration))
    }

    @Test
    fun `parseExpiration when character after slash is removed, should return output without slash`() {
        // Given a non-complete expiration from input
        val expirationFromInput = "08/"
        val expirationFromViewModel = "08/2" // this value has not been updated from inputText

        // Then verify that she slash is removed
        Assert.assertEquals("08", CardInputValidator.parseExpiration(expirationFromInput, expirationFromViewModel))
    }
}