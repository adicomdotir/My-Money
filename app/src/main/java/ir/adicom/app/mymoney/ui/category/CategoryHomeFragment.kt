package ir.adicom.app.mymoney.ui.category

import android.os.Bundle
import android.view.*
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
import ir.adicom.app.mymoney.ui.adapters.CategoryAdapter
import ir.adicom.app.mymoney.models.Category
import kotlinx.android.synthetic.main.fragment_category_home.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class CategoryHomeFragment : Fragment() {
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
        categoryAdapter = CategoryAdapter(listOf())
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
}