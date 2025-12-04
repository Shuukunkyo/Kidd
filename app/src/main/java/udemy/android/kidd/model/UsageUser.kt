package udemy.android.kidd.model

import com.squareup.moshi.Json

data class UsageUser(

    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "last_name")
    val lastName: String
)