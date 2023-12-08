package bangkit.android.submission13.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import bangkit.android.submission13.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_PHOTO_URL = "extra_photo_url"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DESC = "extra_desc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailName = intent.getStringExtra(EXTRA_NAME)
        val detailPhoto = intent.getStringExtra(EXTRA_PHOTO_URL)
        val detailDesc = intent.getStringExtra(EXTRA_DESC)

        supportActionBar?.title = "$detailName Story"

        binding.tvDetailName.text = detailName
        binding.tvDetailDesc.text = detailDesc
        val image: ImageView = binding.ivDetailStory
        Glide.with(image)
            .load(detailPhoto)
            .into(image)
    }
}