package com.emenjivar.creditcard.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.*
import com.emenjivar.creditcard.utils.CardInputValidator
import com.emenjivar.creditcard.utils.InputTransformation
import com.emenjivar.creditcard.viewmodel.CreditCardViewModel


@Composable
fun CreditCardInputs(viewModel: CreditCardViewModel) {
    val focusHolderName = FocusRequester()
    val focusExpiration = FocusRequester()
    val focusCVC = FocusRequester()

    Column {
        FocusableTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (state.isFocused) viewModel.flipped
                },
            value = viewModel.number,
            label = "Number of card",
            onValueChange = {
                CardInputValidator.parseNumber(it)?.let { number ->
                    viewModel.number = number
                }
            },
            keyboardType = KeyboardType.Number,
            nextFocus = focusHolderName
        )

        FocusableTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (state.isFocused) viewModel.flipped = false
                }
                .focusRequester(focusHolderName),
            value = viewModel.name,
            label = "Name of card",
            onValueChange = {
                CardInputValidator.parseHolderName(it)?.let { name ->
                    viewModel.name = name
                }
            },
            nextFocus = focusExpiration
        )

        Row {
            FocusableTextField(
                modifier = Modifier
                    .weight(0.5f)
                    .onFocusEvent { state ->
                        if (state.isFocused) viewModel.flipped = false
                    }
                    .focusRequester(focusExpiration),
                value = viewModel.expiration,
                label = "Expiration",
                visualTransformation = InputTransformation(),
                onValueChange = {
                    viewModel.expiration = if (it.length >= 5) it.substring(0, 4) else it
                },
                keyboardType = KeyboardType.Number,
                nextFocus = focusCVC
            )

            FocusableTextField(
                modifier = Modifier
                    .weight(0.5f)
                    .onFocusEvent { state ->
                        if (state.isFocused) viewModel.flipped = true
                    }
                    .focusRequester(focusCVC),
                value = viewModel.cvc,
                label = "Security code",
                onValueChange = {
                    CardInputValidator.parseCVC(it)?.let { cvc ->
                        viewModel.cvc = cvc
                    }
                },
                keyboardType = KeyboardType.Number
            )
        }

    }
}

@Composable
private fun FocusableTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    nextFocus: FocusRequester? = null,
    label: String
) {
    val keyboardOptions = KeyboardOptions(
        keyboardType = keyboardType,
        imeAction = if (nextFocus != null) ImeAction.Next else ImeAction.Done
    )

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        visualTransformation = visualTransformation,
        modifier = modifier,
        label = { Text(text = label) },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = {
                nextFocus?.requestFocus()
            }
        )
    )
}