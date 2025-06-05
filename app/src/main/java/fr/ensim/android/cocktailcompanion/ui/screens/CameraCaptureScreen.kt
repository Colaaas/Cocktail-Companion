package fr.ensim.android.cocktailcompanion.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun CameraCaptureScreen() {
    val context = LocalContext.current
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var imageFile by remember { mutableStateOf<File?>(null) }
    var hasCameraPermission by remember { mutableStateOf(false) }
    var photoUris by remember { mutableStateOf(listOf<Uri>()) } // Liste des photos prises

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && photoUri != null) {
            photoUris = photoUris + photoUri!! // Ajoute la photo à la galerie
        }
    }

    Column {
        Text(
            text = "Vos photos de cocktails",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                val file = File(context.cacheDir, "photo_${System.currentTimeMillis()}.jpg")
                imageFile = file
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )
                photoUri = uri
                launcher.launch(uri)
            },
            enabled = hasCameraPermission
        ) {
            Text("Prendre une photo")
        }

        // Affiche la dernière photo prise (optionnel)
        photoUris.lastOrNull()?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Dernière photo prise",
                modifier = Modifier.padding(8.dp)
            )
        }

        // Affiche la galerie de toutes les photos prises
        if (photoUris.isNotEmpty()) {
            Text(
                text = "Galerie",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, start = 8.dp)
            )
            // Affichage horizontal des miniatures
            androidx.compose.foundation.lazy.LazyRow(
                modifier = Modifier.padding(8.dp)
            ) {
                items(photoUris.size) { index ->
                    Image(
                        painter = rememberAsyncImagePainter(photoUris[index]),
                        contentDescription = "Photo $index",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(80.dp)
                    )
                }
            }
        }
    }
}