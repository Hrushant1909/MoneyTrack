package com.hrushant.moneytrack.data.local.session

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "moneytrack_preferences"
)


class SessionManager(
    private val context: Context
) {


    companion object{
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_ID = intPreferencesKey("user_id")
    }


    val isLoggedIn : Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    val loggedInUserId: Flow<Int?> = context.dataStore.data.map { preferences ->
        preferences[USER_ID]
    }

    suspend fun saveSession(userId: Int) {

        context.dataStore.edit { preferences ->

            preferences[IS_LOGGED_IN] = true
            preferences[USER_ID] = userId
        }
    }
    suspend fun clearSession() {

        context.dataStore.edit { preferences ->

            preferences[IS_LOGGED_IN] = false
            preferences.remove(USER_ID)
        }
    }

}