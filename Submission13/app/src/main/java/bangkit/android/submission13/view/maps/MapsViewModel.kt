package bangkit.android.submission13.view.maps

import androidx.lifecycle.ViewModel
import bangkit.android.submission13.data.Repository

class MapsViewModel(private val repository: Repository): ViewModel() {

    fun getLocation() = repository.getLocation()

}