package com.sfedu.recipebook.ui.recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlin.reflect.KFunction6
import com.sfedu.recipebook.R

@Composable
fun RecipeChangeScreen(
    onNavigateToMain: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()) {
    RecipeChangeScreen(
        onNavigateToMain = onNavigateToMain,
        onUpdate = viewModel::updateRecipe,
        modifier = modifier
        )

}

@Composable
internal fun RecipeChangeScreen(
    onNavigateToMain: () -> Unit,
    onUpdate: KFunction6<String, String, Int, Int, String, String, Unit>,
    modifier: Modifier = Modifier
) {
    var nameRecipe by remember { mutableStateOf(currentRecipe.name) }
    var difficulty by remember { mutableStateOf(currentRecipe.difficulty) }
    var cookingTime by remember { mutableStateOf(currentRecipe.cookingTime) }
    var servingSize by remember { mutableStateOf(currentRecipe.servingSize) }
    var ingredients by remember { mutableStateOf(currentRecipe.ingredients) }
    var recipeSteps by remember { mutableStateOf(currentRecipe.recipeSteps) }
    //var imageRecipe by remember { mutableStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
    ) {

        item {

            TextField(
                value = nameRecipe,
                onValueChange = { nameRecipe = it },
                //supportingText = { Text(text = stringResource(id = R.string.recipe_name_text_field))},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, start = 5.dp, end = 5.dp),
                placeholder = { Text(stringResource(id = R.string.recipe_name_text_field)) },
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextField(
                value = difficulty,
                onValueChange = { difficulty = it },
                //supportingText = { Text(text = stringResource(id = R.string.difficulty_text_field))},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp),
                placeholder = { Text(stringResource(id = R.string.difficulty_text_field)) },
            )

            Spacer(modifier = Modifier.height(5.dp))

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

            )

            Spacer(modifier = Modifier.height(5.dp))

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

            )
            Spacer(modifier = Modifier.height(5.dp))

            ingredients = ingredientsListToString(IngredientList(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp)))

            Spacer(modifier = Modifier.height(5.dp))

            TextField(
                value = recipeSteps,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp),
                onValueChange = { recipeSteps = it },
                placeholder = { Text(stringResource(id = R.string.recipe_steps_text_field)) },
            )

            Spacer(modifier = Modifier.height(5.dp))

            Button(
                onClick = {
                onUpdate(
                    nameRecipe,
                    difficulty,
                    cookingTime,//.toIntOrNull()?:0,
                    servingSize,//.toIntOrNull()?:0,
                    ingredients,
                    recipeSteps
                )
                onNavigateToMain()
            },
                modifier = Modifier
                    .width(116.dp)
                    .padding(start = 5.dp, end = 5.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                ) {
                Text("stringResource(id = R.string.save_button)")
            }
        }
    }
}


