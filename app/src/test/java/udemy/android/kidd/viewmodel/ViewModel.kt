package udemy.android.kidd.viewmodel // 包名请根据你的项目结构调整

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import udemy.android.kidd.model.UsageUser
import udemy.android.kidd.repository.HeroRepository // 引入你的 HeroRepository
import udemy.android.kidd.view.usage.UsageUserViewModel

@ExperimentalCoroutinesApi // 使用协程测试需要此注解
class UsageUserViewModelTest {

    // 1. 声明要被测试的 ViewModel 和它所依赖的 Repository
    private lateinit var repository: HeroRepository
    private lateinit var viewModel: UsageUserViewModel

    // 创建一个测试用的协程调度器，让协程同步执行，便于测试
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // 在每个测试运行前，都会执行这里的代码

        // 将主线程的调度器替换为我们的测试调度器
        Dispatchers.setMain(testDispatcher)

        // 【核心】创建 HeroRepository 的模拟 (Mock) 对象
        // 我们不使用真实的 Repository，因为它会发起真实的网络请求，这会让测试变得缓慢且不可靠
        repository = mock(HeroRepository::class.java)

        // 创建 ViewModel 实例，并将模拟的 repository 注入进去
        viewModel = UsageUserViewModel(repository)
    }

    @After
    fun tearDown() {
        // 在每个测试结束后，重置主调度器，避免影响其他测试
        Dispatchers.resetMain()
    }

    @Test
    fun `当 loadUsageHero 被调用时, StateFlow 应该更新为 repository 返回的用户数据`() = runTest {
        // AAA 模式: Arrange, Act, Assert

        // 1. Arrange (准备): 准备好我们的“假”数据和 mock 行为
        val fakeUser = UsageUser(id = 99, name = "测试用户", lastName = "姓")

        // 【关键】定义 mock 对象的行为
        // 我们告诉 mockito: "当 repository 的 fetchUsageUser() 方法被调用时，不要执行真实的网络请求，
        // 而是直接返回我们准备好的 fakeUser 对象"。
        // 注意：因为 fetchUsageUser 是 suspend 函数，我们需要在 `runTest` 作用域内定义 `when`
        `when`(repository.fetchUsageUser()).thenReturn(fakeUser)


        // 2. Act & Assert (行动与断言): 执行操作并验证结果
        viewModel.user.test { // 使用 Turbine 的 `test` 函数来监听 StateFlow

            // (A) 断言 Flow 的初始值
            // 你的 StateFlow 初始值是 null，所以我们先验证这一点
            assertEquals("初始值应该是 null", null, awaitItem())

            // (B) 执行我们要测试的 ViewModel 中的方法
            viewModel.loadUsageHero()

            // (C) 等待并断言 Flow 发射的下一个值
            // `awaitItem()` 会挂起测试，直到 StateFlow 发射一个新的值
            // 我们断言这个新值应该和我们准备的 `fakeUser` 完全相等
            assertEquals("发射的值应该是伪造的用户数据", fakeUser, awaitItem())

            // (可选) 验证没有更多值被发射
            // ensureAllEventsConsumed()

            // (可选) 如果不关心某些发射的值，可以跳过
            // cancelAndIgnoreRemainingEvents()
        }
    }
}
