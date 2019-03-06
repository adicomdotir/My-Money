package ir.adicom.app.mymoney.addeditexpense;

import android.support.annotation.Nullable;

import java.util.List;

import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.data.Expense;
import ir.adicom.app.mymoney.data.source.CategoriesDataSource;
import ir.adicom.app.mymoney.data.source.ExpensesDataSource;

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
    public void saveExpense(String title, Long categoryId, Long price, Long date) {
        if (isExpenseNew()) {
            createExpense(title, categoryId, price, date);
        } else {
            updateExpense(title, categoryId, price, date);
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

    @Override
    public void deleteExpense() {
        if (!isExpenseNew()) {
            mExpensesDataSource.deleteExpense(mExpenseId);
            mView.showExpensesList();
        }
    }

    private void createExpense(String title, Long categoryId, Long price, Long date) {
        Expense newExpense = new Expense();
        newExpense.setTitle(title);
        newExpense.setCategoryId(categoryId);
        newExpense.setPrice(price);
        newExpense.setDate(date);

        if (newExpense.isEmpty()) {
            mView.showEmptyExpenseError();
        } else {
            mExpensesDataSource.saveExpense(newExpense);
            mView.showExpensesList();
        }
    }

    private void updateExpense(String title, Long categoryId, Long price, Long date) {
        if (isExpenseNew()) {
            throw new RuntimeException("updateExpense() was called but expense is new.");
        }

        mExpensesDataSource.updateExpense(new Expense(mExpenseId, title, price, categoryId, date));
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
        mView.setDate(expense.getDate());
        mView.setSelectionCategory(getSelectionCategoryId(expense.getCategoryId()));
    }

    @Override
    public void onDataNotAvailable() {

    }
}
