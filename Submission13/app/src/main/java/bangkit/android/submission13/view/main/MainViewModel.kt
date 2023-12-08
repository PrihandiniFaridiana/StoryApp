package bangkit.android.submission13.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import bangkit.android.submission13.data.Repository
import bangkit.android.submission13.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getAllStories(): LiveData<PagingData<ListStoryItem>> = repository.getAllStories().cachedIn(viewModelScope)

}