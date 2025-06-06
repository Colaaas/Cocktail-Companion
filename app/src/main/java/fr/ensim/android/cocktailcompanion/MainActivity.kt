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
import fr.ensim.android.cocktailcompanion.ui.navigation.Screen
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

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Contenu principal
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        NavGraph(
                            navController = navController,
                            cocktails = cocktails,
                            selectedCocktail = selectedCocktail,
                            onCocktailSelected = { cocktail ->
                                selectedCocktail = cocktail
                                navController.navigate(Screen.Detail.route.replace("{cocktailId}",
                                    cocktail.idDrink.toString()
                                ))
                            },
                            viewModel = cocktailViewModel
                        )
                    }

                    // Footer avec navigation
                    Footer(
                        onHomeClick = { navController.navigate(Screen.Home.route) },
                        onSearchClick = { navController.navigate(Screen.SearchHome.route) },
                        onPictureClick = { navController.navigate(Screen.Camera.route) }
                    )
                }
            }
        }
    }
}