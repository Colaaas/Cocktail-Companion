package fr.ensim.android.cocktailcompanion.model

data class CocktailResponse(
    val drinks: List<Cocktail>?
)

data class Cocktail(
    val idDrink: String?,
    val strDrink: String?,
    val strDrinkThumb: String?,
    val strTags: String?,
    val strInstructions: String?,
    val strInstructionsFR: String?,
    val strCategory: String?,
    val strIBA: String?,
    val strAlcoholic: String?,
    val strGlass: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?
)

data class Ingredient(
    val nom: String,
    val quantite: String,
    val imageUrl: String
)

fun Cocktail.getIngredients(): List<Pair<String?, String>> {
    val ingredients = listOf(
        strMeasure1 to strIngredient1,
        strMeasure2 to strIngredient2,
        strMeasure3 to strIngredient3,
        strMeasure4 to strIngredient4,
        strMeasure5 to strIngredient5,
        strMeasure6 to strIngredient6,
        strMeasure7 to strIngredient7,
        strMeasure8 to strIngredient8,
        strMeasure9 to strIngredient9,
        strMeasure10 to strIngredient10
    )
    return ingredients.filterNot { it.second.isNullOrBlank() }.map {
        val quantity = if (it.second?.lowercase()?.contains("glaçon") == true) "à volonté" else it.first?.trim()
        quantity to it.second!!.trim()
    }

    fun Cocktail.toIngredientList(): List<Ingredient> {
        val ingredients = listOf(
            strMeasure1 to strIngredient1,
            strMeasure2 to strIngredient2,
            strMeasure3 to strIngredient3,
            strMeasure4 to strIngredient4,
            strMeasure5 to strIngredient5,
            strMeasure6 to strIngredient6,
            strMeasure7 to strIngredient7,
            strMeasure8 to strIngredient8,
            strMeasure9 to strIngredient9,
            strMeasure10 to strIngredient10
        )

        return ingredients.filterNot { it.second.isNullOrBlank() }.map { (measure, name) ->
            val nom = name!!.trim()
            val quantite = if (nom.lowercase().contains("glaçon")) "à volonté" else measure?.trim().orEmpty()
            val imageUrl = "https://www.thecocktaildb.com/images/ingredients/${nom.replace(" ", "%20")}-Small.png"
            Ingredient(nom, quantite, imageUrl)
        }
    }

}
