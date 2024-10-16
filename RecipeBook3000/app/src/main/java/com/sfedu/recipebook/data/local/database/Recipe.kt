/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Copyright (C) 2024 Anastasiya Pushkova
 * Copyright (C) 2024 Anastasiya Ermilova
 *
 * This file was modified from a version of The Android Open Source Project
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
package com.sfedu.recipebook.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.sfedu.recipebook.ui.recipe.ingredientsStringToList
import kotlinx.coroutines.flow.Flow

@Entity
data class Recipe(
    var name: String,
    var imageId: Int,
    var difficulty: String,
    var cookingTime: Int,
    var servingSize: Int,
    var ingredients: String,
    var recipeSteps: String,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    @Ignore
    val ingredientsList : List<Triple<String, Double,String>> = ingredientsStringToList(ingredients)

}

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe ORDER BY uid DESC LIMIT 10")
    fun getRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe ORDER BY uid DESC ")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Transaction
    @Query("SELECT * FROM recipe WHERE uid = :recipeId")
    fun getRecipeById(recipeId: Int):  Recipe?

    @Transaction
    @Query("SELECT * FROM recipe WHERE name = :name")
    fun getRecipesByName(name: String):  Flow<List<Recipe>>

    @Insert
    suspend fun insertRecipe(item: Recipe)

    @Delete(entity = Recipe::class)
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM recipe")
    suspend fun deleteAll()

    @Update
    suspend fun updateRecipe(recipe: Recipe)

}
