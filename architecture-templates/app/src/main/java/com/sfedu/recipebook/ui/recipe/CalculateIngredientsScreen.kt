package com.sfedu.recipebook.ui.recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sfedu.recipebook.R

@Composable
fun CalculateIngredientsScreen(
    onNavigateToRecipeView: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()
){

    LazyColumn(modifier) {
        item{
            Button(
                onClick = { onNavigateToRecipeView() },
                modifier = Modifier.width(156.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                border = BorderStroke(2.dp, Color(0xFF4DB6AC))
            ) {
                Text(
                    text = stringResource(id = R.string.return_button)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            val multiplier = IngredientsDropdownMenu()

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                changeViewableIngredients(multiplier)
                onNavigateToRecipeView()
            },
                modifier = Modifier.width(156.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                border = BorderStroke(2.dp, Color(0xFF4DB6AC))
            ) {
                Text(
                    text = stringResource(id = R.string.calculate_button)
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

    Box {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            offset = DpOffset(0.dp, 45.dp)
        ) {
            viewableIngredients.forEach { item ->
                DropdownMenuItem(
                    { Text(text = item.first)},
                    onClick = {
                        selectedItem.value = item
                        selectedItemQuantity = item.second
                        expanded.value = false
                    }
                )
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFB2DFDB))
            .padding(0.dp, 10.dp, 0.dp, 10.dp)
            .clickable { expanded.value = true }
    ) {
        //Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = selectedItem.value.first,
            modifier = Modifier
                .height(30.dp)
                //.padding(10.dp, 10.dp, 10.dp, 0.dp)
                .background(Color(0xFFB2DFDB)),
            style = androidx.compose.ui.text.TextStyle(textIndent = TextIndent(10.sp, 5.sp), fontSize = 26.sp,),
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth().background(Color(0xFFE1E2EC))
            .clickable { expanded.value = true }
    ) {
        //Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = selectedItemQuantity.toString(),
            onValueChange = { selectedItemQuantity = round2Characters(it.toDoubleOrNull() ?: selectedItem.value.second) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
                .background(Color(0xFFE6E6FA))
            ,
        )
        Text(
            text = selectedItem.value.third,
            modifier = Modifier.weight(1f)
                .padding(vertical = 10.dp)
                .padding(start = 10.dp)
                .background(Color(0xFFE1E2EC))
            ,

        )
    }

    return selectedItemQuantity/ selectedItem.value.second
}
