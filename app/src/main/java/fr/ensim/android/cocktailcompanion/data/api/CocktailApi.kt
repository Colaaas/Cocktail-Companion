package fr.ensim.android.cocktailcompanion.data.api

import fr.ensim.android.cocktailcompanion.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
    @GET("search.php")
    suspend fun searchCocktail(@Query("s") name: String): CocktailResponse
}
