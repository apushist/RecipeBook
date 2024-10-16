/*
 * Copyright (C) 2024 Anastasiya Pushkova
 * Copyright (C) 2024 Anastasiya Ermilova
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sfedu.recipebook.ui.recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sfedu.recipebook.R

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults

@Composable
fun CalculateIngredientsScreen(
    onNavigateToRecipeView: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()
){
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            (TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onNavigateToRecipeView() }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.return_button))
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    navigationIconContentColor = Color(0xFF4DB6AC),
                    actionIconContentColor = Color(0xFF4DB6AC)
                )
            ))
        },
    ){
        Box(
            modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .background(Color.White)
        ) {
            Column {
                LazyColumn(modifier) {
                    item{
                        val multiplier = IngredientsDropdownMenu()

                        Row(
                            modifier = Modifier.padding(top = 10.dp)
                        ) {
                            Spacer(modifier = Modifier.weight(2f,true))

                            Button(onClick = {
                                changeViewableIngredients(multiplier)
                                onNavigateToRecipeView()
                            },
                                modifier = Modifier.width(140.dp),
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
            modifier = Modifier.background(Color.White),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            offset = DpOffset(0.dp, 43.dp)
        ) {
            viewableIngredients.forEach { item ->
                DropdownMenuItem(
                    
                    { Text(

                        text = item.first)},
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
        Text(
            text = selectedItem.value.first,
            modifier = Modifier
                            .height(30.dp)
                            .background(Color(0xFFB2DFDB)),
            style = TextStyle(textIndent = TextIndent(10.sp, 5.sp), fontSize = 26.sp,),
        )
    }

    Row(
        modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE1E2EC))
                        .clickable { expanded.value = true }
    ) {
        TextField(
            value = selectedItemQuantity.toString(),
            onValueChange = { selectedItemQuantity = round2Characters(it.toDoubleOrNull() ?: selectedItem.value.second) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                            .weight(1f)
                            .background(Color(0xFFE6E6FA))
            ,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFE1E2EC),
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color(0xFFE1E2EC),
                focusedTextColor = Color.Black,
                focusedIndicatorColor = Color(0xFF4DB6AC),
            ),
        )
        Text(
            text = selectedItem.value.third,
            modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 10.dp)
                            .padding(start = 10.dp)
                            .background(Color(0xFFE1E2EC))
            ,

        )
    }

    return selectedItemQuantity/ selectedItem.value.second
}








































