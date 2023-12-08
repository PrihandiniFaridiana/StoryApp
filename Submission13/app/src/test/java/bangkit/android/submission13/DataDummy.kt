package bangkit.android.submission13

import bangkit.android.submission13.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                id = "id: $i",
                name = "User $i",
                description = "lorem ipsum dolor sit amet",
                photoUrl = "https://core.api.efishery.com/image/p/q100;/https://efishery.com/images/misi-global-hifi-1.png",
                createdAt = "2023-11-16T13:20:01",
                lat = i.toDouble(),
                lon = i.toDouble()
            )
            items.add(story)
        }
        return items
    }
}