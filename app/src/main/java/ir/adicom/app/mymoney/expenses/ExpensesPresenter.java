package ir.adicom.app.mymoney.expenses;

import java.util.List;

import ir.adicom.app.mymoney.data.Expense;
import ir.adicom.app.mymoney.data.source.ExpensesDataSource;

/**
 *
 * Created by adicom on 8/29/18.
 */

public class ExpensesPresenter implements ExpensesContract.Presenter {

    private ExpensesContract.View mExpensesView;
    private ExpensesDataSource mExpensesDataSource;

    public ExpensesPresenter(ExpensesContract.View expensesView, ExpensesDataSource cds) {
        this.mExpensesView = expensesView;
        this.mExpensesDataSource = cds;
        expensesView.setPresenter(this);
    }

    @Override
    public void start() {
        loadExpenses();
    }

    public void loadExpenses() {
        mExpensesDataSource.getExpenses(new ExpensesDataSource.LoadExpensesCallback() {
            @Override
            public void onExpensesLoaded(List<Expense> expenses) {
                mExpensesView.showExpenses(expenses);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void addNewExpense() {
        mExpensesView.showAddExpense();
    }
}
