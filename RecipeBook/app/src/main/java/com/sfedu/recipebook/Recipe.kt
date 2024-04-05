package com.sfedu.recipebook

import kotlin.text.StringBuilder

data class Recipe(
    val id: Int,
    val imageId: Int,
    val title: String,
    private val ingredients: String,
    private val recipeSteps: String,
    ){

    val ingredientsWithCountMap: MutableMap<String,Double> = getIngedients(ingredients)
    val recipeStepsList: List<String> = getRecipeSteps(recipeSteps)


    private fun getIngedients(str: String): MutableMap<String,Double>{
        val ingWithCount = str.split(";")
        val result: MutableMap<String, Double> = mutableMapOf()
        for(i in 0..ingWithCount.count() step 2){
            result.put(ingWithCount[i],ingWithCount[i+1].toDouble())
        }
        return result
    }

    private fun getRecipeSteps(str: String): List<String> {
        return str.split(";")
    }

    public fun ingredientsMapToString():String{
        val sb = StringBuilder()
        for(component in ingredientsWithCountMap){
            sb.append(component.key).append(";").append(component.value).append(";")
        }
        return sb.toString()
    }

    public fun recipeStepsListToString():String{
        val sb = StringBuilder()
        for(step in recipeStepsList){
            sb.append(step).append(";")
        }
        return sb.toString()
    }
}



