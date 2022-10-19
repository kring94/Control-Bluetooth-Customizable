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
    private val IMAGE_ONE_SELECT = booleanPreferencesKey("image_one_selected")
    private val IMAGE_TWO_SELECT = booleanPreferencesKey("image_two_selected")
    private val IMAGE_THREE_SELECT = booleanPreferencesKey("image_three_selected")
    private val IMAGE_FOUR_SELECT = booleanPreferencesKey("image_four_selected")
    private val IMAGE_FIVE_SELECT = booleanPreferencesKey("image_five_selected")
    private val IMAGE_SIX_SELECT = booleanPreferencesKey("image_six_selected")
    private val IMAGE_SEVEN_SELECT = booleanPreferencesKey("image_seven_selected")
    private val IMAGE_EIGHT_SELECT = booleanPreferencesKey("image_eight_selected")
    private val IMAGE_NINE_SELECT = booleanPreferencesKey("image_nine_selected")

    // Function and variable for save setting options
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
    // Function and variables for save imageButton selected
    suspend fun saveSelectedButtons(context: Context,
                                    stateButton: Boolean,
                                    idImage: Int
    ){
        context.dataStore.edit { selectedButton ->
            when(idImage){
                1 -> selectedButton[IMAGE_ONE_SELECT] = stateButton
                2 -> selectedButton[IMAGE_TWO_SELECT] = stateButton
                3 -> selectedButton[IMAGE_THREE_SELECT] = stateButton
                4 -> selectedButton[IMAGE_FOUR_SELECT] = stateButton
                5 -> selectedButton[IMAGE_FIVE_SELECT] = stateButton
                6 -> selectedButton[IMAGE_SIX_SELECT] = stateButton
                7 -> selectedButton[IMAGE_SEVEN_SELECT] = stateButton
                8 -> selectedButton[IMAGE_EIGHT_SELECT] = stateButton
                9 -> selectedButton[IMAGE_NINE_SELECT] = stateButton
            }
        }
    }

    val preferenceOneImage: Flow<Boolean> = context.dataStore.data
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
            preferences[IMAGE_ONE_SELECT] ?: true
        }

    val preferenceTwoImage: Flow<Boolean> = context.dataStore.data
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
            preferences[IMAGE_TWO_SELECT] ?: true
        }

    val preferenceThreeImage: Flow<Boolean> = context.dataStore.data
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
            preferences[IMAGE_THREE_SELECT] ?: true
        }

    val preferenceFourImage: Flow<Boolean> = context.dataStore.data
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
            preferences[IMAGE_FOUR_SELECT] ?: true
        }

    val preferenceFiveImage: Flow<Boolean> = context.dataStore.data
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
            preferences[IMAGE_FIVE_SELECT] ?: true
        }

    val preferenceSixImage: Flow<Boolean> = context.dataStore.data
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
            preferences[IMAGE_SIX_SELECT] ?: true
        }

    val preferenceSevenImage: Flow<Boolean> = context.dataStore.data
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
            preferences[IMAGE_SEVEN_SELECT] ?: true
        }

    val preferenceEightImage: Flow<Boolean> = context.dataStore.data
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
            preferences[IMAGE_EIGHT_SELECT] ?: true
        }

    val preferenceNineImage: Flow<Boolean> = context.dataStore.data
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
            preferences[IMAGE_NINE_SELECT] ?: true
        }
}