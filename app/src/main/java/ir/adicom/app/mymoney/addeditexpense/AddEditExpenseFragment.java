package ir.adicom.app.mymoney.addeditexpense;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.adicom.app.mymoney.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditExpenseFragment extends Fragment implements AddEditExpenseContract.View {
    private AddEditExpenseContract.Presenter mPresenter;

    public AddEditExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_expense, container, false);
    }

    @Override
    public void setPresenter(AddEditExpenseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showEmptyExpenseError() {

    }

    @Override
    public void showExpensesList() {

    }
}
