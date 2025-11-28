package udemy.android.kidd.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import udemy.android.kidd.R
import udemy.android.kidd.databinding.ItemCreditCard1Binding
import udemy.android.kidd.databinding.ItemCreditCard2Binding
import udemy.android.kidd.model.CardSummary
import java.text.DecimalFormat

// 【修改1】构造函数不再接收任何数据，它被创建时是空的
class CreditCardAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 内部数据列表，私有
    private var cardList: List<CardSummary> = emptyList()

    // 【修改2】提供一个公开的方法来更新数据
    // 这是从 Fragment 传递数据的唯一入口
    fun submitList(newCardList: List<CardSummary>) {
        this.cardList = newCardList
        // 这是通知 RecyclerView 刷新UI的最有效方法
        notifyDataSetChanged()
    }

    // --- ViewHolder 的正确实现 ---
    // ViewHolder for the first card type
    inner class CardViewHolder1(val binding: ItemCreditCard1Binding) : RecyclerView.ViewHolder(binding.root) {
        private val formatter = DecimalFormat("#,###")
        fun bind(summary: CardSummary) {
            binding.cardName.text = summary.cardName
            binding.cardBrand.text = summary.cardBrand
            binding.cardNumberTextView.text = "**** ${summary.cardNumber.takeLast(4)}"
            binding.paymentAmountTextView.text = "¥ ${formatter.format(summary.paymentAmount)}"
        }
    }

    // ViewHolder for the second card type
    inner class CardViewHolder2(val binding: ItemCreditCard2Binding) : RecyclerView.ViewHolder(binding.root) {
        private val formatter = DecimalFormat("#,###")
        fun bind(summary: CardSummary) {
            binding.cardName.text = summary.cardName
            binding.cardBrand.text = summary.cardBrand
            binding.cardNumberTextView.text = "**** ${summary.cardNumber.takeLast(4)}"
            binding.paymentAmountTextView.text = "¥ ${formatter.format(summary.paymentAmount)}"
        }
    }
    // ----------------------------

    override fun getItemCount(): Int {
        return cardList.size // 返回当前数据列表的大小
    }

    override fun getItemViewType(position: Int): Int {
        // 我们可以根据 position 来决定使用哪个布局
        // 例如，奇数用布局1，偶数用布局2
        return if (position % 2 == 0) {
            R.layout.item_credit_card_1
        } else {
            R.layout.item_credit_card_2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_credit_card_1 -> {
                val binding = ItemCreditCard1Binding.inflate(inflater, parent, false)
                CardViewHolder1(binding)
            }
            R.layout.item_credit_card_2 -> {
                val binding = ItemCreditCard2Binding.inflate(inflater, parent, false)
                CardViewHolder2(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= cardList.size) return
        val summary = cardList[position]

        when (holder) {
            is CardViewHolder1 -> holder.bind(summary)
            is CardViewHolder2 -> holder.bind(summary)
        }
    }
}
