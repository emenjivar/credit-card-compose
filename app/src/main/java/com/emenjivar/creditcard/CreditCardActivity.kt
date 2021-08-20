package com.emenjivar.creditcard

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emenjivar.creditcard.component.CreditCardFrontSide
import com.emenjivar.creditcard.component.Dropdown
import com.emenjivar.creditcard.model.CreditCardModel
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
    Column(
        modifier = Modifier
            .padding(top = 28.dp)
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        val model = CreditCardModel(
            number = viewModel.number,
            holderName = viewModel.name,
            expiration = viewModel.monthExpiration + "/" + viewModel.yearExpiration
        )
        CreditCardFrontSide(
            model = model,
            emptyChar = 'X',
            backgroundColor = Color.Black
        )
        CreditCardInputs(viewModel = viewModel)
    }

}

@Composable
fun CreditCardInputs(viewModel: CreditCardViewModel) {
    Column {
        OutlinedTextField(
            value = viewModel.number,
            onValueChange = {
                if (it.isNotEmpty() && it.length <= 16 && it.last().isDigit()) {
                    viewModel.number = it
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(text = "Number of card")
            }
        )
        Dropdown(
            label = "Month",
            data = listOf("01", "02", "03", "04", "05", "06"),
            onSelect = {
                Log.d("CreditCardActivity", "month selected: $it")
                viewModel.monthExpiration = it
            }
        )
        Dropdown(
            label = "Year",
            data = listOf("21", "22", "23", "24", "25"),
            onSelect = {
                Log.d("CreditCardActivity", "year selected: $it")
                viewModel.yearExpiration = it
            }
        )
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = {
                if (it.isNotEmpty() && it.last().isLetter() || it.last() == ' ') {
                    viewModel.name = it
                }
            },
            label = {
                Text("Name of card")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyCreditCardPreview() {
    val mockViewModel = CreditCardViewModel()
    mockViewModel.name = ""
    mockViewModel.number = ""
    mockViewModel.monthExpiration = "01"
    mockViewModel.yearExpiration = "22"

    LayoutCreditCard(
        viewModel = mockViewModel
    )
}