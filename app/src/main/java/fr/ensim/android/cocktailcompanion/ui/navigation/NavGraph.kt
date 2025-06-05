package fr.ensim.android.cocktailcompanion.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.ensim.android.cocktailcompanion.model.Cocktail
import fr.ensim.android.cocktailcompanion.ui.components.CocktailGrid
import fr.ensim.android.cocktailcompanion.ui.screens.CocktailDetailScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    cocktails: List<Cocktail>,
    selectedCocktail: Cocktail?,
    onCocktailSelected: (Cocktail) -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            CocktailGrid(
                cocktails = cocktails,
                onCocktailClick = { selectedCocktail ->
                    onCocktailSelected(selectedCocktail)
                    navController.navigate(Screen.Detail.route)
                }
            )
        }
        composable(Screen.Detail.route) {
            selectedCocktail?.let { CocktailDetailScreen(it) }
        }
    }
}