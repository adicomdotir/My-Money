package ir.adicom.app.mymoney.ui.expense

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ir.adicom.app.mymoney.R
import ir.adicom.app.mymoney.db.AppDatabase
import ir.adicom.app.mymoney.models.Category
import ir.adicom.app.mymoney.models.Expense
import ir.adicom.app.mymoney.ui.adapters.ExpenseAdapter
import kotlinx.android.synthetic.main.fragment_expense_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.math.log

class ExpenseHomeFragment : Fragment() {
    private lateinit var rvExpense: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var navController: NavController
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appDatabase = context.let { AppDatabase.invoke(it!!) }

        val expense = arguments?.getParcelable<Expense>("Expense")
        val categoryTitle = arguments?.getString("CategoryTitle")
        if (expense != null) {
            GlobalScope.launch(Dispatchers.IO) {
                var categoryId = appDatabase.getCategoryDao().getItemByTitle(categoryTitle!!)
                if (categoryId == null) {
                    appDatabase.getCategoryDao().addCategory(Category(0, categoryTitle))
                    categoryId = appDatabase.getCategoryDao().getItemByTitle(categoryTitle)
                    expense.categoryId = categoryId!!
                    appDatabase.getExpenseDao().addExpense(expense)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController =
            (activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container_view) as NavHostFragment).navController
        rvExpense = view.findViewById(R.id.rv_expense)
        expenseAdapter = ExpenseAdapter(listOf())
        rvExpense.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvExpense.adapter = expenseAdapter

        appDatabase.getExpenseDao().getAllExpenses().observe(
            viewLifecycleOwner,
            Observer {
                if (it.isNotEmpty()) {
                    tv_no_expenses_available.visibility = View.GONE
                    rvExpense.visibility = View.VISIBLE
                    expenseAdapter.setExpenseList(it)
                }
            }
        )

        view.findViewById<FloatingActionButton>(R.id.fab_expense_add).setOnClickListener {
            navController.navigate(R.id.action_expenseHomeFragment_to_newExpenseFragment)
        }
    }
}