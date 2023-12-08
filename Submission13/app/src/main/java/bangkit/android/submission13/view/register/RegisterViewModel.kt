package bangkit.android.submission13.view.register

import androidx.lifecycle.ViewModel
import bangkit.android.submission13.data.Repository

class RegisterViewModel(private val repository: Repository): ViewModel() {

    fun register(name: String, email: String, password: String) = repository.register(name, email, password)

}