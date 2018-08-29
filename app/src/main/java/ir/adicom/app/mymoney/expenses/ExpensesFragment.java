package ir.adicom.app.mymoney.expenses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.adicom.app.mymoney.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesFragment extends Fragment implements ExpensesContract.View {

    private ExpensesContract.Presenter mExpensesPresenter;

    public ExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expenses, container, false);
    }

    @Override
    public void setPresenter(ExpensesContract.Presenter presenter) {
        this.mExpensesPresenter = presenter;
    }
}
