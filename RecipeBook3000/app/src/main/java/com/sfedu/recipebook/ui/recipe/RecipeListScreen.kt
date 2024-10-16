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

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.sfedu.recipebook.data.local.database.Recipe
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DrawerValue

import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer

import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.material3.rememberDrawerState

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.Alignment
import androidx.compose.runtime.setValue

import androidx.compose.material3.AlertDialog
import androidx.compose.ui.res.stringResource
import com.sfedu.recipebook.R


@Composable
fun RecipeListScreen(
    onNavigateToAddScreen: () -> Unit,
    onNavigateToRecipeView: () -> Unit,
    context: Context,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()
    if (items is RecipeUiState.Success) {
        RecipeListScreen(
            onNavigateToAddScreen = onNavigateToAddScreen,
            onNavigateToRecipeView = onNavigateToRecipeView,
            onDelete = viewModel::deleteAll,
            recipes = (items as RecipeUiState.Success).data,
            context = context,
            modifier = modifier
        )
    }
}



@Composable
internal fun RecipeListScreen(
    onNavigateToAddScreen: () -> Unit,
    onNavigateToRecipeView: () -> Unit,
    recipes: List<Recipe>,
    onDelete: () -> Unit,
    context: Context,
    modifier: Modifier = Modifier
) {
    var drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White
            ){
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    var expanded by remember { mutableStateOf(false)}

//                    Box(
//                        contentAlignment = Alignment.TopStart,
//                        modifier = Modifier//.fillMaxHeight()
//                                            .clickable(onClick = { expanded = true })
//                    ) {
//                        Text(stringResource(id = R.string.change_language_button))
//
//                        DropdownMenu(
//                            expanded = expanded,
//                            onDismissRequest = { expanded = false },
//                        ) {
//                            DropdownMenuItem(
//                                onClick = { // TODO add a Russian translation
//                                          setLocale("ru", context = context)
//                                     },
//                                text = { Text(stringResource(id = R.string.russian_button)) }
//                            )
//
//                            DropdownMenuItem(
//                                onClick = { // TODO add an English translation
//                                    setLocale("en", context = context)
//
//                                      },
//                                text = { Text(stringResource(id = R.string.english_button)) }
//                            )
//                        }
//
//                    }

                    Box(
                        contentAlignment = Alignment.TopStart,//BottomStart,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        val openDialog = remember { mutableStateOf(false) }
                        Button(
                            { openDialog.value = true },
                            modifier = Modifier.width(156.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                            border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                        ) {
                            Text(stringResource(id = R.string.delete_all_recipes_button))
                        }
                        if (openDialog.value) {
                            AlertDialog(
                                onDismissRequest = { openDialog.value = false},
                                title = { Text(stringResource(id = R.string.delete_all_recipes_alert)) },
                                confirmButton  = {
                                    Button(
                                        onClick = { onDelete(); openDialog.value = false },
                                        //openDialog.value = false ,
                                        modifier = Modifier.width(126.dp),
                                        shape = RoundedCornerShape(15.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                                        border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                                    ) {
                                        Text(stringResource(id = R.string.delete_button))
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
                    }
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    @OptIn(ExperimentalMaterial3Api::class)
                    TopAppBar(
                        title = { Text(stringResource(id = R.string.app_name), fontSize = 22.sp) },

                        navigationIcon = {
                            IconButton(onClick = { scope.launch {drawerState.open() }}) {
                                Icon(Icons.Filled.Menu, contentDescription = stringResource(id = R.string.menu_icon_description))
                            }
                        },

//                        actions ={
//                            IconButton( onClick = { }
//                            ) {
//                                Icon(Icons.Filled.Search, contentDescription = stringResource(id = R.string.search_icon_description))
//                            }
//                        },

                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White,
                            titleContentColor = Color.Black,
                            navigationIconContentColor = Color(0xFF4DB6AC),
                            actionIconContentColor = Color(0xFF4DB6AC)
                        )
                    )
                },

                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { onNavigateToAddScreen() },
                        content = { Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.add_icon_description)) },
                        containerColor = Color.White,
                        contentColor = Color(0xFF4DB6AC),
                    )
                },

                floatingActionButtonPosition = FabPosition.Center,

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
                            .padding(bottom = 70.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            items(recipes) { recipe ->
                                RecipeCard(
                                    recipe = recipe,
                                    modifier = Modifier.padding(10.dp),
                                    onNavigateToRecipeView = onNavigateToRecipeView
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}



@Composable
fun RecipeCard(
    //index: Int,
    recipe: Recipe,
    modifier: Modifier = Modifier,
    onNavigateToRecipeView: () -> Unit,
){
    Button(onClick = {
        currentRecipe = recipe
        viewableIngredients = ingredientsStringToList(currentRecipe.ingredients)
        onNavigateToRecipeView()
    },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB2DFDB), contentColor = Color.Black),
    ) {
        Text(
            recipe.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
        )
    }
}
