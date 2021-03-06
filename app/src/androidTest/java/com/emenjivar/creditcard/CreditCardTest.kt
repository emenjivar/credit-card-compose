package com.emenjivar.creditcard

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.emenjivar.creditcard.component.CreditCard
import org.junit.Rule
import org.junit.Test

class CreditCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun creditCard_default_values() {
        // Having an empty credit card object
        composeTestRule.setContent {
            CreditCard(
                number = "",
                expiration = "",
                holderName = "",
                cvc = ""
            )
        }

        // Then verify that default values are not empty
        composeTestRule
            .onNodeWithTag("lCardNumber")
            .assertTextEquals("xxxx xxxx xxxx xxxx")

        composeTestRule
            .onNodeWithTag("lExpirationDate")
            .assertTextEquals("00/00")

        composeTestRule
            .onNodeWithTag("lHolderName")
            .assertTextEquals("YOUR NAME")
    }

    @Test
    fun creditCard_complete_number_card() {
        // Having a complete credit card number
        val cardNumber = "4444555566667777"

        composeTestRule.setContent {
            CreditCard(
                number = cardNumber,
                expiration = "",
                holderName = "",
                cvc = ""
            )
        }

        // Then verify the string is separated (using spaces) into 4 blocks
        composeTestRule
            .onNodeWithTag("lCardNumber")
            .assertTextEquals("4444 5555 6666 7777")
    }

    @Test
    fun creditCard_incomplete_number_card_filled_by_default_empty_char() {
        // Having an incomplete (and valid) credit card number
        val cardNumber = "4444555"

        composeTestRule.setContent {
            CreditCard(
                number = cardNumber,
                expiration = "",
                holderName = "",
                cvc = ""
            )
        }

        // Then verify the partial number is separated, filled the rest of string with x
        composeTestRule
            .onNodeWithTag("lCardNumber")
            .assertTextEquals("4444 555x xxxx xxxx")
    }

    @Test
    fun creditCard_incomplete_number_card_filled_using_another_empty_char() {
        // Having an incomplete (and valid) credit card number
        val cardNumber = "444"
        val emptyChar = '*'

        composeTestRule.setContent {
            CreditCard(
                number = cardNumber,
                expiration = "",
                holderName = "",
                cvc = "",
                emptyChar = emptyChar
            )
        }

        // Then verify that the rest of block is filled using *
        composeTestRule
            .onNodeWithTag("lCardNumber")
            .assertTextEquals("444* **** **** ****")
    }

    @Test
    fun creditCard_card_issuer_image_displayed() {
        composeTestRule.setContent {
            CreditCard(
                number = "4",
                expiration = "",
                holderName = "",
                cvc = ""
            )
        }

        composeTestRule
            .onNodeWithTag("iCardEntity")
            .assertExists()
    }

    @Test
    fun creditCard_card_issuer_image_not_displayed() {
        // Having an invalid credit card number
        val badCreditCardNumber = "0000000000000000"

        composeTestRule.setContent {
            CreditCard(
                number = badCreditCardNumber,
                expiration = "",
                holderName = "",
                cvc = ""
            )
        }

        composeTestRule
            .onNodeWithTag("iCardEntity")
            .assertDoesNotExist()
    }

    @Test
    fun creditCardBackSide_displayed_data() {
        // Having a valid credit card number
        val cardNumber = "4000100020003000"
        val cvc = "571"

        composeTestRule.setContent {
            CreditCard(
                number = cardNumber,
                expiration = "",
                holderName = "",
                cvc = cvc,
                flipped = true
            )
        }

        // Then verify the last block of card number is displayed
        composeTestRule
            .onNodeWithTag("cardNumberSignature")
            .assertTextEquals("3000")

        // Also verify the cvc number is masked using *
        composeTestRule
            .onNodeWithTag("cvc")
            .assertTextEquals("***")
    }

    @Test
    fun creditCardBackSide_not_hidden_cvc() {
        // having a normal cvc
        val cvc = "571"

        composeTestRule.setContent {
            CreditCard(
                number = "",
                expiration = "",
                holderName = "",
                cvc = cvc,
                flipped = true,
                showSecurityCode = true
            )
        }

        // Then verify the cvc field is equals that the parameter
        composeTestRule
            .onNodeWithTag("cvc")
            .assertTextEquals(cvc)
    }

    @Test
    fun creditCardBackSide_card_issuer_image_displayed() {
        // Having a valid visa number
        val number = "4000111122223333"

        composeTestRule.setContent {
            CreditCard(
                number = number,
                expiration = "",
                holderName = "",
                cvc = "",
                flipped = true
            )
        }

        composeTestRule
            .onNodeWithTag("cardIssuer")
            .assertExists()
    }

    @Test
    fun creditCardBackSide_card_issuer_image_not_displayed() {
        // Having a valid visa number
        val number = "0000000000000000"

        composeTestRule.setContent {
            CreditCard(
                number = number,
                expiration = "",
                holderName = "",
                cvc = "",
                flipped = true
            )
        }

        composeTestRule
            .onNodeWithTag("cardIssuer")
            .assertDoesNotExist()
    }
}