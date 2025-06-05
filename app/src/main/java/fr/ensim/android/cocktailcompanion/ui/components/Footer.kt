package fr.ensim.android.cocktailcompanion.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.LocalBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun Footer(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* TODO: navigate home */ }) {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = "Accueil",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(onClick = { /* TODO: search action */ }) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Rechercher",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(onClick = { /* TODO: open bar */ }) {
            Icon(
                imageVector = Icons.Outlined.LocalBar,
                contentDescription = "Icône de bar",
                tint = MaterialTheme.colorScheme.primary
            )

        }
    }
}
