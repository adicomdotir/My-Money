package ir.adicom.app.mymoney.ui.category

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.adicom.app.mymoney.MainActivity
import ir.adicom.app.mymoney.R
import ir.adicom.app.mymoney.db.AppDatabase
import ir.adicom.app.mymoney.ui.adapters.CategoryAdapter
import ir.adicom.app.mymoney.models.Category
import ir.adicom.app.mymoney.ui.adapters.OnCategoryDeleteListener
import kotlinx.android.synthetic.main.fragment_category_home.*
import kotlinx.coroutines.*

class CategoryHomeFragment : Fragment(), OnCategoryDeleteListener {
    private lateinit var rvCategory: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var navController: NavController
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         setHasOptionsMenu(true)

        val category = arguments?.getParcelable<Category>("Category")
        if (category != null) {
            MainScope().launch {
                appDatabase.getCategoryDao().addCategory(category)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = (activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container_view) as NavHostFragment).navController
        rvCategory = view.findViewById(R.id.rv_category)
        categoryAdapter = CategoryAdapter(listOf(), this)
        rvCategory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvCategory.adapter = categoryAdapter

        appDatabase = context.let { AppDatabase.invoke(it!!) }
        appDatabase.getCategoryDao().getAllCategories().observe(
            viewLifecycleOwner,
            Observer {
                if (it.isNotEmpty()) {
                    tv_no_categories_available.visibility = View.GONE
                    rvCategory.visibility = View.VISIBLE
                    categoryAdapter.setCategoryList(it)
                }
            }
        )

        (requireActivity() as MainActivity).supportActionBar?.title =  "لیست دسته ها"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_home, container, false);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                navController.navigate(R.id.action_categoryHomeFragment_to_newCategoryFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCategoryDelete(category: Category) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("ایا مطمئن هستید؟")
            builder.apply {
                setPositiveButton(R.string.yes,
                    DialogInterface.OnClickListener { dialog, id ->
                        CoroutineScope(Dispatchers.IO).launch {
                            val result = appDatabase.getExpenseDao().existCategoryIdOnExpense(category.id)
                            if (!result) {
                                appDatabase.getCategoryDao().deleteCategory(category)
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(requireContext(), " این دسته به علت استفاده در هزینه قابل حذف نمیباشد", Toast.LENGTH_SHORT).show()
                                }
                            }
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