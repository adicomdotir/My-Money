package ir.adicom.app.mymoney.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ir.adicom.app.mymoney.R
import ir.adicom.app.mymoney.db.AppDatabase
import ir.adicom.app.mymoney.fragments.adapters.CategoryAdapter
import ir.adicom.app.mymoney.models.Category
import kotlinx.android.synthetic.main.fragment_category_home.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.math.log

class CategoryHomeFragment : Fragment() {
    lateinit var rvCategory: RecyclerView
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var navController: NavController
    var appDatabase: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val category = arguments?.getParcelable<Category>("Category")
        Log.e("TAG", category.toString())
        if (category != null) {
            MainScope().launch {
                appDatabase?.getCategoryDao()?.addCategory(category)
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

        appDatabase = context?.let { AppDatabase.invoke(it) }
        appDatabase?.getCategoryDao()?.getAllCategories()?.observe(
            viewLifecycleOwner,
            Observer {
                if (it.isNotEmpty()) {
                    tv_no_notes_available.visibility = View.GONE
                    rvCategory.visibility = View.VISIBLE
                    categoryAdapter.setCategoryList(it)
                }
            }
        )

        view.findViewById<FloatingActionButton>(R.id.fab_category_add).setOnClickListener {
            navController.navigate(R.id.action_categoryHomeFragment_to_newCategoryFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_home, container, false);
    }
}