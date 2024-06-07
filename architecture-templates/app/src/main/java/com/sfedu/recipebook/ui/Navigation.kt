
package com.sfedu.recipebook.ui

import android.content.Context
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
import com.sfedu.recipebook.ui.recipe.RecipeChangeScreen
import com.sfedu.recipebook.ui.recipe.RecipeListScreen
import com.sfedu.recipebook.ui.recipe.RecipeScreen


@Composable
fun MainNavigation(context: Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            RecipeListScreen(
                onNavigateToAddScreen = { navController.navigate("addScreen") },
                onNavigateToRecipeView = { navController.navigate("recipeView") },
                context = context,
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
                onNavigateToRecipeView = { navController.navigate("recipeView") },
                onNavigateToChange = { navController.navigate("changeScreen") },
                modifier = Modifier.padding(16.dp)
            )
        }
        composable("calculateScreen"){
            CalculateIngredientsScreen(
                onNavigateToRecipeView = { navController.navigate("recipeView") },
                modifier = Modifier.padding(16.dp)
            )
        }
        composable("changeScreen"){
            RecipeChangeScreen(
                onNavigateToMain = { navController.navigate("main") },
                onNavigateToRecipeView = { navController.navigate("recipeView") },
                onNavigateToChange = { navController.navigate("changeScreen") },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
