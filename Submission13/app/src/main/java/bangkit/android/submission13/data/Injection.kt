package bangkit.android.submission13.data

import android.content.Context
import bangkit.android.submission13.data.dataStore.TokenPreference
import bangkit.android.submission13.data.dataStore.dataStore
import bangkit.android.submission13.network.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = TokenPreference.getInstance(context.dataStore)
        val token = runBlocking { pref.getToken().first() }
        val apiService = ApiConfig.getApiService(token.token)
        return Repository.getInstance(apiService, pref)
    }
}