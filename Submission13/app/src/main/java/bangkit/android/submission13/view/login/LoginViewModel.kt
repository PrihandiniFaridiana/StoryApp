package bangkit.android.submission13.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bangkit.android.submission13.data.Repository
import bangkit.android.submission13.data.dataStore.UserToken
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password)
    fun saveToken(userToken: UserToken) {
        viewModelScope.launch { repository.saveToken(userToken) }
    }

}