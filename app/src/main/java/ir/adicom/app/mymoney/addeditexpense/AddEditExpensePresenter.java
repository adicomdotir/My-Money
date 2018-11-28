package ir.adicom.app.mymoney.addeditexpense;

import android.support.annotation.Nullable;

import ir.adicom.app.mymoney.data.Category;

/**
 * AddEditExpensePresenter
 * Created by Y.P on 20/11/2018.
 */

public class AddEditExpensePresenter implements AddEditExpenseContract.Presenter {
    private  AddEditExpenseContract.View mView;
    @Nullable
    private Long mExpenseId;

    public AddEditExpensePresenter(@Nullable Long expenseId, AddEditExpenseContract.View view) {
        this.mView = view;
        mExpenseId = expenseId;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void saveExpense(String title) {
        if (isExpenseNew()) {
            createExpense(title);
        } else {
            updateExpense(title);
        }
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
