package udemy.android.kidd.network

import retrofit2.http.GET
import retrofit2.http.Query
import udemy.android.kidd.model.CardSummary

interface CardApiService {
    @GET("credit_card.json")
    suspend fun fetchCardSummary(
        @Query("key") apikey:String = "72f90dd0"
    ): List<CardSummary>
}