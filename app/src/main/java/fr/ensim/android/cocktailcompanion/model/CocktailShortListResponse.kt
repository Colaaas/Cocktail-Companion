package fr.ensim.android.cocktailcompanion.model

data class CocktailShort(
    val idDrink: String?,
    val strDrink: String?,
    val strDrinkThumb: String?
)

data class CocktailShortListResponse(
    val drinks: List<CocktailShort>?
)
