package ir.adicom.app.mymoney.addeditexpense;

import android.support.annotation.Nullable;

import java.util.List;

import ir.adicom.app.mymoney.App;
import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.data.source.CategoriesDataSource;
import ir.adicom.app.mymoney.data.source.ExpensesDataSource;
import ir.adicom.app.mymoney.data.source.local.CategoriesLocalDataSource;
import ir.adicom.app.mymoney.util.AppExecutors;

/**
 * AddEditExpensePresenter
 * Created by Y.P on 20/11/2018.
 */

public class AddEditExpensePresenter implements AddEditExpenseContract.Presenter {
    private  AddEditExpenseContract.View mView;
    private CategoriesDataSource mCategoriesDataSource;
    private ExpensesDataSource mExpensesDataSource;
    @Nullable
    private Long mExpenseId;

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
    public void saveExpense(String title) {
        if (isExpenseNew()) {
            createExpense(title);
        } else {
            updateExpense(title);
        }
    }

    @Override
    public void loadCategories() {
        mCategoriesDataSource.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<Category> categories) {
                mView.setCategories(categories);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void updateExpense(String title) {
        Category newCategory = new Category();
        newCategory.setTitle(title);
        // Todo: updateExpense is incomplete
//        if (newCategory.isEmpty()) {
//            mAddCategoryView.showEmptyCategoryError();
//        } else {
//            mCategorysRepository.saveCategory(newCategory);
//            mAddCategoryView.showCategorysList();
//        }
    }

    private void createExpense(String title) {
        if (isExpenseNew()) {
            throw new RuntimeException("updateCategory() was called but task is new.");
        }
        // Todo: createExpense is incomplete
//        mCategorysRepository.saveCategory(new Category(mCategoryId,  title));
//        mAddCategoryView.showCategorysList(); // After an edit, go back to the list.
    }

    public boolean isExpenseNew() {
        return mExpenseId == null;
    }
}
