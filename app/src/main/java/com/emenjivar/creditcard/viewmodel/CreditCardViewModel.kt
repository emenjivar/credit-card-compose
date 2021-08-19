package com.emenjivar.creditcard.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreditCardViewModel : ViewModel() {
    var name by mutableStateOf("")
    var number by mutableStateOf("")
    var monthExpiration by mutableStateOf("00")
    var yearExpiration by mutableStateOf("00")
}