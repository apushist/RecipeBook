
package com.sfedu.recipebook.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Transaction
import com.sfedu.recipebook.ui.recipe.ingredientsStringToList
import kotlinx.coroutines.flow.Flow

@Entity
data class Recipe(
    val name: String,
    val imageId: Int,
    val difficulty: String,
    val cookingTime: Int,
    val servingSize: Int,
    val ingredients: String,
    val recipeSteps: String,
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

}
