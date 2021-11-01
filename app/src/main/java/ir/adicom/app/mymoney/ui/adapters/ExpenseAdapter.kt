package ir.adicom.app.mymoney.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import ir.adicom.app.mymoney.R
import ir.adicom.app.mymoney.models.Expense
import kotlinx.android.synthetic.main.expense_item.view.*
import saman.zamani.persiandate.PersianDate
import java.text.NumberFormat
import java.util.*

class ExpenseAdapter(private var expenseList: List<Expense>): RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    inner class ExpenseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvExpenseTitle: TextView = itemView.findViewById(R.id.tv_expense_title)

        fun bind(expense: Expense) {
            val persianDate = PersianDate(expense.date)
            var dateStr = "${persianDate.shYear}/${persianDate.shMonth}/${persianDate.shDay}"
            val priceWithFormat = NumberFormat.getNumberInstance(Locale.US)
                .format(expense.price)
                .replace(",", "،")
            tvExpenseTitle.text = expense.title
            itemView.tv_expense_price.text = priceWithFormat
            itemView.tv_expense_date.text = dateStr
            itemView.tv_category_title.text = expense.categoryId.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.expense_item, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]
        holder.bind(expense)
    }

    fun setExpenseList(expenseList: List<Expense>) {
        this.expenseList = expenseList
        notifyDataSetChanged()
    }

}
