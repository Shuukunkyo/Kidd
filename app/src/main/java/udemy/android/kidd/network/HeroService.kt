package udemy.android.kidd.network

import retrofit2.http.GET
import retrofit2.http.Query
import udemy.android.kidd.model.UsageUser

interface HeroService {

    @GET("usage_user.json")
    suspend fun getUsageUser(
        @Query("key") apiKey: String = "72f90dd0"
    ): UsageUser
}

