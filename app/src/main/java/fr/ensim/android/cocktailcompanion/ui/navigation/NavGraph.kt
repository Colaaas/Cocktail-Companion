package fr.ensim.android.cocktailcompanion.ui.navigation

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.ensim.android.cocktailcompanion.model.Cocktail
import fr.ensim.android.cocktailcompanion.ui.components.CocktailGrid
import fr.ensim.android.cocktailcompanion.ui.screens.*
import fr.ensim.android.cocktailcompanion.viewmodel.CocktailViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{cocktailId}")
    object SearchHome : Screen("searchHome")
    object SearchByName : Screen("searchByName")
    object SearchByIngredient : Screen("searchByIngredient")
    object DiscoverRandom : Screen("discoverRandom")
    object Camera : Screen("camera")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    cocktails: List<Cocktail>,
    selectedCocktail: Cocktail?,
    onCocktailSelected: (Cocktail) -> Unit,
    viewModel: CocktailViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            CocktailGrid(
                cocktails = cocktails,
                onCocktailClick = { cocktail ->
                    navController.navigate(Screen.Detail.route.replace("{cocktailId}",
                        cocktail.idDrink.toString()
                    ))
                }
            )
        }
        composable("detail/{cocktailId}") { backStackEntry ->
            val cocktailId = backStackEntry.arguments?.getString("cocktailId")
            var cocktail by remember { mutableStateOf<Cocktail?>(null) }
            var isLoading by remember { mutableStateOf(true) }

            LaunchedEffect(cocktailId) {
                isLoading = true
                cocktail = cocktailId?.let { viewModel.fetchCocktailById(it) }
                isLoading = false
            }

            when {
                isLoading -> CircularProgressIndicator()
                cocktail != null -> CocktailDetailScreen(cocktail = cocktail!!, navController = navController)
                else -> Text("Cocktail introuvable")
            }
        }
        composable(Screen.SearchHome.route) {
            SearchHomeScreen(
                onSearchByName = { navController.navigate(Screen.SearchByName.route) },
                onDiscoverRandom = { navController.navigate(Screen.DiscoverRandom.route) },
                onSearchByIngredient = { navController.navigate(Screen.SearchByIngredient.route) }
            )
        }
        composable(Screen.SearchByName.route) {
            SearchByNameScreen(
                viewModel = viewModel,
                onCocktailSelected = { cocktail ->
                    navController.navigate(Screen.Detail.route.replace("{cocktailId}",
                        cocktail.idDrink.toString()
                    ))
                }
            )
        }
        composable(Screen.SearchByIngredient.route) {
            SearchByIngredientScreen(
                viewModel = viewModel,
                onCocktailSelected = { cocktail ->
                    navController.navigate(Screen.Detail.route.replace("{cocktailId}",
                        cocktail.idDrink.toString()
                    ))
                }
            )
        }
        composable(Screen.DiscoverRandom.route) {
            DiscoverRandomScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Screen.Camera.route) {
            CameraCaptureScreen()
        }
    }
}