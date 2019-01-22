package ir.adicom.app.mymoney.addeditexpense;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.data.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditExpenseFragment extends Fragment implements AddEditExpenseContract.View {
    private AddEditExpenseContract.Presenter mPresenter;
    private Spinner catSpinner;
    private EditText mTitle;
    private EditText mPrice;

    public AddEditExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_expense, container, false);
        catSpinner = (Spinner) root.findViewById(R.id.spinner_category);
        mTitle = (EditText) root.findViewById(R.id.add_expense_title);
        mPrice = (EditText) root.findViewById(R.id.et_expense_price);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_expense);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitle.getText().toString();
                Long price = Long.parseLong(mPrice.getText().toString());
                Long categoryId = 0L;
                mPresenter.saveExpense(title, categoryId, price);
            }
        });
    }

    @Override
    public void setPresenter(AddEditExpenseContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.start();
    }

    @Override
    public void showEmptyExpenseError() {
        Snackbar.make(mTitle, getString(R.string.empty_title_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showExpensesList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();

    }

    @Override
    public void setCategories(List<Category> categories) {
        // TODO: 1/7/19 change this bad smells
        List<String> list = new ArrayList<>();
        for (Category c: categories) {
            list.add(c.getTitle());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(dataAdapter);
    }
}
