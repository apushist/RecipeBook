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
            recipeRepository.add(name,0,difficulty,cookingTime,servingSize,ingredients,recipeSteps)
           // TODO Add imageId

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
