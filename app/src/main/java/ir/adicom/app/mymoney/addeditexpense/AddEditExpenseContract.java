package ir.adicom.app.mymoney.addeditexpense;

import java.util.List;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;
import ir.adicom.app.mymoney.data.Category;

/**
 * AddEditExpenseContract
 * Created by Y.P on 20/11/2018.
 */

interface AddEditExpenseContract {
    interface View extends BaseView<Presenter> {
        void showEmptyExpenseError();

        void showExpensesList();

        void setCategories(List<Category> categories);
    }

    interface Presenter extends BasePresenter {
        void saveExpense(String title);

        void loadCategories();
    }
}
