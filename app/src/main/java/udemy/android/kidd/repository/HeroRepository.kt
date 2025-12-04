package udemy.android.kidd.repository

import udemy.android.kidd.model.UsageUser
import udemy.android.kidd.network.HeroService
import udemy.android.kidd.network.RetrofitInstanceHero


//✔ Repository 是 ViewModel 用来访问 ApiService 的入口
//✔ ViewModel 永远不与 Retrofit 直接接触
class HeroRepository(
    private val api: HeroService = RetrofitInstanceHero.api

) {
    suspend fun fetchUsageUser(): UsageUser? {
        // API 返回 List → 真实项目中首页只显示一个人 → 取第一条

        return api.getUsageUser()
    }

}