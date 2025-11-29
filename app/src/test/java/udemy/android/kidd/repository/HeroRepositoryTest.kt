package udemy.android.kidd.repository

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import udemy.android.kidd.model.UsageUser
import udemy.android.kidd.network.HeroService
import kotlin.jvm.Throws
import kotlin.test.assertEquals

/**
 * 这是一个针对 HeroRepository 类进行单元测试的类。
 * 单元测试的目的是隔离地验证单个组件（这里是 HeroRepository）的行为是否正确。
 */
class HeroRepositoryTest {

    // 声明 HeroService 接口的 Mock 对象。
    // Mock 对象是用来模拟真实 API 服务的“假”对象，这样在测试 Repository 时，
    // 我们不需要真正进行网络请求，保证测试的快速和独立。
    private lateinit var api: HeroService

    // 声明我们要测试的 HeroRepository 实例。
    private lateinit var repository: HeroRepository

    /**
     * @Before 注解标记的方法在每个测试方法（@Test）执行前都会运行。
     * 它的作用是设置测试环境（初始化）。
     */
    @Before
    fun setup() {
        // 1. 使用 Mockito 库创建 HeroService 接口的 Mock 实例。
        api = mock(HeroService::class.java)

        // 2. 实例化 HeroRepository，并将 Mock 的 HeroService 注入进去。
        // 现在，Repository 调用 API 时，实际上是在调用这个 Mock 对象。
        repository = HeroRepository(api)
    }

    /**
     * @Test 注解标记的方法是一个独立的测试用例。
     * 这个测试用例的目的是验证：当 HeroRepository 调用 API 成功时，
     * 它应该返回 API 实际返回的数据。
     * * `runTest` 是 Kotlin Coroutines Test 库提供的，用于在测试环境中运行挂起函数 (suspend fun)。
     */
    @Test
    fun `fetchUsageUser should return user from api`()= runTest {
        // 1. 假数据（Fake Response）
        // 定义一个我们期望 API 返回的假数据对象。
        val fakeUser = UsageUser(
            id = 9,
            name = "TestName",
            lastName = "Test_lastName"
        )

        // 2. Mock API 的行为 (When)
        // 使用 Mockito 的 `when` 语句来指定：
        // "当调用 api.getUsageUser("72f90dd0") 时，不要真正执行网络请求，而是直接返回 fakeUser。"
        `when`(api.getUsageUser("72f90dd0")).thenReturn(fakeUser)

        // 3. 调用 (Act)
        // 调用我们要测试的方法。
        val result = repository.fetchUsageUser()

        // 4. 断言（Assert）
        // 验证结果：断言 Repository 返回的结果 (result) 应该和我们预设的假数据 (fakeUser) 完全相同。
        assertEquals(fakeUser, result)

        // 5. 确认 API 被调用 (Verify)
        // 使用 Mockito 的 `verify` 语句来确认：
        // Repository 确实（且只）调用了 api.getUsageUser("72f90dd0") 这个方法一次。
        verify(api).getUsageUser("72f90dd0")
    }
}