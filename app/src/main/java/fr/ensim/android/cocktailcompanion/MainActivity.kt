package fr.ensim.android.cocktailcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import fr.ensim.android.cocktailcompanion.model.Cocktail
import fr.ensim.android.cocktailcompanion.ui.components.Footer
import fr.ensim.android.cocktailcompanion.ui.navigation.NavGraph
import fr.ensim.android.cocktailcompanion.ui.screens.SearchScreen
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
                val navController = rememberNavController()

                var selectedCocktail by remember { mutableStateOf<Cocktail?>(null) }
                var currentScreen by remember { mutableStateOf("main") }

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Contenu principal
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        when (currentScreen) {
                            "main" -> {
                                NavGraph(
                                    navController = navController,
                                    cocktails = cocktails,
                                    selectedCocktail = selectedCocktail,
                                    onCocktailSelected = { cocktail ->
                                        selectedCocktail = cocktail
                                        navController.navigate("detail")
                                    }
                                )
                            }

                            "search" -> {
                                SearchScreen(
                                    viewModel = cocktailViewModel,
                                    onCocktailSelected = { cocktail ->
                                        selectedCocktail = cocktail
                                        currentScreen = "main"
                                        navController.navigate("detail")
                                    }
                                )
                            }
                        }
                    }

                    // Footer avec navigation
                    Footer(
                        onHomeClick = { currentScreen = "main" },
                        onSearchClick = { currentScreen = "search" }
                    )
                }
            }
        }
    }
}
