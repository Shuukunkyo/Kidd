package udemy.android.kidd.model

import com.squareup.moshi.Json

data class CardSummary(
    val id: Int,
    @Json(name = "card_name")
    val cardName: String,
    @Json(name = "card_brand")
    val cardBrand: String,
    @Json(name = "card_number")
    val cardNumber: String,
    @Json(name = "total_credit_limit")
    val totalCreditLimit: Int,
    @Json(name = "payment_amount")
    val paymentAmount: Int,
    @Json(name = "payment_date")
    val paymentDate: String,
    @Json(name = "available_balance")
    val availableBalance: Int,
)