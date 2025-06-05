package fr.ensim.android.cocktailcompanion.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import fr.ensim.android.cocktailcompanion.model.Cocktail
import fr.ensim.android.cocktailcompanion.model.Ingredient
import fr.ensim.android.cocktailcompanion.model.getIngredients
import fr.ensim.android.cocktailcompanion.ui.components.IngredientItem

@Composable
fun CocktailDetailScreen(cocktail: Cocktail) {
    val ingredients: List<Ingredient> = cocktail.getIngredients().map { (quantite, nom) ->
        Ingredient(
            nom = nom,
            quantite = quantite ?: "",
            imageUrl = "https://www.thecocktaildb.com/images/ingredients/${nom.replace(" ", "%20")}-Small.png"
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Nom du cocktail
            Text(
                text = cocktail.strDrink ?: "Nom inconnu",
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Image
            Image(
                painter = rememberImagePainter(data = cocktail.strDrinkThumb),
                contentDescription = cocktail.strDrink,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        // Tags
        cocktail.strTags?.let {
            item {
                Text(text = "Tags : $it")
            }
        }

        // Recette
        item {
            Text(text = "Recette", modifier = Modifier.padding(top = 8.dp))
            Text(text = cocktail.strInstructionsFR ?: cocktail.strInstructions ?: "Recette non disponible")
        }

        // Ingrédients
        item {
            Text(text = "Ingrédients", modifier = Modifier.padding(top = 8.dp))
        }
        items(ingredients) { ingredient ->
            IngredientItem(ingredient)
        }
    }
}
