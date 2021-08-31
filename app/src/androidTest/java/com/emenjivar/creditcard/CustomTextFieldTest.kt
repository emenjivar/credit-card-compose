package com.emenjivar.creditcard

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.emenjivar.creditcard.component.CustomTextField
import com.emenjivar.creditcard.component.CustomTextFieldDeleteIcon
import org.junit.Rule
import org.junit.Test

class CustomTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun customTextFieldDeleteIcon_rendered_container() {
        // Having a non-empty string
        val value = "abcd"

        composeTestRule.setContent {
            CustomTextFieldDeleteIcon(
                value = value,
                onClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag("deleteIconContainer")
            .assertExists()


    }

    @Test
    fun customTextFieldDeleteIcon_not_rendered_container() {
        // Having an empty string
        val value = ""

        composeTestRule.setContent {
            CustomTextFieldDeleteIcon(
                value = value,
                onClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag("deleteIconContainer")
            .assertDoesNotExist()
    }

    @Test
    fun customTextField_loaded_values() {
        // Having non-empty values
        val value = "test value"
        val label = "test field"

        composeTestRule.setContent {
            CustomTextField(
                modifier = Modifier,
                value = value,
                label = label
            )
        }

        composeTestRule
            .onNodeWithText(value)
            .assertExists()

        composeTestRule
            .onNodeWithText(label)
            .assertExists()
    }

    @ExperimentalTestApi
    @Test
    fun customTextField_next_focus_requested() {
        // Having 3 focuses
        val focusOne = FocusRequester()
        val focusTwo = FocusRequester()
        val focusThree = FocusRequester()

        composeTestRule.setContent {
            Column {
                CustomTextField(
                    modifier = Modifier
                        .focusRequester(focusOne)
                        .testTag("focusOne"),
                    value = "value one",
                    label = "label one",
                    nextFocus = focusTwo
                )
                CustomTextField(
                    modifier = Modifier
                        .focusRequester(focusTwo)
                        .testTag("focusTwo"),
                    value = "value two",
                    label = "label two",
                    nextFocus = focusThree
                )
                CustomTextField(
                    modifier = Modifier
                        .focusRequester(focusThree)
                        .testTag("focusThree"),
                    value = "value three",
                    label = "label three"
                )
            }
        }

        // Then verify the order of selection after perform imeAction (next button)
        composeTestRule
            .onNodeWithTag("focusOne")
            .performImeAction()

        composeTestRule
            .onNodeWithTag("focusTwo")
            .assertIsFocused()
            .performImeAction()

        composeTestRule
            .onNodeWithTag("focusThree")
            .assertIsFocused()
    }
}