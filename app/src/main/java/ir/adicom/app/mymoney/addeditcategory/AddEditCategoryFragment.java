package ir.adicom.app.mymoney.addeditcategory;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import ir.adicom.app.mymoney.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditCategoryFragment extends Fragment implements AddEditCategoryContract.View {

    public static final String ARGUMENT_EDIT_CATEGORY_ID = "EDIT_CATEGORY_ID";
    private AddEditCategoryContract.Presenter mPresenter;
    private TextView mTitle;
    private ImageButton mImageBtnDelete;

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
        FloatingActionButton fabDelete =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_delete_category);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                    .setMessage("ایا شما میخواهید این دسته را حذف کنید؟")
                    .setCancelable(false)
                    .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mPresenter.deleteCategory();
                        }
                    })
                    .setNegativeButton("خیر", null)
                    .show();
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
