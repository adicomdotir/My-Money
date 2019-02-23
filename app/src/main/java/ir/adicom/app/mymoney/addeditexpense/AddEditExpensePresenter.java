package ir.adicom.app.mymoney.addeditexpense;

import android.support.annotation.Nullable;

import java.util.List;

import ir.adicom.app.mymoney.App;
import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.data.Expense;
import ir.adicom.app.mymoney.data.source.CategoriesDataSource;
import ir.adicom.app.mymoney.data.source.ExpensesDataSource;
import ir.adicom.app.mymoney.data.source.local.CategoriesLocalDataSource;
import ir.adicom.app.mymoney.util.AppExecutors;

/**
 * AddEditExpensePresenter
 * Created by Y.P on 20/11/2018.
 */

public class AddEditExpensePresenter implements AddEditExpenseContract.Presenter, ExpensesDataSource.GetExpenseCallback {
    private  AddEditExpenseContract.View mView;
    private CategoriesDataSource mCategoriesDataSource;
    private ExpensesDataSource mExpensesDataSource;
    @Nullable
    private Long mExpenseId;
    private List<Category> mCategories;

    public AddEditExpensePresenter(@Nullable Long expenseId, AddEditExpenseContract.View view, CategoriesDataSource cds, ExpensesDataSource eds) {
        this.mView = view;
        mExpenseId = expenseId;
        mCategoriesDataSource = cds;
        mExpensesDataSource = eds;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadCategories();
    }

    @Override
    public void saveExpense(String title, Long categoryId, Long price) {
        if (isExpenseNew()) {
            createExpense(title, categoryId, price);
        } else {
            updateExpense(title, categoryId, price);
        }
    }

    @Override
    public void loadCategories() {
        mCategoriesDataSource.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<Category> categories) {
                mView.setCategories(categories);
                mCategories = categories;
                if (mExpenseId != null) {
                    mExpensesDataSource.getExpense(mExpenseId, AddEditExpensePresenter.this);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void createExpense(String title, Long categoryId, Long price) {
        Expense newExpense = new Expense();
        newExpense.setTitle(title);
        newExpense.setCategoryId(categoryId);
        newExpense.setPrice(price);
        newExpense.setDate(System.currentTimeMillis());

        if (newExpense.isEmpty()) {
            mView.showEmptyExpenseError();
        } else {
            mExpensesDataSource.saveExpense(newExpense);
            mView.showExpensesList();
        }
    }

    private void updateExpense(String title, Long categoryId, Long price) {
        if (isExpenseNew()) {
            throw new RuntimeException("updateExpense() was called but expense is new.");
        }

        mExpensesDataSource.saveExpense(new Expense(mExpenseId,  title, categoryId, price, System.currentTimeMillis()));
        mView.showExpensesList(); // After an edit, go back to the list.
    }

    public boolean isExpenseNew() {
        return mExpenseId == null;
    }

    private int getSelectionCategoryId(Long categoryId) {
        for (int i = 0; i < mCategories.size(); i++) {
            Category cate = mCategories.get(i);
            if (cate.getId() == categoryId) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onExpenseLoaded(Expense expense) {
        mView.setTitle(expense.getTitle());
        mView.setPrice(expense.getPrice());
        mView.setSelectionCategory(getSelectionCategoryId(expense.getCategoryId()));
    }

    @Override
    public void onDataNotAvailable() {

    }
}
