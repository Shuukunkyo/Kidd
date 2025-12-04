package udemy.android.kidd.repository

/**
 * 自定义的 Repository 结果封装类 (RepoResult)
 * 用于在 Repository 和 ViewModel 之间传递数据和错误，支持干净的 when 表达式。
 * @param T 泛型参数，代表成功时携带的数据类型 (如 List<CardSummary>)。
 */
sealed interface RepoResult<out T> {

    // 状态 1: 成功获取数据
    data class Success<out T>(val data: T) : RepoResult<T>

    // 状态 2: 失败 (携带异常和可选的错误信息)
    data class Failure(val exception: Exception, val message: String? = null) : RepoResult<Nothing>
}