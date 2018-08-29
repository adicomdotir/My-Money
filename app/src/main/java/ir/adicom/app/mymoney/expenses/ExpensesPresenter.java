package ir.adicom.app.mymoney.expenses;

/**
 * Created by adicom on 8/29/18.
 */

public class ExpensesPresenter implements ExpensesContract.Presenter {

    private ExpensesContract.View mExpensesView;

    public ExpensesPresenter(ExpensesContract.View expensesView) {
        this.mExpensesView = expensesView;
        expensesView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
