package com.emenjivar.creditcard

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.emenjivar.creditcard.component.CreditCardForm
import com.emenjivar.creditcard.viewmodel.CreditCardViewModel
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class CreditCardFormTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun creditCardForm_load_values(){
        // Given a non-empty viewModel
        val viewModel = CreditCardViewModel()
        viewModel.number = "0123456789101112"
        viewModel.name = "carlos menjivar"
        viewModel.expiration = "09/22"
        viewModel.cvc = "012"

        composeTestRule.setContent {
            CreditCardForm(viewModel = viewModel)
        }

        // Then verify the values are loaded on the editTexts
        composeTestRule
            .onNodeWithText("0123456789101112")
            .assertExists()

        composeTestRule
            .onNodeWithText("carlos menjivar")
            .assertExists()

        composeTestRule
            .onNodeWithText("09/22")
            .assertExists()

        composeTestRule
            .onNodeWithText("012")
            .assertExists()
    }

    @Test
    fun creditCardForm_verify_focus_order() {
        // Given and empty viewModel
        val viewModel = CreditCardViewModel()

        composeTestRule.setContent {
            CreditCardForm(viewModel = viewModel)
        }

        // Then verify the focus order
        composeTestRule
            .onNodeWithTag("tCardNumber")
            .performImeAction()

        composeTestRule
            .onNodeWithTag("tCardHolder")
            .assertIsFocused()
            .performImeAction()

        composeTestRule
            .onNodeWithTag("tExpiration")
            .assertIsFocused()
            .performImeAction()

        composeTestRule
            .onNodeWithTag("tSecurityCode")
            .assertIsFocused()
    }

    @Test
    fun creditCardForm_delete_text_using_right_icon() {
        // Given an non-empty viewModel
        val viewModel = CreditCardViewModel()
        viewModel.number = "0000111122223333"
        viewModel.name = "carlos menjivar"
        viewModel.expiration = "09/22"
        viewModel.cvc = "123"

        composeTestRule.setContent {
            CreditCardForm(viewModel = viewModel)
        }

        // When 'x' icon is press on every textField
        composeTestRule
            .onNodeWithTag("tCardNumber")
            .onChildren()
            .filterToOne(hasTestTag("deleteIconContainer"))
            .performClick()

        composeTestRule
            .onNodeWithTag("tCardHolder")
            .onChildren()
            .filterToOne(hasTestTag("deleteIconContainer"))
            .performClick()

        composeTestRule
            .onNodeWithTag("tExpiration")
            .onChildren()
            .filterToOne(hasTestTag("deleteIconContainer"))
            .performClick()

        composeTestRule
            .onNodeWithTag("tSecurityCode")
            .onChildren()
            .filterToOne(hasTestTag("deleteIconContainer"))
            .performClick()

        // Then verify the values of the viewModel are empty
        Assert.assertEquals("", viewModel.number)
        Assert.assertEquals("", viewModel.name)
        Assert.assertEquals("", viewModel.expiration)
        Assert.assertEquals("", viewModel.cvc)
    }

    @Test
    fun creditCardForm_card_number_max_length() {
        // Given a simple viewModel
        val viewModel = CreditCardViewModel()

        composeTestRule.setContent {
            CreditCardForm(viewModel = viewModel)
        }

        // When large card number is the input
        composeTestRule
            .onNodeWithTag("tCardNumber")
            .performTextInput("00001111222233334444")

        // Then verify the viewModel number ignore chars after 16
        Assert.assertEquals("0000111122223333", viewModel.number)

        // And check the focus of the next input is requested automatically
        composeTestRule
            .onNodeWithTag("tCardHolder")
            .assertIsFocused()
    }

    @Test
    fun creditCardForm_cvc_is_focus_after_filled_expiration() {
        // Given an empty viewModel
        val viewModel = CreditCardViewModel()

        composeTestRule.setContent {
            CreditCardForm(viewModel = viewModel)
        }

        // When user fills cvc field
        composeTestRule
            .onNodeWithTag("tExpiration")
            .performTextInput("2223")

        // Then verify the cvc field is focused
        composeTestRule
            .onNodeWithTag("tSecurityCode")
            .assertIsFocused()
    }

    @Test
    fun creditCardForm_cvc_max_length() {
        // Given an empty viewModel
        val viewModel = CreditCardViewModel()

        composeTestRule.setContent {
            CreditCardForm(viewModel = viewModel)
        }

        // When user fills cvc field with more than 3 char
        composeTestRule
            .onNodeWithTag("tSecurityCode")
            .performTextInput("123")

        // And fills cvc again, with extra chars
        composeTestRule
            .onNodeWithTag("tSecurityCode")
            .performTextInput("456")

        // Then verify the viewModel cvc length is 3
        Assert.assertEquals("123", viewModel.cvc)
    }
}