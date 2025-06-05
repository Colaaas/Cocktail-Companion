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
        "Long Island Iced Tea", "Amaretto Sour", "Gin Basil Smash", "Clover Club"
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

    fun searchCocktails(query: String) {
        viewModelScope.launch {
            try {
                if (query.isBlank()) {
                    loadPredefinedCocktails()
                } else {
                    val response = ApiService.api.searchCocktail(query)
                    _cocktails.value = response.drinks ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _cocktails.value = emptyList()
            }
        }
    }

    fun loadRandomCocktails(count: Int, onResult: (List<Cocktail>) -> Unit) {
        viewModelScope.launch {
            val results = mutableListOf<Cocktail>()
            repeat(count) {
                try {
                    val response = ApiService.api.getRandomCocktail()
                    response.drinks?.firstOrNull()?.let { results.add(it) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            onResult(results)
        }
    }

    fun filterByAlcoholic(type: String, onResult: (List<Cocktail>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiService.api.filterByAlcoholic(type)
                val drinks = response.drinks?.mapNotNull {
                    it.idDrink?.let { id -> ApiService.api.lookupCocktail(id).drinks?.firstOrNull() }
                } ?: emptyList()
                onResult(drinks)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(emptyList())
            }
        }
    }

    fun filterByCategory(category: String, onResult: (List<Cocktail>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiService.api.filterByCategory(category)
                val drinks = response.drinks?.mapNotNull {
                    it.idDrink?.let { id -> ApiService.api.lookupCocktail(id).drinks?.firstOrNull() }
                } ?: emptyList()
                onResult(drinks)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(emptyList())
            }
        }
    }

    fun filterByGlass(glass: String, onResult: (List<Cocktail>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiService.api.filterByGlass(glass)
                val drinks = response.drinks?.mapNotNull {
                    it.idDrink?.let { id -> ApiService.api.lookupCocktail(id).drinks?.firstOrNull() }
                } ?: emptyList()
                onResult(drinks)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(emptyList())
            }
        }
    }

    fun searchByIngredient(ingredient: String, onResult: (List<Cocktail>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiService.api.filterByIngredient(ingredient)
                val drinks = response.drinks?.mapNotNull {
                    it.idDrink?.let { id -> ApiService.api.lookupCocktail(id).drinks?.firstOrNull() }
                } ?: emptyList()
                onResult(drinks)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(emptyList())
            }
        }
    }
}


