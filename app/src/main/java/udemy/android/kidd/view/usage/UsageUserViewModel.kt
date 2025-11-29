package udemy.android.kidd.view.usage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import udemy.android.kidd.model.UsageUser
import udemy.android.kidd.repository.HeroRepository

// 移除了构造函数中的默认值，强制 ViewModel 必须通过工厂来创建，并接收一个正确的 Repository 实例
class UsageUserViewModel(
    private val repository: HeroRepository = HeroRepository()
) : ViewModel() {

    private val _user = MutableStateFlow<UsageUser?>(null)
    val user = _user.asStateFlow()

    fun loadUsageHero() {
        viewModelScope.launch {
            _user.value = repository.fetchUsageUser()
        }
    }
}