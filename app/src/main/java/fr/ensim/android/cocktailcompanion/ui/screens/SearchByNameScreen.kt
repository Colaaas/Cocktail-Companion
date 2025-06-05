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
fun SearchByNameScreen(
    viewModel: CocktailViewModel,
    onCocktailSelected: (Cocktail) -> Unit
) {
    val cocktails by viewModel.cocktails.collectAsState()
    var cocktailName by remember { mutableStateOf("") }
    var isNonAlcoholic by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    // Filtrage combiné
    val filteredCocktails = cocktails.filter { cocktail ->
        (!isNonAlcoholic || cocktail.strAlcoholic?.contains("Non", ignoreCase = true) == true) &&
                (cocktailName.isBlank() || cocktail.strDrink?.contains(cocktailName, ignoreCase = true) == true)
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Recherche par nom", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(1.dp))
        OutlinedTextField(
            value = cocktailName,
            onValueChange = { cocktailName = it },
            label = { Text("Nom du cocktail (ex: Mojito)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    isLoading = true
                    viewModel.searchCocktails(cocktailName)
                    isLoading = false
                    focusManager.clearFocus()
                }
            ),
            trailingIcon = {
                IconButton(onClick = {
                    isLoading = true
                    viewModel.searchCocktails(cocktailName)
                    isLoading = false
                    focusManager.clearFocus()
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Rechercher")
                }
            }
        )
        Spacer(Modifier.height(1.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(vertical = 2.dp)
        ) {
            Text("Sans alcool")
            Switch(
                checked = isNonAlcoholic,
                onCheckedChange = { checked -> isNonAlcoholic = checked }
            )
        }
        Spacer(Modifier.height(1.dp))
        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
        } else if (filteredCocktails.isNotEmpty()) {
            CocktailGrid(cocktails = filteredCocktails, onCocktailClick = onCocktailSelected)
        } else {
            Text("Aucun résultat affiché pour le moment.", style = MaterialTheme.typography.bodyMedium)
        }
    }
}