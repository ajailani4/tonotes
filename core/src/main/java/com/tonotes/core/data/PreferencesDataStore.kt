package com.tonotes.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tonotes.core.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            Constants.DataStore.PREFERENCES_NAME
        )
        private val ACCESS_TOKEN = stringPreferencesKey(Constants.DataStore.ACCESS_TOKEN_KEY)
        private val SELECTED_BACKUP_TYPE =
            intPreferencesKey(Constants.DataStore.SELECTED_BACKUP_TYPE)
    }

    suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
        }
    }

    fun getAccessToken() =
        context.dataStore.data.map { it[ACCESS_TOKEN] ?: "" }

    suspend fun saveSelectedBackupType(backupType: Int) {
        context.dataStore.edit {
            it[SELECTED_BACKUP_TYPE] = backupType
        }
    }

    fun getSelectedBackupType() =
        context.dataStore.data.map { it[SELECTED_BACKUP_TYPE] ?: 0 }
}