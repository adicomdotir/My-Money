package ir.adicom.app.mymoney.expenses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ir.adicom.app.mymoney.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesFragment extends Fragment implements ExpensesContract.View {

    private ExpensesContract.Presenter mExpensesPresenter;
    private ListView lvExpenses;

    public ExpensesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expenses, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvExpenses = (ListView) view.findViewById(R.id.expenses_list);
    }

    @Override
    public void setPresenter(ExpensesContract.Presenter presenter) {
        this.mExpensesPresenter = presenter;
    }
}
