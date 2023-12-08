package bangkit.android.submission13.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bangkit.android.submission13.databinding.CardStoryBinding
import bangkit.android.submission13.response.ListStoryItem
import com.bumptech.glide.Glide

class StoryAdapter: PagingDataAdapter<ListStoryItem, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>(){
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem:ListStoryItem) : Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder (private val binding: CardStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClick(story)
            }
            binding.tvStoryName.text = story.name
            val image: ImageView = binding.ivStory
            Glide.with(image)
                .load(story.photoUrl)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryAdapter.ViewHolder {
        val binding = CardStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryAdapter.ViewHolder, position: Int) {
        val items = getItem(position)
        if (items != null) {
            holder.bind(items)
        }
    }

    interface OnItemClickCallback{
        fun onItemClick(data: ListStoryItem)
    }

}