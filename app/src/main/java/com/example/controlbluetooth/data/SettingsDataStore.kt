package com.example.controlbluetooth.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

// Create a DataStore instance using the preferencesDataStore delegate, with the Context as
// receiver.
private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCES_NAME
)
class SettingsDataStore(context: Context) {
    private val IS_FULL_MODE_LAYOUT = booleanPreferencesKey("is_full_mode_layout")
    private val WAS_IMAGE_ONE_SELECT = booleanPreferencesKey("was_image_one_selected")
    private val WAS_IMAGE_TWO_SELECT = booleanPreferencesKey("was_image_two_selected")
    private val WAS_IMAGE_THREE_SELECT = booleanPreferencesKey("was_image_three_selected")
    private val WAS_IMAGE_FOUR_SELECT = booleanPreferencesKey("was_image_four_selected")
    private val WAS_IMAGE_FIVE_SELECT = booleanPreferencesKey("was_image_five_selected")
    private val WAS_IMAGE_SIX_SELECT = booleanPreferencesKey("was_image_six_selected")
    private val WAS_IMAGE_SEVEN_SELECT = booleanPreferencesKey("was_image_seven_selected")
    private val WAS_IMAGE_EIGHT_SELECT = booleanPreferencesKey("was_image_eight_selected")
    private val WAS_IMAGE_NINE_SELECT = booleanPreferencesKey("was_image_nine_selected")


    suspend fun saveLayoutToPreferencesStore(isFullModeLayoutManager: Boolean,
                                             context: Context
                                             ){
        context.dataStore.edit {    preferences ->
            preferences[IS_FULL_MODE_LAYOUT] = isFullModeLayoutManager
        }
    }

    val preferenceFlow: Flow<Boolean> = context.dataStore.data
            // Manejo de posible error en lectura de datos en el preferences store
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            // On the first run of the app, we will use LinearLayoutManager by default
            preferences[IS_FULL_MODE_LAYOUT] ?: true
        }
}