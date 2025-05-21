package fr.ensim.android.cocktailcompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.ensim.android.cocktailcompanion.model.Cocktail
import fr.ensim.android.cocktailcompanion.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CocktailViewModel : ViewModel() {
    private val _cocktail = MutableStateFlow<Cocktail?>(null)
    val cocktail: StateFlow<Cocktail?> = _cocktail

    fun fetchCocktail(name: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchCocktail(name)
                _cocktail.value = response.drinks?.firstOrNull()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
