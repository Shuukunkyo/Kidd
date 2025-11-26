package udemy.android.kidd.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import udemy.android.kidd.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    // 声明 View Binding 变量，用于访问布局中的 View
    private var _binding: FragmentHomeBinding? = null
    // 这是一个非空属性，只能在 onCreateView 和 onDestroyView 之间访问
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 在这里进行所有 View 的初始化和事件绑定
    }

    override fun onDestroy() {
        super.onDestroy()
        // 3. 在 Fragment 销毁 View 时，将 binding 设为 null，防止内存泄漏
        _binding = null
    }
}