import fr.ensim.android.cocktailcompanion.model.CocktailResponse
import fr.ensim.android.cocktailcompanion.model.CocktailShortListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("search.php")
    suspend fun searchCocktail(@Query("s") name: String): CocktailResponse

    @GET("lookup.php")
    suspend fun lookupCocktail(@Query("i") id: String): CocktailResponse

    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse

    @GET("filter.php")
    suspend fun filterByAlcoholic(@Query("a") type: String): CocktailShortListResponse

    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): CocktailShortListResponse

    @GET("filter.php")
    suspend fun filterByGlass(@Query("g") glass: String): CocktailShortListResponse

    @GET("filter.php")
    suspend fun filterByIngredient(@Query("i") ingredient: String): CocktailShortListResponse
}
