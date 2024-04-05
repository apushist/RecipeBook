package com.sfedu.recipebook

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecipeDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "allRecipes.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "recipeTab"
        private const val ID_COLUMN = "id"
        private const val TITLE_COLUMN = "title"
        private const val INGREDIENTS_COLUMN = "ingredients"
        private const val RECIPE_STEPS_COLUMN = "recipeSteps"
        private const val IMAGE_ID_COLUMN = "image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($ID_COLUMN INTEGER PRIMARY KEY, $TITLE_COLUMN TEXT, $INGREDIENTS_COLUMN TEXT," +
                "$RECIPE_STEPS_COLUMN TEXT, $IMAGE_ID_COLUMN INTEGER)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertRecipe(recipe: Recipe){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TITLE_COLUMN,recipe.title)
            put(IMAGE_ID_COLUMN,recipe.imageId)
            put(RECIPE_STEPS_COLUMN,recipe.recipeStepsListToString())
            put(INGREDIENTS_COLUMN,recipe.ingredientsMapToString())

        }
        db.insert(TABLE_NAME,null,values)
        db.close()

    }

    fun getAllRecipes():List<Recipe>{
        val recipes = mutableListOf<Recipe>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)
        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COLUMN))
            val imageId = cursor.getInt(cursor.getColumnIndexOrThrow(IMAGE_ID_COLUMN))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE_COLUMN))
            val ingredients = cursor.getString(cursor.getColumnIndexOrThrow(INGREDIENTS_COLUMN))
            val recipeSteps = cursor.getString(cursor.getColumnIndexOrThrow(RECIPE_STEPS_COLUMN))

            recipes.add(Recipe(id,imageId,title,ingredients, recipeSteps))
        }

        cursor.close()
        db.close()
        return recipes
    }

}