package udemy.android.kidd.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.ui.semantics.selected
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import udemy.android.kidd.R
import udemy.android.kidd.adapter.CreditCardAdapter
import udemy.android.kidd.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scrollCard()

    }

    // 在 onDestroyView 中清理 binding，这是正确的生命周期实践
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun scrollCard(){
        // 1. 使用您指定的正确布局文件
        val cardLayouts = listOf(R.layout.item_credit_card_1, R.layout.item_credit_card_2)

        // 2. 用布局列表初始化适配器
        val cardAdapter = CreditCardAdapter(cardLayouts)

        // 3. 将适配器设置给 ViewPager2
        binding.fragmentHomeMainCard.cardViewPager.adapter = cardAdapter

        // --- 开始添加新代码 ---

        // 1. 获取包含所有小圆点的 LinearLayout
        val dotsLayout = binding.fragmentHomeMainCard.homeMaincardSelectcard.dotsLayout

        val viewPager = binding.fragmentHomeMainCard.cardViewPager
        viewPager.adapter = cardAdapter

        // 2. 将所有小圆点的 ImageView 放入一个列表，方便操作
        val dots = listOf<ImageView>(
            dotsLayout.findViewById(R.id.selected_item_1),
            dotsLayout.findViewById(R.id.selected_item_2),
            dotsLayout.findViewById(R.id.selected_item_3),
            dotsLayout.findViewById(R.id.selected_item_4),
            dotsLayout.findViewById(R.id.selected_item_5)
        )

        // 3. 监听 ViewPager2 的页面切换事件
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("HomeFragment", "onPageSelected: $position")


                // 4. 在页面切换时，更新所有小圆点的颜色
                dots.forEachIndexed { index, dotImageView ->
                    if (index == position) {
                        // 这是当前页，设置为“选中”状态 (你的小红点)
                        dotImageView.setImageResource(R.drawable.active_dot)
                    } else {
                        // 其他页，设置为“未选中”状态 (灰色圆点)
                        dotImageView.setImageResource(R.drawable.inactive_dot)
                    }
                }
            }
        })

        // 5. 手动初始化第一次进入时的状态
        // 假设一开始显示第一页 (position 0)，我们手动更新一下圆点
        dots.forEachIndexed { index, dotImageView ->
            if (index == 0) {
                dotImageView.setImageResource(R.drawable.active_dot)
            } else {
                dotImageView.setImageResource(R.drawable.inactive_dot)
            }
        }
    }
}