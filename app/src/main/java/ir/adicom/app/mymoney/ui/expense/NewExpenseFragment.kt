package ir.adicom.app.mymoney.ui.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.button.MaterialButton
import ir.adicom.app.mymoney.R
import ir.adicom.app.mymoney.db.AppDatabase
import ir.adicom.app.mymoney.models.Category
import ir.adicom.app.mymoney.models.Expense
import ir.adicom.app.mymoney.ui.DatePickerFragment
import kotlinx.android.synthetic.main.fragment_new_expense.*


class NewExpenseFragment : Fragment() {

    private lateinit var appDatabase: AppDatabase
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController =
            (activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container_view) as NavHostFragment).navController

        appDatabase = AppDatabase.invoke(requireContext())
        appDatabase.getCategoryDao().getAllCategories().observe(viewLifecycleOwner, Observer {
            val adapter = ArrayAdapter<Category>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                it
            )
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_category.setAdapter(adapter)
        })
        btnSaveClick(view)
        btnCancelClick(view)

        btn_picker.setOnClickListener {
            DatePickerFragment(requireContext())
                .show(activity?.supportFragmentManager!!, null)

        }
    }

    private fun btnCancelClick(view: View) {
        view.findViewById<MaterialButton>(R.id.btn_cancel).setOnClickListener {
            navController.navigate(R.id.action_newExpenseFragment_to_expenseHomeFragment)
        }
    }

    private fun btnSaveClick(view: View) {
        view.findViewById<MaterialButton>(R.id.btn_save).setOnClickListener {
            val title = edt_expense_title.text.toString()
            val price = edt_expense_price.text.toString()
            if (title.isEmpty() || price.isEmpty()) {
                Toast.makeText(activity, "Some field is empty", Toast.LENGTH_SHORT).show()
            } else {
                val expense = Expense(0, title, price.toLong(), 0)
                val bundle = Bundle().apply {
                    putParcelable("Expense", expense)
                    putString("CategoryTitle", spinner_category.text.toString())
                }
                navController.navigate(
                    R.id.action_newExpenseFragment_to_expenseHomeFragment,
                    bundle
                )
            }
        }
    }
}

