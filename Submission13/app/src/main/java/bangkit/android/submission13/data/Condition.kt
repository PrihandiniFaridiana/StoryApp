package bangkit.android.submission13.data

sealed class Condition<out R> private constructor() {
    data class Success <out T>(val data: T) : Condition<T>()
    data class Error (val error: String) : Condition<Nothing>()
}