package fr.ensim.android.cocktailcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import fr.ensim.android.cocktailcompanion.ui.theme.CocktailAppTheme
import fr.ensim.android.cocktailcompanion.viewmodel.CocktailViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailAppTheme {
                val vm: CocktailViewModel = viewModel()
                val cocktail by vm.cocktail.collectAsState()

                LaunchedEffect(Unit) {
                    vm.fetchCocktail("Negroni")
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    if (cocktail != null) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = cocktail!!.strDrink ?: "No name", style = MaterialTheme.typography.headlineMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            AsyncImage(
                                model = cocktail!!.strDrinkThumb,
                                contentDescription = null,
                                modifier = Modifier.height(200.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = cocktail!!.strInstructions ?: "")
                        }
                    } else {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}