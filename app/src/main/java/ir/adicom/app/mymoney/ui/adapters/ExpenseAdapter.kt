package ir.adicom.app.mymoney.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import ir.adicom.app.mymoney.R
import ir.adicom.app.mymoney.models.Expense

class ExpenseAdapter(private var expenseList: List<Expense>): RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    inner class ExpenseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvExpenseTitle: TextView = itemView.findViewById(R.id.tv_expense_title)

        fun bind(expense: Expense) {
            tvExpenseTitle.text = expense.title
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
