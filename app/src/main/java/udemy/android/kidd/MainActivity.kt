package udemy.android.kidd

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import udemy.android.kidd.databinding.ActivityMainBinding
import udemy.android.kidd.ui.theme.KiddTheme

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)

        // 1. 初始化 View Binding
        //    MainActivity 加载 activity_main.xml
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 设置 Activity 的内容视图
        setContentView(binding.root)

        //  FragmentContainerView 设置为 NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController = navHostFragment.navController

        // BottomNavigationView 加载 bottom_nav_menu.xml
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.usageFragment -> {
                    navController.navigate(R.id.usageFragment)
                    true
                }
                R.id.campaignFragment -> {
                    navController.navigate(R.id.campaignFragment)
                    true
                }
                R.id.pointsFragment -> {
                    navController.navigate(R.id.pointsFragment)
                    true
                }
                R.id.menuFragment -> {
                    navController.navigate(R.id.MenuFragment)
                    true
                }
                else -> false
            }
        }
    }
}

