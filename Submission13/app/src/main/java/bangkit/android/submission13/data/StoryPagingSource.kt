package bangkit.android.submission13.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import bangkit.android.submission13.data.dataStore.TokenPreference
import bangkit.android.submission13.network.ApiConfig
import bangkit.android.submission13.network.ApiService
import bangkit.android.submission13.response.ListStoryItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class StoryPagingSource(private val apiService: ApiService, private val tokenPreference: TokenPreference): PagingSource<Int, ListStoryItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,ListStoryItem> {
        try {
            val pref = runBlocking { tokenPreference.getToken().first() }
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = ApiConfig.getApiService(pref.token).getAllStories(position, params.loadSize)
            return LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

}