package com.sfedu.recipebook.ui.recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em

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
        Column (
            modifier = Modifier .fillMaxWidth()
                                .background(Color(0xFFE0F2F1)),
        ){
            Row(
                Modifier.fillMaxWidth()
                        .background(Color.White)
                        .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        onNavigateToMain() },
                    modifier = Modifier.width(156.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                    border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                    ) {
                    Text(text = "Return")
                }
                Button(
                    onClick = {
                        viewModel.deleteRecipe(recipe)
                        onNavigateToMain() },
                    modifier = Modifier.width(156.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                    border = BorderStroke(2.dp, Color(0xFF4DB6AC))
                    ) {
                    Text(text = "Delete")
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(0.dp)) {
                item {
                    Row(
                        Modifier.fillMaxWidth()
                            //.background(Color.White)
                            .padding(10.dp, 10.dp, 10.dp, 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = recipe.name,
                            modifier = Modifier.width(256.dp)
                                .height(30.dp)
                                //.padding(10.dp, 10.dp, 10.dp, 0.dp)
                                .background(Color(0xFFB2DFDB)),
                            style = androidx.compose.ui.text.TextStyle(textIndent = TextIndent(10.sp, 5.sp), fontSize = 26.sp,),

                            )
                        Spacer(modifier = Modifier.height(20.dp))
                    }



                }
                items(ingredients.value) {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = ingredientTripleToString(it),
                            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    Row(
                        Modifier.fillMaxWidth()
                            .background(Color.White)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            resetViewableIngredients(ingredients = ingredients)
                            onNavigateToCalc() },

                            modifier = Modifier.width(156.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                            border = BorderStroke(2.dp, Color(0xFF4DB6AC)),
                        ) {
                            Text(text = "Recalculate ingredients")
                        }
                        Button(onClick = { resetViewableIngredients(ingredients = ingredients) },

                            modifier = Modifier.width(156.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1), contentColor = Color.Black),
                            border = BorderStroke(2.dp, Color(0xFF4DB6AC)),
                        ) {
                            Text(text = "Reset ingredients")
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = recipe.recipeSteps,
                        modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp),
                        )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}