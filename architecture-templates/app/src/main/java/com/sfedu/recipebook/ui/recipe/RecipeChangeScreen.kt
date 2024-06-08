package com.sfedu.recipebook.ui.recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlin.reflect.KFunction6
import com.sfedu.recipebook.R

@Composable
fun RecipeChangeScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToRecipeView: () -> Unit,
    onNavigateToChange: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()) {
    RecipeChangeScreen(
        onNavigateToMain = onNavigateToMain,
        onNavigateToRecipeView = onNavigateToRecipeView,
        onNavigateToChange = onNavigateToChange,
        onUpdate = viewModel::updateRecipe,
        modifier = modifier
    )

}

@Composable
internal fun RecipeChangeScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToRecipeView: () -> Unit,
    onNavigateToChange: () -> Unit,
    onUpdate: KFunction6<String, String, Int, Int, String, String, Unit>,
    modifier: Modifier = Modifier
) {
    var nameRecipe by remember { mutableStateOf(currentRecipe.name) }
    var difficulty by remember { mutableStateOf(currentRecipe.difficulty) }
    var cookingTime by remember { mutableStateOf(currentRecipe.cookingTime) }
    var servingSize by remember { mutableStateOf(currentRecipe.servingSize) }
    var ingredients by remember { mutableStateOf(currentRecipe.ingredients) }
    var recipeSteps by remember { mutableStateOf(currentRecipe.recipeSteps) }
    //var imageRecipe by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            (TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onNavigateToRecipeView() }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_icon_description)
                        )
                    }
                },
                actions = {
                    Column {
                        Box {
                            Button(
                                onClick = {
                                    onUpdate(
                                        nameRecipe,
                                        difficulty,
                                        cookingTime,//.toIntOrNull() ?: 0,
                                        servingSize,//.toIntOrNull() ?: 0,
                                        ingredients,
                                        recipeSteps
                                    )
                                    onNavigateToRecipeView()
                                },
                                modifier = Modifier
                                    .width(116.dp)
                                    .padding(start = 5.dp, end = 5.dp),
                                shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFE0F2F1),
                                    contentColor = Color.Black
                                ),
                                border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                            ) {
                                Text(stringResource(id = R.string.save_button))
                            }
                        }
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
            //.background(Color(0xFFE0F2F1)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                ) {

                    item {

                        TextField(
                            value = nameRecipe,
                            onValueChange = { nameRecipe = it },
                            //supportingText = { Text(text = stringResource(id = R.string.recipe_name_text_field))},
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp, start = 5.dp, end = 5.dp),
                            placeholder = { Text(stringResource(id = R.string.recipe_name_text_field)) },
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        TextField(
                            value = difficulty,
                            onValueChange = { difficulty = it },
                            //supportingText = { Text(text = stringResource(id = R.string.difficulty_text_field))},
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp),
                            placeholder = { Text(stringResource(id = R.string.difficulty_text_field)) },
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        TextField(
                            value = cookingTime.toString(),
                            onValueChange = { cookingTime = it.toIntOrNull()?:0 },
                            //supportingText = { Text(text = stringResource(id = R.string.cooking_time_text_field))},
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                            placeholder = { Text(stringResource(id = R.string.cooking_time_text_field)) },

                            )

                        Spacer(modifier = Modifier.height(5.dp))

                        TextField(
                            value = servingSize.toString(),
                            onValueChange = { servingSize = it.toIntOrNull()?:0 },
                            //supportingText = { Text(text = stringResource(id = R.string.serving_size_text_field))},
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            )
                            ,
                            placeholder = { Text(stringResource(id = R.string.serving_size_text_field)) },

                            )
                        Spacer(modifier = Modifier.height(5.dp))

                        val multiplier = ChangeIngredientsDropdownMenu()

                        Row(
                            modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                        ) {
                            Spacer(modifier = Modifier.weight(2f,true))

                            //todo add button + delete button
                            Button(onClick = {
                                //changeViewableIngredients(multiplier) todo saving
                                onNavigateToChange()
                            },
                                modifier = Modifier.width(116.dp),
                                shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                                border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                            ) {
                                Text(
                                    text = stringResource(id = R.string.save_button)
                                )
                            }
                        }

                        ingredients = ingredientsListToString(IngredientList(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp)))

                        Spacer(modifier = Modifier.height(5.dp))

                        TextField(
                            value = recipeSteps,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp),
                            onValueChange = { recipeSteps = it },
                            placeholder = { Text(stringResource(id = R.string.recipe_steps_text_field)) },
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                    }
                }
            }
        }
    }
}

@Composable
fun ChangeIngredientsDropdownMenu():Triple<String, Double,String> {
    val expanded = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf(viewableIngredients[0]) }
    var selectedItemQuantity by remember { mutableDoubleStateOf(selectedItem.value.second) }

    Box {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            offset = DpOffset(0.dp, 43.dp)
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
    var ingredientName by remember { mutableStateOf("") }
    var ingredientMeasure by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFB2DFDB))
            .padding(0.dp, 10.dp, 0.dp, 10.dp)
            .clickable { expanded.value = true }
    ) {
        TextField (
            value = selectedItem.value.first,
            onValueChange = { ingredientName = it },
            placeholder = { Text( stringResource(id = R.string.ingredient_name_text_field)) },

            modifier = Modifier
                .height(30.dp)
                .background(Color(0xFFB2DFDB)),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFE1E2EC),
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color(0xFFE1E2EC),
                focusedTextColor = Color.Black,
                focusedIndicatorColor = Color(0xFF4DB6AC),
            ),
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
        TextField (
            value = selectedItem.value.third,
            onValueChange = { ingredientMeasure = it },
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp)
                .padding(start = 10.dp)
                .background(Color(0xFFE1E2EC))
            ,
            placeholder = { Text(stringResource(id = R.string.ingredient_measure_text_field)) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFE1E2EC),
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color(0xFFE1E2EC),
                focusedTextColor = Color.Black,
                focusedIndicatorColor = Color(0xFF4DB6AC),
            ),
            )
    }
    return Triple(ingredientName,selectedItemQuantity,ingredientMeasure)
}
