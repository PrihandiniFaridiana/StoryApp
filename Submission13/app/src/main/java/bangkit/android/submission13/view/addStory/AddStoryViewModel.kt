package bangkit.android.submission13.view.addStory

import androidx.lifecycle.ViewModel
import bangkit.android.submission13.data.Repository
import java.io.File

class AddStoryViewModel (private val repository: Repository): ViewModel() {

    fun addNewStory(photoFile: File, desc: String) = repository.addNewStory(photoFile, desc)

}