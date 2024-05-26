package com.sfedu.recipebook.ui.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CalculateIngredientsScreen(
    onNavigateToRecipeView: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()
){

    LazyColumn(modifier) {
        item{
            Button(onClick = { onNavigateToRecipeView() }) {
                Text(
                    text = "Return"
                )
            }
            val multiplier = IngredientsDropdownMenu()

            Button(onClick = {
                changeViewableIngredients(multiplier)
                onNavigateToRecipeView()
            }) {
                Text(
                    text = "Calculate"
                )
            }
        }
    }
}

@Composable
fun IngredientsDropdownMenu():Double {
    val expanded = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf(viewableIngredients[0]) }
    var selectedItemQuantity by remember { mutableDoubleStateOf(selectedItem.value.second) }

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        offset = DpOffset(0.dp, 0.dp)
    ) {
        viewableIngredients.forEach { item ->
            DropdownMenuItem(
                { Text(text = item.first)},
                onClick = {
                    selectedItem.value = item
                    selectedItemQuantity = item.second
                    expanded.value = false
            })
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth().background(Color.Gray)
            .clickable { expanded.value = true }
    ) {
        Text(
            text = selectedItem.value.first,
            modifier = Modifier
        )
        TextField(
            value = selectedItemQuantity.toString(),
            onValueChange = { selectedItemQuantity = round2Characters(it.toDoubleOrNull() ?: selectedItem.value.second) },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = selectedItem.value.third,
            modifier = Modifier
        )
    }

    return selectedItemQuantity/ selectedItem.value.second
}
