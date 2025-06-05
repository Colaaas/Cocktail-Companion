package fr.ensim.android.cocktailcompanion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import fr.ensim.android.cocktailcompanion.model.Cocktail
import fr.ensim.android.cocktailcompanion.viewmodel.CocktailViewModel
import fr.ensim.android.cocktailcompanion.ui.components.CocktailGrid

@Composable
fun SearchByIngredientScreen(
    viewModel: CocktailViewModel,
    onCocktailSelected: (Cocktail) -> Unit
) {

    var ingredient by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Cocktail>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(Modifier.fillMaxSize().padding(8.dp)) {
        Text("Recherche par ingrédient", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = ingredient,
            onValueChange = { ingredient = it },
            label = { Text("Ingrédient (ex: Rum)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = ingredient.trim().contains(" "),
            supportingText = {
                if (ingredient.trim().contains(" ")) {
                    Text("Un seul ingrédient à la fois (ex: Rum)")
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    isLoading = true
                    if (ingredient.trim().contains(" ")) {
                        // Ne lance pas la recherche, ou affiche un message d'erreur
                        return@KeyboardActions
                    }
                    viewModel.searchByIngredient(ingredient) { results ->
                        searchResults = results
                        isLoading = false
                    }
                    focusManager.clearFocus()
                }
            ),
            trailingIcon = {
                IconButton(onClick = {
                    isLoading = true
                    if (ingredient.trim().contains(" ")) {
                        // Ne lance pas la recherche, ou affiche un message d'erreur
                        return@IconButton
                    }
                    viewModel.searchByIngredient(ingredient) { results ->
                        searchResults = results
                        isLoading = false
                    }
                    focusManager.clearFocus()
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Rechercher par ingrédient")
                }
            }
        )
        Spacer(Modifier.height(24.dp))
        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
        } else if (searchResults.isNotEmpty()) {
            CocktailGrid(cocktails = searchResults, onCocktailClick = onCocktailSelected)
        } else {
            Text("Aucun résultat affiché pour le moment.", style = MaterialTheme.typography.bodyMedium)
        }
    }
}