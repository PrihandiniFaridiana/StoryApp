package bangkit.android.submission13.response

data class GetAllStoriesResponse(
	val listStory: List<ListStoryItem> = emptyList(),
	val error: Boolean? = null,
	val message: String? = null
)

data class ListStoryItem(
	val photoUrl: String,
	val createdAt: String,
	val name: String,
	val description: String,
	val lon: Double,
	val id: String,
	val lat: Double
)

