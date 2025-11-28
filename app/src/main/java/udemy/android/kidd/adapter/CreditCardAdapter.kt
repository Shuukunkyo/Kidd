package udemy.android.kidd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CreditCardAdapter(private val layouts: List<Int>) : RecyclerView.Adapter<CreditCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 这里的 'viewType' 就是我们从 getItemViewType 传过来的布局资源 ID
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 因为我们的卡片布局是静态的，所以这里没有任何具体的数据需要绑定到视图上。
        // 这个方法可以为空。
    }

    override fun getItemCount(): Int {
        return layouts.size
    }

    // 我们使用布局资源的 ID 作为视图类型。这是一个常见且巧妙的技巧。
    override fun getItemViewType(position: Int): Int {
        return layouts[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
