package fr.ensim.android.cocktailcompanion.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import fr.ensim.android.cocktailcompanion.model.Ingredient

@Composable
fun IngredientItem(ingredient: Ingredient) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(data = ingredient.imageUrl),
            contentDescription = ingredient.nom,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 8.dp)
        )
        Text(text = ingredient.nom, modifier = Modifier.weight(1f))
        Text(text = ingredient.quantite)
    }
}
