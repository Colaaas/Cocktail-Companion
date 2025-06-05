package fr.ensim.android.cocktailcompanion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.ensim.android.cocktailcompanion.model.Cocktail
import fr.ensim.android.cocktailcompanion.viewmodel.CocktailViewModel
import fr.ensim.android.cocktailcompanion.ui.components.CocktailGrid

@Composable
fun DiscoverRandomScreen(
    viewModel: CocktailViewModel,
    navController: NavController // Ajouté pour le bouton retour
) {
    var cocktail by remember { mutableStateOf<Cocktail?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.loadRandomCocktails(1) { cocktails ->
            cocktail = cocktails.firstOrNull()
            isLoading = false
        }
    }

    if (isLoading) {
        CircularProgressIndicator(Modifier)
    } else {
        cocktail?.let {
            CocktailDetailScreen(cocktail = it, navController = navController)
        } ?: Text("Aucun cocktail trouvé")
    }
}