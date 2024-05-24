package com.sfedu.recipebook.ui.recipe

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CalculateIngredientsScreen(
    onNavigateToRecipeView: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()
){

    LazyColumn(modifier) {
        item{
            Button(onClick = { onNavigateToRecipeView() }) {
                Text(
                    text = "Return"
                )
            }
        }
        //todo add calculation view как-то выбираем нужный ингредиент и его количество
        item{
            Button(onClick = {
                //todo add saving of viewableingredients
                onNavigateToRecipeView
            }) {
                Text(
                    text = "Calculate"
                )
            }
        }
    }
}