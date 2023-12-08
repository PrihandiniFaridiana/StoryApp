package bangkit.android.submission13.data.dataStore

data class UserToken(
    val token: String,
    val name: String,
    val userId: String,
    val isLogin: Boolean = false
)
