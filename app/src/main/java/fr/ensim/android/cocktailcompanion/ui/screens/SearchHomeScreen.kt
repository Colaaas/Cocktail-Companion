package fr.ensim.android.cocktailcompanion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchHomeScreen(
    onSearchByName: () -> Unit,
    onDiscoverRandom: () -> Unit,
    onSearchByIngredient: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onSearchByName, modifier = Modifier.fillMaxWidth()) {
            Text("Rechercher Cocktail")
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onDiscoverRandom, modifier = Modifier.fillMaxWidth()) {
            Text("Découvrir 6 cocktails au hasard")
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onSearchByIngredient, modifier = Modifier.fillMaxWidth()) {
            Text("Rechercher un cocktail par ingrédient")
        }
    }
}