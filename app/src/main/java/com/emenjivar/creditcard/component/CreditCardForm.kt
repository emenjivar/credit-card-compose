package com.emenjivar.creditcard.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emenjivar.creditcard.utils.FieldType
import com.emenjivar.creditcard.utils.InputTransformation
import com.emenjivar.creditcard.utils.InputValidator
import com.emenjivar.creditcard.viewmodel.CreditCardViewModel


/**
 * @param viewModel is defined in this project, this value should be sync with CreditCard(...) parameters
 */
@Composable
fun CreditCardForm(
    viewModel: CreditCardViewModel
) {
    val focusHolderName = FocusRequester()
    val focusExpiration = FocusRequester()
    val focusCVC = FocusRequester()

    Column {
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (state.isFocused) viewModel.flipped = false
                },
            value = viewModel.number,
            label = "Number of card",
            visualTransformation = InputTransformation(FieldType.CARD_NUMBER),
            onValueChange = {
                viewModel.number =
                    if (viewModel.number.length >= 16) viewModel.number.substring(0..15) else it

                // When value is completed, request focus of next field
                if (viewModel.number.length >= 16) focusHolderName.requestFocus()
            },
            trailingIcon = {
                CustomTextFieldDeleteIcon(value = viewModel.number) {
                    viewModel.number = ""
                }
            },
            keyboardType = KeyboardType.Number,
            nextFocus = focusHolderName
        )

        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (state.isFocused) viewModel.flipped = false
                }
                .focusRequester(focusHolderName),
            value = viewModel.name,
            label = "Name of card",
            onValueChange = {
                InputValidator.parseHolderName(it)?.let { name ->
                    viewModel.name = name
                }
            },
            trailingIcon = {
                CustomTextFieldDeleteIcon(value = viewModel.name) {
                    viewModel.name = ""
                }
            },
            nextFocus = focusExpiration
        )

        Row {
            CustomTextField(
                modifier = Modifier
                    .weight(0.5f)
                    .onFocusEvent { state ->
                        if (state.isFocused) viewModel.flipped = false
                    }
                    .focusRequester(focusExpiration),
                value = viewModel.expiration,
                label = "Expiration",
                visualTransformation = InputTransformation(FieldType.EXPIRATION),
                onValueChange = {
                    viewModel.expiration = if (it.length >= 4) it.substring(0..3) else it

                    // When value is completed, request focus of next field
                    if (viewModel.expiration.length >= 4) focusCVC.requestFocus()
                },
                trailingIcon = {
                    CustomTextFieldDeleteIcon(value = viewModel.expiration) {
                        viewModel.expiration = ""
                    }
                },
                keyboardType = KeyboardType.Number,
                nextFocus = focusCVC
            )

            CustomTextField(
                modifier = Modifier
                    .weight(0.5f)
                    .onFocusEvent { state ->
                        if (state.isFocused) viewModel.flipped = true
                    }
                    .focusRequester(focusCVC),
                value = viewModel.cvc,
                label = "CVC",
                onValueChange = {
                    InputValidator.parseCVC(it)?.let { cvc ->
                        viewModel.cvc = cvc
                    }
                },
                trailingIcon = {
                    CustomTextFieldDeleteIcon(value = viewModel.cvc) {
                        viewModel.cvc = ""
                    }
                },
                keyboardType = KeyboardType.Number
            )
        }
    }
}

@Preview(name = "Credit card form", showBackground = true)
@Composable
private fun CreditCardFormPreview() {
    val viewModel = CreditCardViewModel()
    Column(
        modifier = Modifier.width(500.dp)
    ) {
        CreditCardForm(viewModel = viewModel)
    }
}