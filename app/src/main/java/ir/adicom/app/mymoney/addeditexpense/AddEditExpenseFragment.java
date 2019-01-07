package ir.adicom.app.mymoney.addeditexpense;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

    public AddEditExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_expense, container, false);
        catSpinner = (Spinner) root.findViewById(R.id.spinner_category);
        return root;
    }

    @Override
    public void setPresenter(AddEditExpenseContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.start();
    }

    @Override
    public void showEmptyExpenseError() {

    }

    @Override
    public void showExpensesList() {

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
