package ir.adicom.app.mymoney.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import ir.adicom.app.mymoney.R
import ir.adicom.app.mymoney.models.Category


class NewCategoryFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var edtCategoryTitle: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = (activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container_view) as NavHostFragment).navController
        edtCategoryTitle = view.findViewById<TextInputLayout>(R.id.edt_category_title)
        btnCancelClick(view)
        btnSaveClick(view)
    }

    private fun btnSaveClick(view: View) {
        view.findViewById<MaterialButton>(R.id.btn_save).setOnClickListener {
            val title = edtCategoryTitle.editText?.text.toString()
            val bundle = Bundle().apply {
                putParcelable("Category", Category(0, title))
            }
            navController.navigate(R.id.action_newCategoryFragment_to_categoryHomeFragment, bundle)
        }
    }

    private fun btnCancelClick(view: View) {
        view.findViewById<MaterialButton>(R.id.btn_cancel).setOnClickListener {
            navController.navigate(R.id.action_newCategoryFragment_to_categoryHomeFragment)
        }
    }
}