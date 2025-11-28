package udemy.android.kidd.view.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import udemy.android.kidd.network.RetrofitInstance
import udemy.android.kidd.repository.CardRepository

/**
 * 这是一个 ViewModel，专门负责为卡片相关的 UI (比如 HomeFragment) 提供数据和处理业务逻辑。
 *
 * 它继承自 androidx.lifecycle.ViewModel，这意味着它具有生命周期感知能力：
 * 1. 它能在配置变更（如屏幕旋转）时存活下来，不会丢失数据。
 * 2. 当关联的 UI (Fragment/Activity) 被彻底销毁时，它的 onCleared() 方法会被调用，可以在此清理资源。
 */
class CardViewModel(
    // 1. 依赖注入：接收一个 Repository 作为数据源。
    // Repository (仓库) 模式用于封装数据获取的细节（是从网络获取还是从本地数据库获取）。
    // ViewModel 不关心数据具体从哪里来，只管向 Repository 请求。
    // 这里使用了默认参数，方便在不使用依赖注入框架的情况下直接实例化 ViewModel。
    // 在大型项目中，这通常会通过 Hilt 或 Koin 等框架来注入。
    private val repository: CardRepository = CardRepository(RetrofitInstance.api)
): ViewModel() {

    // 2. 状态管理：使用 StateFlow 来管理并暴露 UI 状态。
    // _cardSummaryState 是一个可变的 StateFlow，只在 ViewModel 内部使用，用于更新状态。
    // 它被初始化为 Loading 状态，这样 UI 在第一时间就会显示一个加载指示器。
    private val _cardSummaryState = MutableStateFlow<HomeCardSummaryState>(HomeCardSummaryState.Loading)

    // 3. 状态暴露：向外部暴露一个不可变的 StateFlow。
    // UI 层 (Fragment 或 Compose Screen) 会观察 (collect) 这个 cardSummaryState。
    // 当它的值发生变化时（从 Loading -> Success 或 Error），UI 会自动收到通知并更新界面。
    // 这种“单向数据流”的设计使得状态变化可预测且易于调试。
    val cardSummaryState: StateFlow<HomeCardSummaryState> = _cardSummaryState

    /**
     * `init` 代码块会在 ViewModel 实例第一次被创建时执行。
     * 在这里调用 fetchCardSummary() 可以实现“进入页面就自动加载数据”的效果。
     */
    init {
        fetchCardSummary()
    }

    /**
     * 这是获取卡片摘要信息的核心业务逻辑方法。
     * 它可以被 UI 调用（比如下拉刷新时），也可以在 init 中自动调用。
     */
    fun fetchCardSummary(){
        // 4. 启动一个协程 (Coroutine) 来执行异步操作。
        // `viewModelScope` 是一个与 ViewModel 生命周期绑定的协程作用域。
        // 当 ViewModel 被销毁时，这个作用域内的所有协程都会被自动取消，从而避免内存泄漏。
        viewModelScope.launch {
            // (A) 在开始网络请求之前，立即将状态更新为 Loading。
            // UI 观察到这个变化后，会显示加载动画。
            _cardSummaryState.value = HomeCardSummaryState.Loading
            try {
                // (B) 调用 Repository 的方法来执行实际的网络请求。
                // `suspend` 函数的调用必须在协程中。
                val result = repository.fetchCardSummary()

                // (C) 网络请求成功，将状态更新为 Success，并附带获取到的数据。
                // UI 观察到这个变化后，会隐藏加载动画，并显示卡片数据。
                _cardSummaryState.value = HomeCardSummaryState.Success(result)
            } catch (e: Exception){
                // (D) 在 try-catch 块中捕获任何可能发生的异常（如网络错误、解析错误等）。
                // 将状态更新为 Error，并附带错误信息。
                // UI 观察到这个变化后，会隐藏加载动画，并向用户显示错误提示。
                _cardSummaryState.value = HomeCardSummaryState.Error(
                    e.message ?: "Unknown Error" // 如果异常没有 message，提供一个默认的错误文本。
                )
            }
        }
    }
}
