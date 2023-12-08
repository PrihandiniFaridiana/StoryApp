package bangkit.android.submission13.view.addStory

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import bangkit.android.submission13.data.Condition
import bangkit.android.submission13.databinding.ActivityAddStoryBinding
import bangkit.android.submission13.helper.reduceFileImage
import bangkit.android.submission13.helper.uriToFile
import bangkit.android.submission13.view.ViewModelFactory
import bangkit.android.submission13.view.main.MainActivity

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private var imageUri: Uri? = null
    private val addNewStoryViewModel by viewModels<AddStoryViewModel>{(ViewModelFactory.getInstance(this))}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Make Your Own Story"
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnAddStory.setOnClickListener{ uploadImage() }
    }

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launchGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            showImage()
        }
    }

    private fun showImage() {
        imageUri?.let {
            Log.d("Image URL", "ShowImage: $it")
            binding.ivAddStory.setImageURI(it)
        }
    }

    private fun uploadImage() {
        imageUri?.let { uri ->
            val imgFile = uriToFile(uri, this).reduceFileImage()
            val desc = binding.etAddStory.text.toString()
            addNewStoryViewModel.addNewStory(imgFile, desc).observe(this) { condition ->
                when(condition) {
                    is Condition.Success -> {
                        val intent= Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    is Condition.Error -> {
                        Toast.makeText(this, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}