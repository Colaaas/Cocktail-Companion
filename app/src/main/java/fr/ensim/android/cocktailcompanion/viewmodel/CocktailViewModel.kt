package fr.ensim.android.cocktailcompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.ensim.android.cocktailcompanion.data.api.ApiService
import fr.ensim.android.cocktailcompanion.model.Cocktail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CocktailViewModel : ViewModel() {

    private val _cocktails = MutableStateFlow<List<Cocktail>>(emptyList())
    val cocktails: StateFlow<List<Cocktail>> = _cocktails

    private val cocktailNames = listOf(
        "Negroni", "Mojito", "Margarita", "Martini Dry", "Daiquiri", "Manhattan",
        "Old Fashioned", "Whiskey Sour", "Cosmopolitan", "Bloody Mary", "Pina Colada",
        "Mai Tai", "Caipirinha", "Zombie", "Blue Lagoon", "Bahama Mama", "Hurricane",
        "Planter's Punch", "Tequila Sunrise", "Singapore Sling", "Mimosa", "Bellini",
        "Spritz", "Kir Royal", "French 75", "Irish Coffee", "Espresso Martini",
        "Hot Toddy", "B-52", "Black Russian", "White Russian", "Sidecar", "Sazerac",
        "Boulevardier", "Gin Fizz", "Cuba Libre", "Tom Collins", "Paloma",
        "Dark 'n' Stormy", "Moscow Mule", "Sex on the Beach", "Malibu Sunrise",
        "Passion Fruit Martini", "Strawberry Daiquiri", "Watermelon Mojito",
        "Long Island Iced Tea", "Amaretto Sour", "Gin Basil Smash", "Clover Club",
        "Tiki Punch"
    )

    fun loadPredefinedCocktails() {
        viewModelScope.launch {
            val results = cocktailNames.mapNotNull { name ->
                try {
                    val response = ApiService.api.searchCocktail(name)
                    response.drinks?.firstOrNull()
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
            _cocktails.value = results
        }
    }
}
