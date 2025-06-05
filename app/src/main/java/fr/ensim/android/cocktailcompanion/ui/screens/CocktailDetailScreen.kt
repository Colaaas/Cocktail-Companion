package fr.ensim.android.cocktailcompanion.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import fr.ensim.android.cocktailcompanion.model.Cocktail
import fr.ensim.android.cocktailcompanion.model.Ingredient
import fr.ensim.android.cocktailcompanion.model.getIngredients
import fr.ensim.android.cocktailcompanion.ui.components.IngredientItem
import androidx.compose.foundation.layout.FlowRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailDetailScreen(cocktail: Cocktail, navController: NavController) {
    val ingredients: List<Ingredient> = cocktail.getIngredients().map { (quantite, nom) ->
        Ingredient(
            nom = nom,
            quantite = quantite ?: "",
            imageUrl = "https://www.thecocktaildb.com/images/ingredients/${nom.replace(" ", "%20")}-Small.png"
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // TopAppBar avec bouton retour
        TopAppBar(
            title = { Text("Détail du cocktail") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Nom du cocktail
                Text(
                    text = cocktail.strDrink ?: "Nom inconnu",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
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
            cocktail.strTags?.let { tags ->
                val tagList = mutableListOf<String>()
                cocktail.strCategory?.let { tagList.add(it) }
                tagList.addAll(tags.split(",").map { it.trim() })

                item {
                    Text(text = "Tags", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        tagList.forEach { tag ->
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }


            item {
                // Infos supplémentaires : sans alcool, verre
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (cocktail.strAlcoholic?.equals("Non alcoholic", ignoreCase = true) == true) {
                        Text(
                            text = "Sans alcool",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    cocktail.strGlass?.let { glass ->
                        Text(
                            text = "À servir dans : $glass",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }

            // Recette
            item {
                Text(text = "Recette", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = cocktail.strInstructionsFR ?: cocktail.strInstructions ?: "Recette non disponible")
            }

            // Ingrédients
            item {
                Text(text = "Ingrédients", style = MaterialTheme.typography.titleMedium)
            }
            items(ingredients) { ingredient ->
                IngredientItem(ingredient)
            }
        }
    }
}
