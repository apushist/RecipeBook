/*
 * Copyright (C) 2022 The Android Open Source Project
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.sfedu.recipebook.data.local.database.Recipe
import com.sfedu.recipebook.ui.recipe.currentRecipe
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import com.sfedu.recipebook.R
import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment


@Composable
fun RecipeListScreen(
    onNavigateToAddScreen: () -> Unit,
    onNavigateToRecipeView: () -> Unit,
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
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("RecipeBook", fontSize = 22.sp) },
                navigationIcon = {
                    IconButton( onClick = { }
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = "Меню")
                      }
                },
                actions ={
                    IconButton( onClick = { }
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Поиск")
                    }
                },
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
                content = { Icon(Icons.Filled.Add, contentDescription = "Добавить") },
                onClick = { onNavigateToAddScreen() },

            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ){
        Box(
            modifier = Modifier.fillMaxSize()
                               .padding(it)
                               .background(Color(0xFFE0F2F1)),
            //contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(bottom = 24.dp)
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
        //colors = ButtonDefaults.textButtonColors(),
        //modifier = Modifier.padding(10.dp),
    ) {
        Text(
            recipe.name,
            modifier = Modifier
                //.padding(0.dp, 40.dp, 10.dp, 10.dp
                //.background(
                //  if(index%2==0) Color(0xFFC8E6C9) else Color(0xFFE8F5E9) )
                .fillMaxWidth()
                .height(20.dp),
            //style = TextStyle(textIndent = TextIndent(15.sp, 10.sp), fontSize = 26.sp,)

        )

    }

}
