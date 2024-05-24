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

package com.sfedu.recipebook.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.sfedu.recipebook.data.RecipeRepository
import com.sfedu.recipebook.data.DefaultRecipeRepository
import com.sfedu.recipebook.data.local.database.Recipe
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsRecipeRepository(
        recipeRepository: DefaultRecipeRepository
    ): RecipeRepository
}

class FakeRecipeRepository @Inject constructor() : RecipeRepository {
    override val recipes: Flow<List<Recipe>> = flowOf(listOf())

    override suspend fun add(
        name: String,
        imageId: Int,
        difficulty: String,
        cookingTime: Int,
        servingSize: Int,
        ingredients: String,
        recipeSteps: String
        ) {
        throw NotImplementedError()
    }

    override suspend fun getRecipeById(recipeId: Int): Recipe {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipesByName(name: String): Flow<List<Recipe>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecipeById(recipeId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    /* override suspend fun deleteRecipeById(recipeId: Int) {
         TODO("Not yet implemented")
     }*/
}

val fakeRecipes = listOf("One", "Two", "Three")
