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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.sfedu.recipebook.data.local.database.Recipe
import com.sfedu.recipebook.ui.currentRecipe



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
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 24.dp),
        ){
        Button(
            modifier = Modifier.width(96.dp),
            onClick = {
            onNavigateToAddScreen()
        }) {
            Text("Add recipe")
        }
        Button(
            modifier = Modifier.width(96.dp),
            onClick = { onDelete() }) {
            Text("Delete all recipe")
        }
        LazyColumn {
            items(recipes){
                recipe -> RecipeCard(
                    recipe = recipe,
                    onNavigateToRecipeView = onNavigateToRecipeView
                    )
            }
        }
    }
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    onNavigateToRecipeView: () -> Unit,
){
    Button(onClick = {
        currentRecipe = recipe
        onNavigateToRecipeView()
    }) {
        Text(recipe.name +"\n"+ recipe.recipeSteps + "\n"+ recipe.ingredients )
    }

}



// Previews

/*@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        RecipeScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        RecipeScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}*/
