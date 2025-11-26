package udemy.android.kidd

import android.os.Bundle
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

        // 1. 初始化 View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 设置 Activity 的内容视图
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavView.setupWithNavController(navController)
    }
}

