package ir.adicom.app.mymoney.addeditcategory;

import android.support.annotation.Nullable;

import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.data.source.CategoriesDataSource;

/**
 * AddEditCategoryPresenter
 * Created by Y.P on 20/11/2018.
 */

public class AddEditCategoryPresenter implements AddEditCategoryContract.Presenter, CategoriesDataSource.GetCategoriesCallback {
    private  AddEditCategoryContract.View mView;
    private CategoriesDataSource mCategoriesDataSource;
    @Nullable
    private Long mCategoryId;

    public AddEditCategoryPresenter(@Nullable Long categoryId, AddEditCategoryContract.View view, CategoriesDataSource cds) {
        mCategoryId = categoryId;
        mView = view;
        mCategoriesDataSource = cds;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (mCategoryId != null) {
            mCategoriesDataSource.getCategory(mCategoryId, this);
        }
    }

    @Override
    public void saveCategory(String title) {
        if (isNewCategory()) {
            createCategory(title);
        } else {
            updateCategory(title);
        }
    }

    @Override
    public void deleteCategory() {
        if (!isNewCategory()) {
            mCategoriesDataSource.deleteCategory(mCategoryId);
            mView.showCategoriesList();
        }
    }

    private boolean isNewCategory() {
        return mCategoryId == null;
    }

    private void createCategory(String title) {
        Category newCategory = new Category();
        newCategory.setTitle(title);
        if (newCategory.isEmpty()) {
            mView.showEmptyCategoryError();
        } else {
            mCategoriesDataSource.saveCategory(newCategory);
            mView.showCategoriesList();
        }
    }

    private void updateCategory(String title) {
        if (isNewCategory()) {
            throw new RuntimeException("updateCategory() was called but task is new.");
        }
        mCategoriesDataSource.updateCategory(new Category(mCategoryId,  title));
        mView.showCategoriesList(); // After an edit, go back to the list.
    }

    @Override
    public void onCategoryLoaded(Category category) {
        mView.setTitle(category.getTitle());
    }

    @Override
    public void onDataNotAvailable() {

    }
}
