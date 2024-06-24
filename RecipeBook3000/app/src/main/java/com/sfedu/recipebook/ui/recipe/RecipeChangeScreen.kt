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
import androidx.compose.material.icons.filled.ArrowDropDown
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
import java.io.Serializable

@Composable
fun RecipeChangeScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToRecipeView: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()) {
    RecipeChangeScreen(
        onNavigateToMain = onNavigateToMain,
        onNavigateToRecipeView = onNavigateToRecipeView,
        onUpdate = viewModel::updateRecipe,
        modifier = modifier
    )

}

@Composable
internal fun RecipeChangeScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToRecipeView: () -> Unit,
    onUpdate: KFunction6<String, String, Int, Int, String, String, Unit>,
    modifier: Modifier = Modifier
) {
    var nameRecipe by remember { mutableStateOf(currentRecipe.name) }
    var difficulty by remember { mutableStateOf(currentRecipe.difficulty) }
    var cookingTime by remember { mutableStateOf(currentRecipe.cookingTime) }
    var servingSize by remember { mutableStateOf(currentRecipe.servingSize) }
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
                                        ingredientsListToString(viewableIngredients),
                                        recipeSteps
                                    )
                                    onNavigateToRecipeView()
                                },
                                modifier = Modifier
                                    .width(140.dp)
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
                .background(Color.White),
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

                        Text(
                            text = stringResource(id = R.string.recipe_name_text_field),
                            modifier = Modifier
                                .padding( start = 5.dp, end = 5.dp),
                        )

                        TextField(
                            value = nameRecipe,
                            onValueChange = { nameRecipe = it },
                            //supportingText = { Text(text = stringResource(id = R.string.recipe_name_text_field))},
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding( start = 5.dp, end = 5.dp),
                            placeholder = { Text(stringResource(id = R.string.recipe_name_text_field)) },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFFE1E2EC),
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color(0xFFE1E2EC),
                                focusedTextColor = Color.Black,
                                focusedIndicatorColor = Color(0xFF4DB6AC),)
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = stringResource(id = R.string.difficulty_text_field),
                            modifier = Modifier
                                .padding( start = 5.dp, end = 5.dp),
                        )

                        TextField(
                            value = difficulty,
                            onValueChange = { difficulty = it },
                            //supportingText = { Text(text = stringResource(id = R.string.difficulty_text_field))},
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp),
                            placeholder = { Text(stringResource(id = R.string.difficulty_text_field)) },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFFE1E2EC),
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color(0xFFE1E2EC),
                                focusedTextColor = Color.Black,
                                focusedIndicatorColor = Color(0xFF4DB6AC),)
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = stringResource(id = R.string.cooking_time_text_field),
                            modifier = Modifier
                                .padding( start = 5.dp, end = 5.dp),
                        )

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
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFFE1E2EC),
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color(0xFFE1E2EC),
                                focusedTextColor = Color.Black,
                                focusedIndicatorColor = Color(0xFF4DB6AC),)
                            )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = stringResource(id = R.string.serving_size_text_field),
                            modifier = Modifier
                                .padding( start = 5.dp, end = 5.dp),
                        )

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
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFFE1E2EC),
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color(0xFFE1E2EC),
                                focusedTextColor = Color.Black,
                                focusedIndicatorColor = Color(0xFF4DB6AC),)
                            )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = stringResource(id = R.string.change_ingredient_field),
                            modifier = Modifier
                                .padding( start = 5.dp, end = 5.dp),
                        )

                        val ingredient = ChangeIngredientsDropdownMenu()

                        Row(
                            modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                        ) {

                            Button(onClick = {
                                viewableIngredients[ingredient.first] = Triple(ingredient.second,ingredient.third,ingredient.fourth)
                            },
                                modifier = Modifier.width(126.dp),
                                shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                                border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                            ) {
                                Text(
                                    text = stringResource(id = R.string.save_button)
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f,true))

                            Button(onClick = {
                                viewableIngredients.removeAt(ingredient.first)
                            },
                                modifier = Modifier.width(126.dp),
                                shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                                border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                            ) {
                                Text(
                                    text =  stringResource(id = R.string.delete_button)
                                )
                            }
                            /*
                            Button(onClick = {
                                viewableIngredients.add(Triple(ingredient.second,ingredient.third,ingredient.fourth))
                            },
                                modifier = Modifier.width(126.dp),
                                shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                                border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                            ) {
                                Text(
                                    text = stringResource(id = R.string.add_icon_description)
                                )
                            }*/
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = stringResource(id = R.string.add_ingredient_field),
                            modifier = Modifier
                                .padding( start = 5.dp, end = 5.dp),
                        )

                        val ingredients = ChangeIngredientList()

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = stringResource(id = R.string.recipe_steps_text_field),
                            modifier = Modifier
                                .padding( start = 5.dp, end = 5.dp),
                        )

                        TextField(
                            value = recipeSteps,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp),
                            onValueChange = { recipeSteps = it },
                            placeholder = { Text(stringResource(id = R.string.recipe_steps_text_field)) },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFFE1E2EC),
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color(0xFFE1E2EC),
                                focusedTextColor = Color.Black,
                                focusedIndicatorColor = Color(0xFF4DB6AC),)
                        )

                        Spacer(modifier = Modifier.height(50.dp))

                    }
                }
            }
        }
    }
}

@Composable
fun ChangeIngredientsDropdownMenu(): Quadruple<Int,String, Double,String> {
    val expanded = remember { mutableStateOf(false) }
    var selectedItemName = remember { mutableStateOf(viewableIngredients[0].first) }
    var selectedItemQuantity by remember { mutableDoubleStateOf(viewableIngredients[0].second) }
    var selectedItemMeasure = remember { mutableStateOf(viewableIngredients[0].third) }
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    Box {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            offset = DpOffset(5.dp, 45.dp),
            modifier = Modifier
                //.fillMaxWidth()
                //.padding(start = 5.dp, end = 10.dp),
        ) {
            viewableIngredients.forEachIndexed { index, item ->
                DropdownMenuItem(
                    { Text(text = item.first)},
                    onClick = {
                        selectedItemName.value = item.first
                        selectedItemQuantity = item.second
                        selectedItemMeasure.value = item.third
                        expanded.value = false
                        selectedItemIndex = index
                    }
                )
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            //.background(Color(0xFFE1E2EC))
            .padding(5.dp, 0.dp, 5.dp, 0.dp)
            //.clickable { expanded.value = true }
    ) {
        TextField (
            value = selectedItemName.value,
            onValueChange = { selectedItemName.value = it },
            placeholder = { Text( stringResource(id = R.string.ingredient_name_text_field)) },
            //textStyle = TextStyle(textIndent = TextIndent(10.sp, 5.sp), fontSize = 26.sp,),
            leadingIcon = {
                Icon(
                    Icons.Filled.ArrowDropDown,
                    contentDescription = "Выбор ингредиента",
                    modifier = Modifier.clickable { expanded.value = true }
                )

            },
            modifier = Modifier
                //.height(30.dp)
                .fillMaxWidth()
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

    Spacer(modifier = Modifier.height(5.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp, 0.dp, 5.dp, 0.dp)
            //.background(Color(0xFFE1E2EC))
            .clickable { expanded.value = true }
    ) {
        TextField(
            value = selectedItemQuantity.toString(),
            onValueChange = { selectedItemQuantity = round2Characters(it.toDoubleOrNull() ?: 0.0) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                //.background(Color(0xFFE6E6FA))
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
            value = selectedItemMeasure.value,
            onValueChange = { selectedItemMeasure.value = it },
            modifier = Modifier
                .weight(2f)
                //.padding(vertical = 10.dp)
                //.padding(start = 10.dp)
                //.background(Color(0xFFE1E2EC))
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
    return Quadruple(selectedItemIndex,selectedItemName.value,selectedItemQuantity,selectedItemMeasure.value)
}

@Composable
fun ChangeIngredientList(
    modifier: Modifier = Modifier
):List<Triple<String, Double,String>> {
    var ingredients by remember { mutableStateOf(mutableListOf<Triple<String, Double,String>>() ) }
    var newIngredientName by remember { mutableStateOf("") }
    var newIngredientQuantity by remember { mutableDoubleStateOf(0.0) }
    var newIngredientMeasure by remember { mutableStateOf("") }

    Column(modifier) {

        Row() {
            TextField(
                value = newIngredientName,
                onValueChange = { newIngredientName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp),
                placeholder = { Text( stringResource(id = R.string.ingredient_name_text_field)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFE1E2EC),
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color(0xFFE1E2EC),
                    focusedTextColor = Color.Black,
                    focusedIndicatorColor = Color(0xFF4DB6AC),
                ),
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(){
            TextField(
                value = newIngredientQuantity.toString(),
                onValueChange = { newIngredientQuantity = round2Characters(it.toDoubleOrNull() ?: 0.0 )},
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFE1E2EC),
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color(0xFFE1E2EC),
                    focusedTextColor = Color.Black,
                    focusedIndicatorColor = Color(0xFF4DB6AC),
                ),
            )

            TextField(
                value = newIngredientMeasure,
                onValueChange = { newIngredientMeasure = it },
                modifier = Modifier
                    .weight(2f)
                    .padding(end = 5.dp),
                placeholder = { Text(stringResource(id = R.string.ingredient_measure_text_field)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFE1E2EC),
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color(0xFFE1E2EC),
                    focusedTextColor = Color.Black,
                    focusedIndicatorColor = Color(0xFF4DB6AC),
                ),
            )

            Button(
                onClick = {
                    if(newIngredientName != "" &&
                        newIngredientQuantity != 0.0 &&
                        newIngredientMeasure != ""
                    )
                        viewableIngredients.add(Triple(newIngredientName,
                            round2Characters(newIngredientQuantity),
                            newIngredientMeasure))
                    //viewableIngredients.add(Triple(ingredient.second,ingredient.third,ingredient.fourth))
                    newIngredientName = ""
                    newIngredientQuantity = 0.0
                    newIngredientMeasure = ""
                },
                modifier = Modifier
                    .width(140.dp)
                    .padding(top = 10.dp, end = 5.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                border = BorderStroke(2.dp, Color(0xFF4DB6AC))
            ) {
                Text(stringResource(id = R.string.add_icon_description))
            }
        }

        ingredients.forEach {
            IngredientView(ingredient = it)
        }
    }
    return ingredients
}
