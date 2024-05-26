package com.sfedu.recipebook.ui.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun RecipeScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToCalc: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val recipe = currentRecipe
    val ingredients = remember {
        mutableStateOf(viewableIngredients)
    }
    if(recipe != null) {
        Column {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onNavigateToMain() },

                    ) {
                    Text(text = "Return")
                }
                Button(
                    onClick = {
                        viewModel.deleteRecipe(recipe)
                        onNavigateToMain()
                    },

                    ) {
                    Text(text = "Delete")
                }
            }

            LazyColumn(modifier) {
                item {


                    Text(
                        text = recipe.name
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                items(ingredients.value) {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = ingredientTripleToString(it)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            resetViewableIngredients(ingredients = ingredients)
                            onNavigateToCalc()
                        }) {
                            Text(text = "Recalculate ingredients")
                        }
                        Button(    onClick = { resetViewableIngredients(ingredients = ingredients) }) {
                            Text(text = "Reset ingredients")
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = recipe.recipeSteps)
                }
            }
        }
    }
}
