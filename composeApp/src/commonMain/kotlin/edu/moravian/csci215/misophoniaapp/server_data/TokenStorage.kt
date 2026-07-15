package edu.moravian.csci215.misophoniaapp.server_data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

expect fun createDataStore(): DataStore<Preferences>

val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

internal const val dataStoreFileName = "tokens.preferences_pb"

object TokenStorage {
    private val dataStore: DataStore<Preferences> by lazy { createDataStore() }
    val accessToken: Flow<String?> = dataStore.data.map { preferences -> preferences[ACCESS_TOKEN_KEY]}
    val refreshToken: Flow<String?> = dataStore.data.map { preferences -> preferences[REFRESH_TOKEN_KEY]}

    suspend fun storeTokens(accessToken: String, refreshToken: String) {
            dataStore.edit {
                it[ACCESS_TOKEN_KEY] = accessToken
                it[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    suspend fun wipeTokens() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }
}

