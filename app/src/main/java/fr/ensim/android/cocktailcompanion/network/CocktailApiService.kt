package fr.ensim.android.cocktailcompanion.network

import fr.ensim.android.cocktailcompanion.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {
    @GET("search.php")
    suspend fun searchCocktail(@Query("s") name: String): CocktailResponse
}
