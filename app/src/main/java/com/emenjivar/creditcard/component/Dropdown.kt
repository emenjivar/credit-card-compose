package com.emenjivar.creditcard.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Dropdown(label: String, data: List<String>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedMonth by remember { mutableStateOf("") }

    val icon = if(expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    OutlinedTextField(
        value = selectedMonth,
        onValueChange = {},
        modifier = Modifier.clickable {
            expanded = !expanded
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(),
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.clickable {
                    expanded = !expanded
                }
            )
        }
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        data.forEach { month ->
            DropdownMenuItem(onClick = {
                selectedMonth = month
                expanded = false
                onSelect(selectedMonth)
            }) {
                Text(month)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownPreview() {
    Dropdown(
        label = "Month",
        data = listOf(),
        onSelect = {}
    )
}