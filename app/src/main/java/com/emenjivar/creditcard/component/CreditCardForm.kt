package com.emenjivar.creditcard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.emenjivar.creditcard.R
import com.emenjivar.creditcard.utils.CardInputValidator
import com.emenjivar.creditcard.utils.FieldType
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
                FocusableTextFieldDeleteIcon(value = viewModel.number) {
                    viewModel.number = ""
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
            trailingIcon = {
                FocusableTextFieldDeleteIcon(value = viewModel.name) {
                    viewModel.name = ""
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
                visualTransformation = InputTransformation(FieldType.EXPIRATION),
                onValueChange = {
                    viewModel.expiration = if (it.length >= 4) it.substring(0..3) else it

                    // When value is completed, request focus of next field
                    if (viewModel.expiration.length >= 4) focusCVC.requestFocus()
                },
                trailingIcon = {
                    FocusableTextFieldDeleteIcon(value = viewModel.expiration) {
                        viewModel.expiration = ""
                    }
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
                label = "CVC",
                onValueChange = {
                    CardInputValidator.parseCVC(it)?.let { cvc ->
                        viewModel.cvc = cvc
                    }
                },
                trailingIcon = {
                    FocusableTextFieldDeleteIcon(value = viewModel.cvc) {
                        viewModel.cvc = ""
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
    label: String,
    trailingIcon: @Composable (() -> Unit)? = null
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
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = {
                nextFocus?.requestFocus()
            }
        )
    )
}

@Composable
private fun FocusableTextFieldDeleteIcon(
    value: String,
    onClick: () -> Unit
) {
    if (value.isNotBlank()) {
        Box(
            modifier = Modifier
                .height(30.dp)
                .padding(2.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable { onClick() }
        ) {
            Icon(
                modifier = Modifier
                    .padding(4.dp),
                painter = painterResource(id = R.drawable.ic_baseline_close_24),
                contentDescription = null
            )
        }
    }
}