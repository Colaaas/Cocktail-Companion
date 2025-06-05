package fr.ensim.android.cocktailcompanion.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import fr.ensim.android.cocktailcompanion.data.PhotoStore
import java.io.File
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed

@Composable
fun CameraCaptureScreen() {
    val context = LocalContext.current
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var imageFile by remember { mutableStateOf<File?>(null) }
    var hasCameraPermission by remember { mutableStateOf(false) }
    var photoUris by remember { mutableStateOf(listOf<Uri>()) }
    val scope = rememberCoroutineScope()

    // Pour l'affichage en grand
    var selectedPhoto by remember { mutableStateOf<Uri?>(null) }
    // Pour la confirmation de suppression
    var photoToDelete by remember { mutableStateOf<Uri?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Charger les photos sauvegardées au démarrage
    LaunchedEffect(Unit) {
        val savedUris = PhotoStore.loadPhotoUris(context).map { Uri.parse(it) }
        photoUris = savedUris
    }

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
            photoUris = photoUris + photoUri!!
            scope.launch {
                PhotoStore.savePhotoUri(context, photoUri.toString())
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Vos photos de cocktails",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )

            // Affiche la dernière photo prise au format 4:3, centrée
            (selectedPhoto ?: photoUris.lastOrNull())?.let { uri ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(4f / 3f)
                        .fillMaxWidth(0.8f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Photo sélectionnée",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            if (photoUris.isNotEmpty()) {
                Text(
                    text = "Galerie",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp, start = 8.dp)
                )
                LazyRow(
                    modifier = Modifier.padding(8.dp)
                ) {
                    itemsIndexed(photoUris) { index, uri ->
                        Box(
                            modifier = Modifier
                                .aspectRatio(4f / 3f)
                                .size(80.dp)
                                .padding(end = 8.dp),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            // Clic sur la miniature pour l'afficher en grand
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "Photo $index",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { selectedPhoto = uri },
                                contentScale = ContentScale.Crop
                            )
                            IconButton(
                                onClick = {
                                    photoToDelete = uri
                                    showDeleteDialog = true
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Supprimer la photo",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bouton en bas de l'écran
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
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
        }

        // Boîte de dialogue de confirmation de suppression
        if (showDeleteDialog && photoToDelete != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Confirmation") },
                text = { Text("Êtes-vous sûr de vouloir supprimer définitivement cette photo ?") },
                confirmButton = {
                    TextButton(onClick = {
                        val toRemove = photoToDelete
                        if (toRemove != null) {
                            photoUris = photoUris.filter { it != toRemove }
                            scope.launch {
                                PhotoStore.savePhotoUris(
                                    context,
                                    photoUris.map { it.toString() }.toSet()
                                )
                                // Suppression physique du fichier
                                try {
                                    val file = File(toRemove.path ?: "")
                                    if (file.exists()) file.delete()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                            if (selectedPhoto == toRemove) selectedPhoto = null
                        }
                        showDeleteDialog = false
                        photoToDelete = null
                    }) {
                        Text("Oui")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDeleteDialog = false
                        photoToDelete = null
                    }) {
                        Text("Non")
                    }
                }
            )
        }
    }
}