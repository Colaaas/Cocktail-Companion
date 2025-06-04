package fr.ensim.android.cocktailcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.ensim.android.cocktailcompanion.model.Cocktail
import fr.ensim.android.cocktailcompanion.ui.components.*
import fr.ensim.android.cocktailcompanion.ui.theme.CocktailCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailCompanionTheme {
                // Exemple mock de cocktails
                val mockCocktail = Cocktail(
                    idDrink = "1",
                    strDrink = "Margarita",
                    strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
                    strTags = "Tag1,Tag2",
                    strInstructions = "Shake and serve.",
                    strIngredient1 = "Tequila",
                    strIngredient2 = "Triple sec",
                    strIngredient3 = "Lime juice",
                    strIngredient4 = null,
                    strIngredient5 = null,
                    strIngredient6 = null,
                    strIngredient7 = null,
                    strIngredient8 = null,
                    strIngredient9 = null,
                    strIngredient10 = null,
                    strMeasure1 = "1 1/2 oz",
                    strMeasure2 = "1/2 oz",
                    strMeasure3 = "1 oz",
                    strMeasure4 = null,
                    strMeasure5 = null,
                    strMeasure6 = null,
                    strMeasure7 = null,
                    strMeasure8 = null,
                    strMeasure9 = null,
                    strMeasure10 = null,
                    strCategory = "Ordinary Drink",
                    strIBA = "Contemporary Classics",
                    strAlcoholic = "Alcoholic",
                    strGlass = "Cocktail glass"
                )
                val cocktails = List(20) { mockCocktail.copy(idDrink = it.toString(), strDrink = "Cocktail $it") }

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppTitle()

                    // Zone scrollable avec la grille
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        CocktailGrid(cocktails = cocktails)
                    }

                    Footer()
                }
            }
        }
    }
}
