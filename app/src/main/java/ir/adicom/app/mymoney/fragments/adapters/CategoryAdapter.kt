package ir.adicom.app.mymoney.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.adicom.app.mymoney.R
import ir.adicom.app.mymoney.models.Category

class CategoryAdapter(private var categoryList: List<Category>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.textView)
        textView.setText(categoryList[position].title)
    }

    fun setCategoryList(categoryList: List<Category>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}