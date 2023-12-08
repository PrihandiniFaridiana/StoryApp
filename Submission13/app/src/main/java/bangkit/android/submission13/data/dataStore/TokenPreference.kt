package bangkit.android.submission13.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class TokenPreference private constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        @Volatile
        private var INSTANCE: TokenPreference? = null
        private val KEY_NAME = stringPreferencesKey("name")
        private val KEY_ID = stringPreferencesKey("userid")
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_IS_LOGIN = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): TokenPreference{
            return INSTANCE ?: synchronized(this) {
                val instance = TokenPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun saveToken(userToken: UserToken) {
        dataStore.edit {preferences ->
            preferences[KEY_NAME] = userToken.name
            preferences[KEY_ID] = userToken.userId
            preferences[KEY_TOKEN] = userToken.token
            preferences[KEY_IS_LOGIN] = userToken.isLogin
        }
    }

    fun getToken(): Flow<UserToken> {
        return dataStore.data.map { preferences ->
            UserToken(
                preferences[KEY_TOKEN] ?: "",
                preferences[KEY_NAME] ?: "",
                preferences[KEY_ID] ?: "",
                preferences[KEY_IS_LOGIN] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}