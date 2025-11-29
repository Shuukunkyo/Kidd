package udemy.android.kidd.network

import retrofit2.http.GET
import udemy.android.kidd.model.CardSummary
import udemy.android.kidd.model.UsageUser

interface CardApiService {
    @GET("credit_card.json?key=72f90dd0")
    suspend fun fetchCardSummary(): List<CardSummary>

}