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

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.sfedu.recipebook.data.RecipeRepository
import com.sfedu.recipebook.data.local.database.Recipe
import com.sfedu.recipebook.ui.recipe.RecipeUiState.Error
import com.sfedu.recipebook.ui.recipe.RecipeUiState.Loading
import com.sfedu.recipebook.ui.recipe.RecipeUiState.Success
import javax.inject.Inject
import kotlin.math.roundToInt


var currentRecipe = Recipe("name",0,"difficulty",
    10,1,"ingredient:0.0:measure;","recipeSteps")

var viewableIngredients: MutableList<Triple<String, Double,String>> = ingredientsStringToList(currentRecipe.ingredients)


@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    val uiState: StateFlow<RecipeUiState> = recipeRepository
        .recipes.map<List<Recipe>, RecipeUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addRecipe(
        name: String,
        //imageId: Int,
        difficulty: String,
        cookingTime: Int,
        servingSize: Int,
        ingredients: String,
        recipeSteps: String
    ) {
        viewModelScope.launch {
            if(checkRecipeCorrectness(
                name,
                //imageId,
                difficulty,
                cookingTime,
                servingSize,
                ingredients,
                recipeSteps
            ))
                recipeRepository.add(name,0,difficulty,cookingTime,servingSize,ingredients,recipeSteps)
           // TODO Add imageId сделаем к защите, надеюсь

        }
    }

    fun getRecipeById(recipeId: Int): Recipe?{
        var res: Recipe? = null
        viewModelScope.launch {
            res = recipeRepository.getRecipeById(recipeId)
        }
        return res
    }

    fun deleteAll(){
        viewModelScope.launch {
            recipeRepository.deleteAll()
        }
    }

    fun deleteRecipe(recipe: Recipe){
        viewModelScope.launch {
            recipeRepository.deleteRecipe(recipe)
        }
    }

}

sealed interface RecipeUiState {
    object Loading : RecipeUiState
    data class Error(val throwable: Throwable) : RecipeUiState
    data class Success(val data: List<Recipe>) : RecipeUiState
}

fun ingredientsListToString(ingredientsList:List<Triple<String, Double,String>>):String{
    val sb = StringBuilder()
    for(component in ingredientsList){
        sb.append("${component.first}:${component.second}:${component.third}").append(";")
    }
    return sb.toString()
}

fun ingredientsStringToList(ingredients: String):MutableList<Triple<String, Double,String>>{
    val ingredientsStr = ingredients.split(";")
    val result: MutableList<Triple<String, Double,String>> = mutableListOf()
    for(ing in ingredientsStr){
        val ingredientComp = ing.split(":")
        if(ingredientComp.size == 3)
            result.add(Triple(ingredientComp[0],ingredientComp[1].toDouble(),ingredientComp[2]))
    }
    return result
}

fun checkRecipeCorrectness(
    name: String,
    //imageId: Int,
    difficulty: String,
    cookingTime: Int,
    servingSize: Int,
    ingredients: String,
    recipeSteps: String
) = name != "" &&
    difficulty != "" &&
    cookingTime > 0 &&
    servingSize > 0 &&
    ingredients != "" &&
    recipeSteps != ""

fun round2Characters(number: Double) = (number*100).roundToInt() / 100.0

fun ingredientTripleToString(ingredient:Triple<String, Double,String>): String{
    return "${ingredient.first} ${ingredient.second} ${ingredient.third}"
}

fun changeViewableIngredients(multiplier: Double){
    for(i in 0..<viewableIngredients.size){
        val ingredient = viewableIngredients[i]
        viewableIngredients[i] = Triple(ingredient.first,
            round2Characters(currentRecipe.ingredientsList[i].second*multiplier),
            ingredient.third)
    }
}

fun resetViewableIngredients(ingredients: MutableState<MutableList<Triple<String, Double, String>>>) {
    ingredients.value = ingredientsStringToList(currentRecipe.ingredients)
    viewableIngredients = ingredientsStringToList(currentRecipe.ingredients)
}