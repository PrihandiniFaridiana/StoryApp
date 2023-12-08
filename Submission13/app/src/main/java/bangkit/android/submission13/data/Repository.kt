package bangkit.android.submission13.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import bangkit.android.submission13.data.dataStore.TokenPreference
import bangkit.android.submission13.data.dataStore.UserToken
import bangkit.android.submission13.network.ApiConfig
import bangkit.android.submission13.network.ApiService
import bangkit.android.submission13.response.ErrorResponse
import bangkit.android.submission13.response.ListStoryItem
import bangkit.android.submission13.response.LoginResponse
import bangkit.android.submission13.response.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class Repository private constructor(private val apiService: ApiService, private val tokenPreference: TokenPreference){

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService, tokenPreference: TokenPreference
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, tokenPreference)
            }.also { instance = it }
    }

    fun register(name: String, email: String, password: String): LiveData<Condition<RegisterResponse>> = liveData {
        try {
            val response = apiService.register(name, email, password)
            if (!response.error) {
                emit(Condition.Success(response))
            } else {
                emit(Condition.Error(response.message))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Condition.Error("Failed to register $errorMessage"))
        }
    }

    fun login(email: String, password: String): LiveData<Condition<LoginResponse>> = liveData {
        try {
            val response = apiService.login(email, password)
            if (!response.error) {
                val userToken = UserToken(
                    name = response.loginResult.name,
                    userId = response.loginResult.userId,
                    token = response.loginResult.token,
                    isLogin = true
                )
//                ApiConfig.token = response.loginResult.token
                tokenPreference.saveToken(userToken)
                emit(Condition.Success(response))
            } else {
                emit(Condition.Error(response.message))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Condition.Error("Failed to login $errorMessage"))
        }
    }

    suspend fun saveToken(userToken: UserToken) {
        return tokenPreference.saveToken(userToken)
    }

    fun getToken(): Flow<UserToken> {
        return tokenPreference.getToken()
    }

    suspend fun logout() {
        tokenPreference.logout()
    }

    fun getAllStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, tokenPreference)
            }
        ).liveData
    }

    fun addNewStory(photoFile: File, desc: String) = liveData {
        val requestBody = desc.toRequestBody("text/plain".toMediaType())
        val requestPhotoFile = photoFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            photoFile.name,
            requestPhotoFile
        )
        try {
            val userPref = runBlocking { tokenPreference.getToken().first() }
            val response = ApiConfig.getApiService(userPref.token)
            val successResponse = response.postStory(multipartBody, requestBody)
            emit(Condition.Success(successResponse))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Condition.Error("Failed to add story $errorMessage"))
        }
    }

    fun getLocation(): LiveData<Condition<List<ListStoryItem>>> = liveData {
        try {
            val userPref = runBlocking { tokenPreference.getToken().first() }
            val response = ApiConfig.getApiService(userPref.token)
            val locationResponse = response.getStoriesWithLocation()
            val location = locationResponse.listStory
            emit(Condition.Success(location))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Condition.Error("Failed to load location $errorMessage"))
        }
    }

}