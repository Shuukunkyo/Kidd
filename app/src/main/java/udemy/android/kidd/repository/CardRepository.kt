package udemy.android.kidd.repository

import udemy.android.kidd.model.CardSummary
import udemy.android.kidd.network.CardApiService

//class CardRepository(
//    private val apiService: CardApiService
//) {
//    suspend fun fetchCardSummary() = apiService.fetchCardSummary()
//}

class CardRepository(
    private val apiService: CardApiService
) {

    /**
     * 获取主页信用卡概要数据。现在返回整个列表。
     */
    suspend fun fetchCardSummary(): RepoResult<List<CardSummary>> { // 【修正】返回类型为 List<CardSummary>
        return try {
            val responseList = apiService.fetchCardSummary()

            // 封装在自定义 RepoResult.Success 中，注意现在用 data 字段
            RepoResult.Success(responseList)

        } catch (e: Exception) {
            // 封装在自定义 RepoResult.Failure 中
            // 传入日文错误信息，供 ViewModel 显示
            RepoResult.Failure(e, "ネットワーク接続エラーが発生しました (Network connection error occurred)")
        }
    }
}




//✔ Repository 提供一个 “资料入口”
//✔ ViewModel 只要调用 repository.fetchCardSummary()
//✔ 完全不需要知道 Retrofit 是怎么写的
//✔ 完全不需要知道 baseUrl、key、Moshi 等细节