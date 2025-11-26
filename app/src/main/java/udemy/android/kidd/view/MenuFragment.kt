package udemy.android.kidd.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import udemy.android.kidd.databinding.FragmentMenuBinding
import udemy.android.kidd.databinding.FragmentPointsBinding

class MenuFragment : Fragment(){
    private var _binding: FragmentMenuBinding?=null
    val binding get() = _binding!!



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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