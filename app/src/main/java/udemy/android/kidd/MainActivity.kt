package udemy.android.kidd

// 在 MainActivity.kt 文件中

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.unit.size
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI  // 引入这个
import udemy.android.kidd.R
import udemy.android.kidd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- 1. 保持不变：获取 NavController ---
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController = navHostFragment.navController

        // --- 2. 【核心修改】使用手动设置的方式来替代 setupWithNavController ---
        // 这是解决加载缓慢问题的关键！
        binding.bottomNavView.setOnItemSelectedListener { item ->
            // 调用 NavigationUI.onNavDestinationSelected 来处理导航
            // 这个方法内部有智能逻辑，会避免重新创建已经存在的 Fragment
            NavigationUI.onNavDestinationSelected(item, navController)

            // 返回 true 表示我们已经处理了这次点击事件
            true
        }

        // （可选但推荐）监听导航变化，反过来更新 BottomNavigationView 的选中状态
        // 这样可以处理按返回键或者深层链接导致导航变化的情况
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val menu = binding.bottomNavView.menu
            for (i in 0 until menu.size()) {
                val menuItem = menu.getItem(i)
                if (menuItem.itemId == destination.id) {
                    menuItem.isChecked = true
                    break
                }
            }
        }
    }
}

