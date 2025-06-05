package fr.ensim.android.cocktailcompanion

import com.google.gson.annotations.SerializedName
import fr.ensim.android.cocktailcompanion.model.Cocktail

data class CocktailResponse(
    @SerializedName("drinks") val drinks: List<Cocktail>?
)
