package ir.adicom.app.mymoney.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ir.adicom.app.mymoney.R
import ir.adicom.app.mymoney.models.Category


class NewCategoryFragment : Fragment() {

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
        btnCancelClick(view)
        btnSaveClick(view)
    }

    private fun btnSaveClick(view: View) {
        val edtCategoryTitle = view.findViewById<TextInputLayout>(R.id.edt_category_title)
        val title = edtCategoryTitle.editText?.text.toString()
        view.findViewById<MaterialButton>(R.id.btn_save).setOnClickListener {
            val categoryHomeFragment = CategoryHomeFragment()
            val bundle = Bundle().apply {
                putParcelable("Category", Category(0, title))
            }
            categoryHomeFragment.arguments = bundle

            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fl_fragment_place_holder, categoryHomeFragment)
                ?.commit()
        }
    }

    private fun btnCancelClick(view: View) {
        view.findViewById<MaterialButton>(R.id.btn_cancel).setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fl_fragment_place_holder, CategoryHomeFragment())
                ?.commit()
        }
    }
}