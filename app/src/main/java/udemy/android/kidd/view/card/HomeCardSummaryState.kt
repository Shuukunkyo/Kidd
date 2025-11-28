package udemy.android.kidd.view.card

import udemy.android.kidd.model.CardSummary

/**
 * 用于表示首页卡片信息加载过程中的各种UI状态。
 * 这是一个密封类，确保了我们能够处理所有可能的状态（加载中、成功、失败），
 * 从而使UI状态的管理更加健壮和可预测。
 */
sealed class HomeCardSummaryState {
    /**
     * 表示数据正在加载中。
     * 在此状态下，UI通常应显示一个加载指示器（例如 ProgressBar）。
     */
    object Loading : HomeCardSummaryState()

    /**
     * 表示数据已成功加载。
     * @property cardSummary 成功获取到的卡片信息列表，将用于更新UI。
     */
    data class Success(val cardSummary: List<CardSummary>) : HomeCardSummaryState()

    /**
     * 表示在加载数据过程中发生了错误。
     * @property message 包含错误的详细信息，可用于向用户显示提示（例如通过 Toast 或 SnackBar）。
     */
    data class Error(val message: String) : HomeCardSummaryState()
}
