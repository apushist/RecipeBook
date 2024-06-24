package com.sfedu.recipebook.ui.recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.material3.AlertDialog
import androidx.compose.ui.res.stringResource
import com.sfedu.recipebook.R


@Composable
fun RecipeScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToRecipeView: () -> Unit,
    onNavigateToCalc: () -> Unit,
    onNavigateToChange: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val recipe = currentRecipe
    val ingredients = remember {
        mutableStateOf(viewableIngredients)
    }
    if(recipe != null) {
        Column{
            Scaffold(
                topBar = {
                    @OptIn(ExperimentalMaterial3Api::class)
                    TopAppBar(
                        title= { },
                        navigationIcon={
                            IconButton(onClick = { onNavigateToMain() }
                            ) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back_icon_description))
                            }
                        },
                        actions={

                            Column{
                                var expanded by remember { mutableStateOf(false) }

                                Box {
                                    IconButton(
                                        onClick = { expanded = true }
                                    ) {
                                        Icon(Icons.Filled.MoreVert, contentDescription = stringResource(id = R.string.more_icon_description))
                                    }
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false },
                                        Modifier.background(Color.White)

                                    ) {
                                        TextButton(
                                            {
                                                currentRecipe = recipe
                                                resetViewableIngredients(ingredients = ingredients)

                                                onNavigateToChange()
                                            }
                                        ){
                                            Text(stringResource(id = R.string.change_recipe_button), color = Color.Black)
                                        }

                                        val openDialog = remember { mutableStateOf(false) }
                                        TextButton(
                                            { openDialog.value = true },

                                        ){
                                            Text(stringResource(id = R.string.delete_recipe_button), color = Color.Black)
                                        }
                                        if (openDialog.value) {
                                            AlertDialog(
                                                onDismissRequest = { openDialog.value = false},
                                                title = { Text(stringResource(id = R.string.delete_recipe_alert)) },
                                                dismissButton  = {
                                                    Button(
                                                        onClick = { openDialog.value = false },
                                                        modifier = Modifier.width(126.dp),
                                                        shape = RoundedCornerShape(15.dp),
                                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                                                        border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                                                    ) {
                                                        Text(stringResource(id = R.string.cancel_button))
                                                    }
                                                },
                                                confirmButton = {
                                                    Button(
                                                        onClick = { viewModel.deleteRecipe(recipe)
                                                            onNavigateToMain(); openDialog.value = false },
                                                        //openDialog.value = false ,
                                                        modifier = Modifier.width(126.dp),
                                                        shape = RoundedCornerShape(15.dp),
                                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                                                        border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                                                    ) {
                                                        Text(stringResource(id = R.string.delete_button))
                                                    }
                                                },
                                                containerColor = Color.White,
                                                titleContentColor = Color.Black,
                                            )
                                        }
                                    }
                                }
                            }
                        },

                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White,
                            navigationIconContentColor = Color(0xFF4DB6AC),
                            actionIconContentColor = Color(0xFF4DB6AC)
                        )
                    )
                },

                bottomBar = {
                    BottomAppBar(
                        containerColor = Color.White,
                        contentColor = Color.LightGray
                    ){
                        val openDialog = remember { mutableStateOf(false) }
                        Button(
                            onClick = { openDialog.value = true },
                            modifier = Modifier
                                .width(140.dp)
                                .padding(start = 5.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                            border = BorderStroke(2.dp, Color(0xFF4DB6AC)),
                        ) {
                            Text(text = stringResource(id = R.string.reset_ingredients_button))
                        }
                        if (openDialog.value) {
                            AlertDialog(
                                onDismissRequest = { openDialog.value = false},
                                title = { Text(stringResource(id = R.string.reset_ingredients_alert)) },
                                confirmButton  = {
                                    Button(
                                        onClick = {
                                                resetViewableIngredients(ingredients = ingredients)
                                                onNavigateToRecipeView(); openDialog.value = false
                                        },
                                        modifier = Modifier.width(126.dp),
                                        shape = RoundedCornerShape(15.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                                        border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                                    ) {
                                        Text(stringResource(id = R.string.reset_ingredients_button))
                                    }
                                },
                                dismissButton = {
                                    Button(
                                        onClick = { openDialog.value = false },
                                        modifier = Modifier.width(126.dp),
                                        shape = RoundedCornerShape(15.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                                        border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                                    ) {
                                        Text(stringResource(id = R.string.cancel_button))
                                    }
                                },
                                containerColor = Color.White,
                                titleContentColor = Color.Black,
                            )
                        }




                        Spacer(Modifier.weight(1f, true))

                        Button(onClick = {
                            resetViewableIngredients(ingredients = ingredients)
                            onNavigateToCalc() },

                            modifier = Modifier
                                .width(140.dp)
                                .padding(end = 5.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                            border = BorderStroke(2.dp, Color(0xFF4DB6AC)),
                        ) {
                            Text(text = stringResource(id = R.string.recalculate_ingredients_button))
                        }
                    }
                },
            ){
                Box(
                    modifier = Modifier
                                    .fillMaxSize()
                                    .padding(it)
                                    .background(Color(0xFFE0F2F1)),
                ) {
                    Column(
                        modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 24.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            item {
                                Column(
                                    Modifier
                                        .padding(10.dp, 10.dp, 10.dp, 10.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = recipe.name,
                                        modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(Color(0xFFB2DFDB))
                                                        .padding(0.dp, 5.dp),
                                        style = TextStyle(textIndent = TextIndent(10.sp, 5.sp), fontSize = 26.sp,),
                                    )
                                }

                            }
                            item {
                                Row (
                                    modifier = Modifier.padding(10.dp, 3.dp, 0.dp, 0.dp)
                                ){
                                    Text(
                                        text = stringResource(id = R.string.cooking_time_text_field),
                                    )
                                    Text( text = ": ")
                                    Text(
                                        text = recipe.cookingTime.toString(),
                                    )
                                }
                                Row (
                                    modifier = Modifier.padding(10.dp, 3.dp, 0.dp, 0.dp)
                                ){
                                    Text(
                                        text = stringResource(id = R.string.difficulty_text_field),
                                    )
                                    Text( text = ": ")
                                    Text(
                                        text = recipe.difficulty,
                                    )
                                }
                                Row (
                                    modifier = Modifier.padding(10.dp, 3.dp, 0.dp, 0.dp)
                                ){
                                    Text(
                                        text = stringResource(id = R.string.serving_size_text_field),
                                    )
                                    Text( text = ": ")
                                    Text(
                                        text = recipe.servingSize.toString(),
                                    )
                                }
                            }
                            item {
                                Row (
                                    modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 0.dp)
                                ){
                                    Text(
                                        text = stringResource(id = R.string.ingredients_title),
                                    )
                                    Box (
                                        modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(5.dp, 10.dp, 10.dp, 0.dp),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Box(
                                            modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(Color(0xFFB2DFDB))
                                                        .height(2.dp),
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                            }
                            items(ingredients.value) {
                                Row(Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "•",
                                        modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp),
                                    )
                                    Text(
                                        text = ingredientTripleToString(it),
                                        modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp),
                                    )
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.height(20.dp))

                                Row (
                                    modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 0.dp)
                                ){
                                    Text(
                                        text = stringResource(id = R.string.steps_title),
                                    )
                                    Box (
                                        modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(5.dp, 10.dp, 10.dp, 0.dp),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Box(
                                            modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(Color(0xFFB2DFDB))
                                                        .height(2.dp),
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = recipe.recipeSteps,
                                    modifier = Modifier.padding(15.dp, 0.dp, 10.dp, 0.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}