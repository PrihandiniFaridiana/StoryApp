package bangkit.android.submission13.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import bangkit.android.submission13.R
import bangkit.android.submission13.databinding.ActivityMainBinding
import bangkit.android.submission13.response.ListStoryItem
import bangkit.android.submission13.view.ViewModelFactory
import bangkit.android.submission13.view.addStory.AddStoryActivity
import bangkit.android.submission13.view.detail.DetailActivity
import bangkit.android.submission13.view.maps.MapsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Home"

        showAllStories()

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddStoryActivity::class.java))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.btn_logout -> {
                mainViewModel.logout()
                finishAffinity()
            }
            R.id.btn_location -> {
                startActivity(Intent(this@MainActivity, MapsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAllStories() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        mainViewModel.getAllStories().observe(this) { story ->
            val storyAdapter = StoryAdapter()
            binding.rvStory.adapter = storyAdapter
            storyAdapter.submitData(lifecycle, story)
            storyAdapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
                override fun onItemClick(data: ListStoryItem) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_NAME, data.name)
                    intent.putExtra(DetailActivity.EXTRA_PHOTO_URL, data.photoUrl)
                    intent.putExtra(DetailActivity.EXTRA_DESC, data.description)
                    startActivity(intent)
                }
            })
        }
    }

}