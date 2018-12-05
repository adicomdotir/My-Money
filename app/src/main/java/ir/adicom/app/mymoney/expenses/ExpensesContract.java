package ir.adicom.app.mymoney.expenses;

import java.util.List;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;
import ir.adicom.app.mymoney.data.Expense;

/**
 *
 * Created by adicom on 8/29/18.
 */

public interface ExpensesContract {
    interface View extends BaseView<Presenter> {
        void showProgressBar();

        void showExpenses(List<Expense> expenses);
    
        void showAddExpense();
    }

    interface Presenter extends BasePresenter {

        void addNewExpense();
    }
}
