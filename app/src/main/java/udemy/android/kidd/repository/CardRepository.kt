package udemy.android.kidd.repository

import udemy.android.kidd.model.CardSummary
import udemy.android.kidd.network.CardApiService

class CardRepository(
    private val apiService: CardApiService
) {

    /**
     * 获取主页信用卡概要数据。现在返回整个列表。
     */
    suspend fun fetchCardSummary(): RepoResult<List<CardSummary>> {
        return try {
            val responseList = apiService.fetchCardSummary()
            RepoResult.Success(responseList)
        } catch (e: Exception) {
            RepoResult.Failure(e, "ネットワーク接続エラーが発生しました (Network connection error occurred)")
        }
    }

    /**
     * 获取用户信息。
     * 确保使用通过构造函数注入的、正确的 apiService 实例。
     */
}

//✔ Repository 提供一个 “资料入口”
//✔ ViewModel 只要调用 repository.fetchCardSummary()
//✔ 完全不需要知道 Retrofit 是怎么写的
//✔ 完全不需要知道 baseUrl、key、Moshi 等细节
