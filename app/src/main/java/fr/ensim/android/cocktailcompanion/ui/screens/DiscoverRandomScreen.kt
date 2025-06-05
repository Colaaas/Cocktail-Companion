package fr.ensim.android.cocktailcompanion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.ensim.android.cocktailcompanion.model.Cocktail
import fr.ensim.android.cocktailcompanion.viewmodel.CocktailViewModel
import fr.ensim.android.cocktailcompanion.ui.components.CocktailGrid

@Composable
fun DiscoverRandomScreen(
    viewModel: CocktailViewModel,
    onCocktailSelected: (Cocktail) -> Unit
) {
    var cocktails by remember { mutableStateOf<List<Cocktail>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.loadRandomCocktails(6) {
            cocktails = it
            isLoading = false
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("6 cocktails au hasard", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
        } else {
            CocktailGrid(cocktails = cocktails, onCocktailClick = onCocktailSelected)
        }
    }
}