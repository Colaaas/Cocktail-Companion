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
    val cocktailCategories: List<String> = listOf(
        "Cocktail", "Ordinary Drink", "Punch / Party Drink", "Shake", "Other / Unknown", "Cocoa",
        "Shot", "Coffee / Tea", "Homemade Liqueur", "Beer", "Soft Drink"
    )
    val cocktailGlasses = listOf(
        "Highball glass", "Old-fashioned glass", "Cocktail glass", "Copper Mug", "Whiskey Glass",
        "Collins glass", "Pousse cafe glass", "Champagne flute", "Whiskey sour glass", "Brandy snifter",
        "White wine glass", "Nick and Nora Glass", "Hurricane glass", "Coffee mug", "Shot glass", "Jar",
        "Irish coffee cup", "Punch bowl", "Pitcher", "Pint glass", "Cordial glass", "Beer mug",
        "Margarita/Coupette glass", "Beer pilsner", "Beer Glass", "Parfait glass", "Wine Glass",
        "Mason jar", "Margarita glass", "Martini Glass", "Balloon Glass", "Coupe Glass"
    )
    val cocktails by viewModel.cocktails.collectAsState()
    var searchResults by remember { mutableStateOf<List<Cocktail>>(emptyList()) }
    var cocktailName by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    var expandedCategory by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var expandedGlass by remember { mutableStateOf(false) }
    var selectedGlass by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Recherche par nom", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
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
        Spacer(Modifier.height(16.dp))
        // Filtres regroupés
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(Modifier.padding(12.dp)) {
                // Filtre alcool
                Text("Par alcool")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        viewModel.filterByAlcoholic("Alcoholic") { searchResults = it }
                    }) { Text("Avec alcool") }
                    Button(onClick = {
                        viewModel.filterByAlcoholic("Non_Alcoholic") { searchResults = it }
                    }) { Text("Sans alcool") }
                }
                Spacer(Modifier.height(12.dp))
                // Filtre catégorie
                Text("Par catégorie")
                Box {
                    Button(onClick = { expandedCategory = true }) {
                        Text(selectedCategory ?: "Choisir une catégorie")
                    }
                    DropdownMenu(
                        expanded = expandedCategory,
                        onDismissRequest = { expandedCategory = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Aucun") },
                            onClick = {
                                expandedCategory = false
                                selectedCategory = null
                                viewModel.loadPredefinedCocktails()
                                searchResults = emptyList()
                            }
                        )
                        cocktailCategories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    expandedCategory = false
                                    selectedCategory = category
                                    viewModel.filterByCategory(category) { searchResults = it }
                                }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                // Filtre verre
                Text("Par type de verre")
                Box {
                    Button(onClick = { expandedGlass = true }) {
                        Text(selectedGlass ?: "Choisir un verre")
                    }
                    DropdownMenu(
                        expanded = expandedGlass,
                        onDismissRequest = { expandedGlass = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Aucun") },
                            onClick = {
                                expandedGlass = false
                                selectedGlass = null
                                viewModel.loadPredefinedCocktails()
                                searchResults = emptyList()
                            }
                        )
                        cocktailGlasses.forEach { glass ->
                            DropdownMenuItem(
                                text = { Text(glass) },
                                onClick = {
                                    expandedGlass = false
                                    selectedGlass = glass
                                    viewModel.filterByGlass(glass) { searchResults = it }
                                }
                            )
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
        } else if (cocktailName.isNotBlank() && cocktails.isNotEmpty()) {
            CocktailGrid(cocktails = cocktails, onCocktailClick = onCocktailSelected)
        } else if (searchResults.isNotEmpty()) {
            CocktailGrid(cocktails = searchResults, onCocktailClick = onCocktailSelected)
        } else {
            Text("Aucun résultat affiché pour le moment.", style = MaterialTheme.typography.bodyMedium)
        }
    }
}