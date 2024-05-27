package com.sfedu.recipebook.ui.recipe

import android.widget.Toast
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
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp, start = 5.dp, end = 5.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = difficulty,
                onValueChange = { difficulty = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding( start = 5.dp, end = 5.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = cookingTime.toString(),
                onValueChange = { cookingTime = it.toIntOrNull()?:0 },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding( start = 5.dp, end = 5.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = servingSize.toString(),
                onValueChange = { servingSize = it.toIntOrNull() ?: 0 },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding( start = 5.dp, end = 5.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                )
            )
            Spacer(modifier = Modifier.height(5.dp))

            ingredients = ingredientsListToString(IngredientList(Modifier.fillMaxWidth().padding( start = 5.dp)))

            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = recipeSteps,
                modifier = Modifier.fillMaxWidth().padding( start = 5.dp, end = 5.dp),
                onValueChange = { recipeSteps = it }
            )
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                onClick = {
                onSave(
                    nameRecipe,
                    difficulty,
                    cookingTime,
                    servingSize,
                    ingredients,
                    recipeSteps
                )
                onNavigateToMain()
            },
                modifier = Modifier.width(116.dp).padding( start = 5.dp, end = 5.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                ) {
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

        Row() {
            TextField(
                value = newIngredientName,
                onValueChange = { newIngredientName = it },
                modifier = Modifier.fillMaxWidth().padding(  end = 5.dp),
            )

            Spacer(modifier = Modifier.height(5.dp))
        }

        Row(){
            TextField(
                value = newIngredientQuantity.toString(),
                onValueChange = { newIngredientQuantity = round2Characters(it.toDoubleOrNull() ?: 0.0 )},
                modifier = Modifier.weight(1f).padding(top = 5.dp ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = newIngredientMeasure,
                onValueChange = { newIngredientMeasure = it },
                modifier = Modifier.weight(2f).padding(top = 5.dp, end = 5.dp)
            )
            Button(
                onClick = {
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
                },
                modifier = Modifier.width(116.dp).padding(top = 10.dp, end = 5.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                border = BorderStroke(2.dp, Color(0xFF4DB6AC))
            ) {
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