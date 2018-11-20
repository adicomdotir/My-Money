package ir.adicom.app.mymoney.addeditcategory;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.categories.CategoriesContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditCategoryFragment extends Fragment implements AddEditCategoryContract.View {


    public AddEditCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_category, container, false);
    }

    @Override
    public void setPresenter(CategoriesContract.Presenter presenter) {

    }
}
