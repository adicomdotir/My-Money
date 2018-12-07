package ir.adicom.app.mymoney.addeditcategory;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.categories.CategoriesContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditCategoryFragment extends Fragment implements AddEditCategoryContract.View {

    private AddEditCategoryContract.Presenter mPresenter;
    private TextView mTitle;
    public static final String ARGUMENT_EDIT_CATEGORY_ID = "EDIT_CATEGORY_ID";

    public AddEditCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_category);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveCategory(mTitle.getText().toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_category, container, false);
        mTitle = (TextView) root.findViewById(R.id.add_category_title);

        return root;
    }

    @Override
    public void setPresenter(AddEditCategoryContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showEmptyCategoryError() {
        Snackbar.make(mTitle, getString(R.string.empty_title_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showCategoriesList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();

    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }
}
