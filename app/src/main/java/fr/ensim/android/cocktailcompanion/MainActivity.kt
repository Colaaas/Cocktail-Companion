package fr.ensim.android.cocktailcompanion

import CocktailCard
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.ensim.android.cocktailcompanion.ui.components.*
import fr.ensim.android.cocktailcompanion.ui.theme.CocktailCompanionTheme
import fr.ensim.android.cocktailcompanion.viewmodel.CocktailViewModel


class MainActivity : ComponentActivity() {

    private val cocktailViewModel: CocktailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cocktailViewModel.loadPredefinedCocktails()

        setContent {
            CocktailCompanionTheme {
                val cocktails by cocktailViewModel.cocktails.collectAsState()

                Column(modifier = Modifier.fillMaxSize()) {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Header dans le lazy grid (pas scrollé séparément)
                        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                            AppTitle()
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        // Les cocktails en grille 2 colonnes
                        items(cocktails) { cocktail ->
                            CocktailCard(cocktail = cocktail)
                        }
                    }

                    Footer()
                }
            }
        }

    }
}
