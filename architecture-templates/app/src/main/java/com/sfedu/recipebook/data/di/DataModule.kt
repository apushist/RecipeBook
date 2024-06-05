package com.sfedu.recipebook.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.sfedu.recipebook.data.RecipeRepository
import com.sfedu.recipebook.data.DefaultRecipeRepository
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
