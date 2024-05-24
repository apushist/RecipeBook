package com.sfedu.recipebook.ui.recipe

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sfedu.recipebook.ui.currentRecipeId


@Composable
fun RecipeScreen(
    onNavigateToMain: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val recipe = viewModel.getRecipeById(currentRecipeId)
    if(recipe != null) {
        LazyColumn {
            item{
                Button(onClick = { onNavigateToMain() }) {
                    Text(text = "Return")
                }
            }
            item{
                Text(
                    text = recipe.name
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            item{
                ingredientsList(ingredients = recipe.ingredients)
            }
            item{
                Text(text = recipe.ingredients)
            }
        }
    }
}

@Composable
internal fun ingredientsList(
    ingredients: String
){
    
}