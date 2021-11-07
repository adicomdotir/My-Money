package ir.adicom.app.mymoney.ui.expense

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ir.adicom.app.mymoney.MainActivity
import ir.adicom.app.mymoney.R
import ir.adicom.app.mymoney.db.AppDatabase
import ir.adicom.app.mymoney.models.Category
import ir.adicom.app.mymoney.models.Expense
import ir.adicom.app.mymoney.ui.adapters.ExpenseAdapter
import ir.adicom.app.mymoney.ui.adapters.OnExpenseDeleteListener
import kotlinx.android.synthetic.main.fragment_expense_home.*
import kotlinx.coroutines.*
import kotlin.math.log

class ExpenseHomeFragment : Fragment(), OnExpenseDeleteListener {
    private lateinit var rvExpense: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var navController: NavController
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        appDatabase = context.let { AppDatabase.invoke(it!!) }

        val expense = arguments?.getParcelable<Expense>("Expense")
        val categoryTitle = arguments?.getString("CategoryTitle")
        if (expense != null) {
            GlobalScope.launch(Dispatchers.IO) {
                var categoryId = appDatabase.getCategoryDao().getItemByTitle(categoryTitle!!)
                if (categoryId == null) {
                    appDatabase.getCategoryDao().addCategory(Category(0, categoryTitle))
                    categoryId = appDatabase.getCategoryDao().getItemByTitle(categoryTitle)
                }
                expense.categoryId = categoryId!!
                appDatabase.getExpenseDao().addExpense(expense)
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
        expenseAdapter = ExpenseAdapter(listOf(), this)
        rvExpense.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvExpense.adapter = expenseAdapter

        appDatabase.getExpenseDao().getAllExpensesWithCategory().observe(
            viewLifecycleOwner,
            Observer {
                if (it.isNotEmpty()) {
                    tv_no_expenses_available.visibility = View.GONE
                    rvExpense.visibility = View.VISIBLE
                    expenseAdapter.setExpenseList(it)
                }
            }
        )

        (requireActivity() as MainActivity).supportActionBar?.title =  "لیست هزینه ها"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                navController.navigate(R.id.action_expenseHomeFragment_to_newExpenseFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onExpenseDelete(expense: Expense) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("ایا مطمئن هستید؟")
            builder.apply {
                setPositiveButton(R.string.yes,
                    DialogInterface.OnClickListener { dialog, id ->
                        CoroutineScope(Dispatchers.IO).launch {
                            appDatabase.getExpenseDao().deleteExpense(expense)
                        }
                    })
                setNegativeButton(R.string.no,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }
            builder.create()
        }
        alertDialog?.show()
    }
}