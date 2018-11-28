package ir.adicom.app.mymoney.addeditexpense;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;

/**
 * AddEditExpenseContract
 * Created by Y.P on 20/11/2018.
 */

interface AddEditExpenseContract {
    interface View extends BaseView<Presenter> {
        void showEmptyExpenseError();

        void showExpensesList();
    }

    interface Presenter extends BasePresenter {
        void saveExpense(String title);
    }
}
