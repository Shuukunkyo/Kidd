package udemy.android.kidd.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.launch
import udemy.android.kidd.R
import udemy.android.kidd.adapter.CreditCardAdapter
import udemy.android.kidd.databinding.FragmentHomeBinding
import udemy.android.kidd.view.card.CardViewModel
import udemy.android.kidd.view.card.HomeCardSummaryState

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // 【修改3】使用 KTX 库轻松获取 ViewModel 实例
    private val cardViewModel: CardViewModel by viewModels()
    // 【修改4】将 Adapter 声明为成员变量
    private lateinit var cardAdapter: CreditCardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        // 【修改5】在这里初始化 Adapter 和 ViewPager2
        setupViewPager()

        // 【修改6】在这里开始观察 ViewModel 的状态
        observeCardSummaryState()

        // 你的小圆点逻辑可以放在这里
        setupDotsIndicator()
    }

    private fun setupViewPager() {
        // 创建一个空的 Adapter
        cardAdapter = CreditCardAdapter()
        binding.fragmentHomeMainCard.cardViewPager.adapter = cardAdapter
    }

    private fun observeCardSummaryState() {
        // 使用 viewLifecycleOwner.lifecycleScope 来安全地收集 Flow
        viewLifecycleOwner.lifecycleScope.launch {
            cardViewModel.cardSummaryState.collect { state ->
                // 使用 when 表达式处理不同的 UI 状态
                when (state) {
                    is HomeCardSummaryState.Loading -> {
                        // 显示加载动画
                        Log.d("HomeFragment", "State: Loading")
//                        binding.fragmentHomeMainCard.cardProgressBar.visibility = View.VISIBLE
                    }
                    is HomeCardSummaryState.Success -> {
                        // 加载成功
                        Log.d("HomeFragment", "State: Success with ${state.cardSummary.size} items")
//                        binding.fragmentHomeMainCard.cardProgressBar.visibility = View.GONE
                        // 【核心】将获取到的数据提交给 Adapter
                        cardAdapter.submitList(state.cardSummary)
                    }
                    is HomeCardSummaryState.Error -> {
                        // 加载失败
                        Log.d("HomeFragment", "State: Error - ${state.message}")
//                        binding.fragmentHomeMainCard.cardProgressBar.visibility = View.GONE
                        Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setupDotsIndicator() {
        val viewPager = binding.fragmentHomeMainCard.cardViewPager
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
                dots.forEachIndexed { index, dotImageView ->
                    // 确保不会因为数据少于5个而越界
                    if(index < dots.size) {
                        dotImageView.setImageResource(if (index == position) R.drawable.active_dot else R.drawable.inactive_dot)
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
