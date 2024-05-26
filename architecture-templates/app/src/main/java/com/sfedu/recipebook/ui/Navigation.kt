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

package com.sfedu.recipebook.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sfedu.recipebook.data.local.database.Recipe
import com.sfedu.recipebook.ui.recipe.CalculateIngredientsScreen
import com.sfedu.recipebook.ui.recipe.RecipeAddScreen
import com.sfedu.recipebook.ui.recipe.RecipeListScreen
import com.sfedu.recipebook.ui.recipe.RecipeScreen


@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            RecipeListScreen(
                onNavigateToAddScreen = { navController.navigate("addScreen") },
                onNavigateToRecipeView = { navController.navigate("recipeView") },
                modifier = Modifier.padding(16.dp)
            )
        }
        composable("addScreen"){
            RecipeAddScreen(
                onNavigateToMain = { navController.navigate("main") },
                modifier = Modifier.padding(16.dp)
            )
        }
        composable("recipeView"){
            RecipeScreen(
                onNavigateToMain = { navController.navigate("main") },
                onNavigateToCalc = { navController.navigate("calculateScreen") },
                modifier = Modifier.padding(16.dp)
            )
        }
        composable("calculateScreen"){
            CalculateIngredientsScreen(
                onNavigateToRecipeView = { navController.navigate("recipeView") },
                modifier = Modifier.padding(16.dp)
            )
        }
        // TODO: Add more destinations
    }
}
