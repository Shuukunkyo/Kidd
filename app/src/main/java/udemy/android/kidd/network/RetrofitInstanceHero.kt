package udemy.android.kidd.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstanceHero {

    private const val BASE_URL = "https://my.api.mockaroo.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()) // critical for Kotlin data class
        .build()

    val api: HeroService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(HeroService::class.java)
    }
}