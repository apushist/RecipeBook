package com.sfedu.recipebook.ui.recipe

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlin.reflect.KFunction6

@Composable
fun RecipeAddScreen(
    onNavigateToMain: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()) {
    RecipeAddScreen(
        onNavigateToMain = onNavigateToMain,
        onSave = viewModel::addRecipe,
        modifier = modifier
        )

}

@Composable
internal fun RecipeAddScreen(
    onNavigateToMain: () -> Unit,
    onSave: KFunction6<String, String, Int, Int, String, String, Unit>,
    modifier: Modifier = Modifier
) {
    var nameRecipe by remember { mutableStateOf("Name") }
    var difficulty by remember { mutableStateOf("Easy") }
    var cookingTime by remember { mutableIntStateOf(10) }
    var servingSize by remember { mutableIntStateOf(1) }
    var ingredients by remember { mutableStateOf("Flour:1:cup;Milk:1:cup;") }
    var recipeSteps by remember { mutableStateOf("1. Mix all together\n2. Cook in oven") }
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
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = difficulty,
                onValueChange = { difficulty = it },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = cookingTime.toString(),
                onValueChange = { cookingTime = it.toInt() },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = servingSize.toString(),
                onValueChange = { servingSize = it.toInt() },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            ingredients = ingredientsListToString(IngredientList(Modifier.fillMaxWidth()))
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = recipeSteps,
                onValueChange = { recipeSteps = it }
            )
            Spacer(modifier = Modifier.height(5.dp))
            Button(modifier = Modifier.width(96.dp), onClick = {
                onSave(
                    nameRecipe,
                    difficulty,
                    cookingTime,
                    servingSize,
                    ingredients,
                    recipeSteps
                )
                onNavigateToMain()
            }) {
                Text("Save")
            }
        }
    }
}


@Composable
fun IngredientList(
    modifier: Modifier = Modifier
):List<Triple<String, Double,String>> {
    var ingredients by remember { mutableStateOf(mutableListOf<Triple<String, Double,String>>() ) }
    var newIngredientName by remember { mutableStateOf("IngredientName") }
    var newIngredientQuantity by remember { mutableDoubleStateOf(0.0) }
    var newIngredientMeasure by remember { mutableStateOf("IngredientMeasure") }

    Column(modifier) {


        Row(Modifier.padding(8.dp)) {
            TextField(
                value = newIngredientName,
                onValueChange = { newIngredientName = it },
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = newIngredientQuantity.toString(),
                onValueChange = { newIngredientQuantity = round2Characters(it.toDoubleOrNull() ?: 0.0 )},
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = newIngredientMeasure,
                onValueChange = { newIngredientMeasure = it },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                if(newIngredientName != "" &&
                    newIngredientQuantity != 0.0 &&
                    newIngredientMeasure != ""
                    )
                    ingredients.add(Triple(newIngredientName,
                        round2Characters(newIngredientQuantity),
                        newIngredientMeasure))
                newIngredientName = ""
                newIngredientQuantity = 0.0
                newIngredientMeasure = ""
            }) {
                Text("Add")
            }

        }
        ingredients.forEach {
            IngredientView(ingredient = it)
        }
    }
    return ingredients
}

@Composable
fun IngredientView(
    ingredient: Triple<String, Double,String>
){
    Row(Modifier.fillMaxWidth(0.8f)) {
        Text(
            text = ingredientTripleToString(ingredient)
        )
    }
}