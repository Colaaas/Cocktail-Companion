package fr.ensim.android.cocktailcompanion.ui.components

import CocktailCard
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.ensim.android.cocktailcompanion.model.Cocktail

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CocktailGrid(
    cocktails: List<Cocktail>,
    onCocktailClick: (Cocktail) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(cocktails) { cocktail ->
                CocktailCard(
                    cocktail = cocktail,
                    onClick = { onCocktailClick(cocktail) }
                )
            }
        }
    }
}