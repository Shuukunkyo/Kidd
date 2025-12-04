package udemy.android.kidd.view.usage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import udemy.android.kidd.model.UsageUser
import udemy.android.kidd.repository.HeroRepository

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