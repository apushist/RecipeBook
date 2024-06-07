
package com.sfedu.recipebook.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.sfedu.recipebook.data.local.database.Recipe
import com.sfedu.recipebook.data.local.database.RecipeDao
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface RecipeRepository {
    val recipes: Flow<List<Recipe>>

    suspend fun add(
        name: String,
        imageId: Int,
        difficulty: String,
        cookingTime: Int,
        servingSize: Int,
        ingredients: String,
        recipeSteps: String
    )

    suspend fun getRecipeById(recipeId: Int):Recipe?
    suspend fun getRecipesByName(name: String):Flow<List<Recipe>>
    suspend fun deleteRecipeById(recipeId: Int)
    suspend fun deleteAll()

    suspend fun deleteRecipe(recipe: Recipe)

    suspend fun updateRecipe(recipe: Recipe)

}

class DefaultRecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao
) : RecipeRepository {

    override val recipes: Flow<List<Recipe>> =
        recipeDao.getAllRecipes()

    override suspend fun add(
        name: String,
        imageId: Int,
        difficulty: String,
        cookingTime: Int,
        servingSize: Int,
        ingredients: String,
        recipeSteps: String
    ) {
        recipeDao.insertRecipe(Recipe(
            name = name,
            imageId = imageId,
            difficulty = difficulty,
            cookingTime = cookingTime,
            servingSize = servingSize,
            ingredients = ingredients,
            recipeSteps = recipeSteps
        ))
    }

    override suspend fun getRecipeById(recipeId: Int): Recipe? {
        return recipeDao.getRecipeById(recipeId)
    }

    override suspend fun getRecipesByName(name: String): Flow<List<Recipe>> {
        return recipeDao.getRecipesByName(name)
    }

    override suspend fun deleteRecipeById(recipeId: Int) {
        val recipe = getRecipeById(recipeId)
        if(recipe != null)
            recipeDao.deleteRecipe(recipe)
    }

    override suspend fun deleteAll() {
        recipeDao.deleteAll()
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }

    override suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
    }
}
