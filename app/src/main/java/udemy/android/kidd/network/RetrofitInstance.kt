package udemy.android.kidd.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 * 这是一个单例对象 (Singleton Object)，用于提供全局唯一的 Retrofit 网络请求实例。
 * 使用 `object` 关键字可以确保在整个应用程序中，无论这个对象被调用多少次，都只有一个实例存在。
 * 这样做的好处是：
 * 1. 节省资源：不需要反复创建和销毁重量级的网络客户端对象 (OkHttpClient, Retrofit)。
 * 2. 统一配置：所有网络请求都使用同一套配置（比如相同的 Base URL, 超时时间, 日志拦截器等）。
 */
object RetrofitInstance {

    // 1. 定义 API 的基础 URL
    // 所有的网络请求路径都会在这个 URL 的基础上进行拼接。
    // 例如，如果一个请求的路径是 "/card/list"，那么完整的请求 URL 就是 "https://my.api.mockaroo.com/card/list"。
    private const val BASE_URL = "https://my.api.mockaroo.com/"

    // 2. 配置 OkHttpClient (Retrofit 的底层网络客户端)
    // OkHttp 负责实际的 HTTP 请求发送和接收，比如建立连接、处理重试、设置超时等。
    private val client = OkHttpClient.Builder()
        // 添加一个日志拦截器 (HttpLoggingInterceptor)。
        // 这在开发阶段非常有用，它可以在 Logcat 中打印出详细的网络请求和响应信息。
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                // 设置日志级别为 BODY，表示请求和响应的头部、正文等所有信息都会被打印出来。
                // 在发布应用时，通常会将其级别改为 Level.NONE，以避免泄露敏感信息。
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build() // 创建 OkHttpClient 实例

    // 3. 配置 Moshi (用于 JSON 解析)
    // Moshi 是一个现代化的 JSON 库，用于将服务器返回的 JSON 字符串自动转换成 Kotlin 的数据类 (Data Class)，
    // 或者将 Kotlin 对象转换成 JSON 字符串发送给服务器。
    private val moshi = Moshi.Builder()
        // 添加 Kotlin 反射支持的适配器工厂。
        // 这是让 Moshi 能够理解和处理 Kotlin 特有语法（比如默认参数、可空类型等）的关键。
        .add(KotlinJsonAdapterFactory())
        .build() // 创建 Moshi 实例

    /**
     * 4. 创建并暴露 Retrofit 的 API Service 实例
     * `api` 是外界实际使用的对象，通过它来发起具体的网络请求。
     *
     * 使用 `by lazy` 实现懒加载：
     * 这意味着只有在第一次访问 `RetrofitInstance.api` 时，`lazy` 代码块内部的代码才会被执行。
     * 这避免了在应用启动时就立即创建重量级的 Retrofit 对象，提升了启动性能。
     * 并且，它保证了即使在多线程环境下，这个实例也只会被创建一次。
     */
    val api: CardApiService by lazy {
        Retrofit.Builder()
            // 设置基础 URL
            .baseUrl(BASE_URL)
            // 将我们配置好的 OkHttpClient 实例设置给 Retrofit
            .client(client)
            // 添加 Moshi 转换器工厂，告诉 Retrofit 使用 Moshi 来处理 JSON 数据。
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            // 创建 Retrofit 实例
            .build()
            // 最后，根据你的接口定义 (CardApiService)，创建一个可以实际调用的 API 服务实例。
            .create(CardApiService::class.java)
    }
}

//✔ 它创建 Retrofit
//✔ 配置 JSON 解析（Moshi）
//✔ 配置 OkHttpClient（日志、超时、token 等）
//✔ 提供 api 对象给 Repository 使用
//
//ViewModel 不直接碰 Retrofit
//→ Repository “拿这个 api” 去叫后端
//→ ViewModel 从 Repository 拿数据
//→ UI 显示 ViewModel 数据
