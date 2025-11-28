package udemy.android.kidd.repository

import udemy.android.kidd.network.CardApiService

class CardRepository(
    private val apiService: CardApiService
) {
    suspend fun fetchCardSummary() = apiService.fetchCardSummary()
}

//✔ Repository 提供一个 “资料入口”
//✔ ViewModel 只要调用 repository.fetchCardSummary()
//✔ 完全不需要知道 Retrofit 是怎么写的
//✔ 完全不需要知道 baseUrl、key、Moshi 等细节