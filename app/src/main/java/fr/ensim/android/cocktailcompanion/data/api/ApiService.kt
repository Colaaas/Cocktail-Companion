package fr.ensim.android.cocktailcompanion.data.api

import CocktailApi
import fr.ensim.android.cocktailcompanion.CocktailResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    val api: CocktailApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CocktailApi::class.java)
    }

    suspend fun lookupCocktail(id: String): fr.ensim.android.cocktailcompanion.model.CocktailResponse {
        return api.lookupCocktail(id)
    }
}
