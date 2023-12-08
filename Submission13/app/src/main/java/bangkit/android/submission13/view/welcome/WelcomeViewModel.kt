package bangkit.android.submission13.view.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import bangkit.android.submission13.data.Repository

class WelcomeViewModel(private val repository: Repository): ViewModel() {

    fun getToken() = repository.getToken().asLiveData()

}