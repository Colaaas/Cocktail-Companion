package fr.ensim.android.cocktailcompanion.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.photoDataStore by preferencesDataStore("photos")

object PhotoStore {
    private val PHOTOS_KEY = stringSetPreferencesKey("photo_uris")

    suspend fun savePhotoUri(context: Context, uri: String) {
        context.photoDataStore.edit { prefs ->
            val current = prefs[PHOTOS_KEY] ?: emptySet()
            prefs[PHOTOS_KEY] = current + uri
        }
    }

    suspend fun savePhotoUris(context: Context, uris: Set<String>) {
        context.photoDataStore.edit { prefs ->
            prefs[PHOTOS_KEY] = uris
        }
    }

    suspend fun loadPhotoUris(context: Context): List<String> {
        val prefs = context.photoDataStore.data.first()
        return prefs[PHOTOS_KEY]?.toList() ?: emptyList()
    }
}