package udemy.android.kidd.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import udemy.android.kidd.R
import udemy.android.kidd.adapter.CreditCardAdapter
import udemy.android.kidd.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // --- 修改1：将 Adapter 提升为成员变量 ---
    private lateinit var cardAdapter: CreditCardAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // --- 修改2：只有在 binding 为 null (第一次创建) 时才加载布局 ---
        if (_binding == null) {
            _binding = FragmentHomeBinding.inflate(inflater, container, false)
            Log.d("HomeFragment", "onCreateView: Inflating layout for the first time.")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- 修改3：只有在 Adapter 未初始化时才执行设置逻辑 ---
        if (!::cardAdapter.isInitialized) {
            Log.d("HomeFragment", "onViewCreated: Setting up scrollCard for the first time.")
            scrollCard()
        } else {
            Log.d("HomeFragment", "onViewCreated: Re-attaching existing view, skipping setup.")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // --- 修改4：不再将 _binding 设为 null，以缓存视图 ---
        // _binding = null
        // 注意：这种做法虽然简单，但在极端内存压力下可能有风险。
        // 对于底部导航这种场景，它通常是安全且有效的。
        Log.d("HomeFragment", "onDestroyView: View is being destroyed, but we are keeping the binding.")
    }

    fun scrollCard() {
        // 1. 初始化 Adapter (只在第一次调用时)
        cardAdapter = CreditCardAdapter(listOf(R.layout.item_credit_card_1, R.layout.item_credit_card_2))

        val viewPager = binding.fragmentHomeMainCard.cardViewPager
        viewPager.adapter = cardAdapter

        val dotsLayout = binding.fragmentHomeMainCard.homeMaincardSelectcard.dotsLayout
        val dots = listOf<ImageView>(
            dotsLayout.findViewById(R.id.selected_item_1),
            dotsLayout.findViewById(R.id.selected_item_2),
            dotsLayout.findViewById(R.id.selected_item_3),
            dotsLayout.findViewById(R.id.selected_item_4),
            dotsLayout.findViewById(R.id.selected_item_5)
        )

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("HomeFragment", "onPageSelected: $position")
                dots.forEachIndexed { index, dotImageView ->
                    dotImageView.setImageResource(if (index == position) R.drawable.active_dot else R.drawable.inactive_dot)
                }
            }
        })

        // 初始化圆点状态
        dots.forEachIndexed { index, dotImageView ->
            dotImageView.setImageResource(if (index == 0) R.drawable.active_dot else R.drawable.inactive_dot)
        }
    }
}
