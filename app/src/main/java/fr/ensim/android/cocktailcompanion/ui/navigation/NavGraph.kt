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
    object Detail : Screen("detail/{cocktailId}")
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
                    navController.navigate(Screen.Detail.route + "/${selectedCocktail.idDrink}")
                }
            )
        }
        composable(Screen.Detail.route + "/{cocktailId}") { backStackEntry ->
            val cocktailId = backStackEntry.arguments?.getString("cocktailId")
            val selected = cocktails.find { it.idDrink == cocktailId }
            selected?.let {
                CocktailDetailScreen(it, navController)
            }
        }
    }
}