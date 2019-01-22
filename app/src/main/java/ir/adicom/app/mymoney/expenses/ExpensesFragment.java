package ir.adicom.app.mymoney.expenses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.addeditcategory.AddEditCategoryActivity;
import ir.adicom.app.mymoney.addeditexpense.AddEditExpenseActivity;
import ir.adicom.app.mymoney.data.Expense;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesFragment extends Fragment implements ExpensesContract.View {

    private ExpensesContract.Presenter mExpensesPresenter;
    private ListView lvExpenses;
    private ExpensesAdapter mExpensesAdapter;

    ExpenseItemListener mItemListener = new ExpenseItemListener() {
        @Override
        public void onExpenseClick(Expense clickedExpense) {
            // presenter edit expense
        }
    };

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

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_expense);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExpensesPresenter.addNewExpense();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExpensesAdapter = new ExpensesAdapter(new ArrayList<Expense>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mExpensesPresenter.start();
    }

    @Override
    public void setPresenter(ExpensesContract.Presenter presenter) {
        this.mExpensesPresenter = presenter;
    }

    @Override
    public void showProgressBar() {
        // progress bar
    }

    @Override
    public void showExpenses(List<Expense> expenses) {
        mExpensesAdapter.replaceData(expenses);
        lvExpenses.setAdapter(mExpensesAdapter);
    }

    @Override
    public void showAddExpense() {
        Intent intent = new Intent(getContext(), AddEditExpenseActivity.class);
        startActivityForResult(intent, AddEditExpenseActivity.REQUEST_ADD_EXPENSE);
    }

    private static class ExpensesAdapter extends BaseAdapter {

        private List<Expense> mExpenses;
        private ExpenseItemListener mItemListener;

        public ExpensesAdapter(List<Expense> expenses, ExpenseItemListener itemListener) {
            setList(expenses);
            mItemListener = itemListener;
        }

        public void replaceData(List<Expense> expenses) {
            setList(expenses);
            notifyDataSetChanged();
        }

        private void setList(List<Expense> expenses) {
            mExpenses = expenses;
        }

        @Override
        public int getCount() {
            return mExpenses.size();
        }

        @Override
        public Expense getItem(int i) {
            return mExpenses.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.expense_item, viewGroup, false);
            }

            final Expense expense = getItem(i);
            StringBuilder sb = new StringBuilder();
            sb.append(expense.getId()).append("\n")
                    .append(expense.getTitle()).append("\n")
                    .append(expense.getPrice()).append("\n")
                    .append(expense.getDate()).append("\n")
                    .append(expense.getCategoryId());
            TextView tvItem = (TextView) rowView.findViewById(R.id.tv_item);
            tvItem.setText(sb);
            return rowView;
        }
    }

    public interface ExpenseItemListener {
        void onExpenseClick(Expense clickedExpense);
    }
}
