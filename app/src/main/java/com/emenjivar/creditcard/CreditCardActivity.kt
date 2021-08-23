package com.emenjivar.creditcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emenjivar.creditcard.component.CreditCard
import com.emenjivar.creditcard.utils.CardInputValidator
import com.emenjivar.creditcard.viewmodel.CreditCardViewModel

class CreditCardActivity : ComponentActivity() {
    private val viewModel by viewModels<CreditCardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutCreditCard(
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun LayoutCreditCard(viewModel: CreditCardViewModel) {
    Column {
        Column(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        ) {
            CreditCard(
                bankName = "BancoAgrícola",
                number = viewModel.number,
                expiration = viewModel.expiration,
                holderName = viewModel.name,
                cvc = viewModel.cvc,
                flipped = viewModel.flipped,
                emptyChar = 'X'
            )
        }

        CreditCardInputs(viewModel = viewModel)
    }

}

@Composable
fun CreditCardInputs(viewModel: CreditCardViewModel) {
    val requesterHolderName = FocusRequester()
    val requesterExpiration = FocusRequester()
    val requesterCVC = FocusRequester()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if(state.isFocused) viewModel.flipped = false
                },
            value = viewModel.number,
            onValueChange = {
                CardInputValidator.parseNumber(it)?.let { number ->
                    viewModel.number = number
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onNext = { requesterHolderName.requestFocus() }
            ),
            label = {
                Text(text = "Number of card")
            }
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if(state.isFocused) viewModel.flipped = false
                }
                .focusOrder(requesterHolderName),
            value = viewModel.name,
            onValueChange = {
                CardInputValidator.parseHolderName(it)?.let { name ->
                    viewModel.name = name
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { requesterExpiration.requestFocus() }
            ),
            label = {
                Text("Name of card")
            }
        )

        Row {
            TextField(
                value = viewModel.expiration,
                modifier = Modifier
                    .onFocusEvent { state ->
                        if(state.isFocused) viewModel.flipped = false
                    }
                    .focusRequester(requesterExpiration),
                onValueChange = {
                    CardInputValidator.parseExpiration(it, viewModel.expiration)
                        ?.let { expiration ->
                            viewModel.expiration = expiration
                        }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onNext = { requesterCVC.requestFocus() }
                ),
                label = {
                    Text("Expiration")
                }
            )
            TextField(
                modifier = Modifier
                    .weight(0.5f)
                    .onFocusEvent { state ->
                        if(state.isFocused) viewModel.flipped = true
                    }
                    .focusRequester(requesterCVC),
                value = viewModel.cvc,
                onValueChange = {
                    CardInputValidator.parseCVC(it)?.let { cvc ->
                        viewModel.cvc = cvc
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                label = {
                    Text("Security code")
                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MyCreditCardPreview() {
    val mockViewModel = CreditCardViewModel()
    mockViewModel.name = ""
    mockViewModel.number = ""
    mockViewModel.expiration = "01/22"

    LayoutCreditCard(
        viewModel = mockViewModel
    )
}