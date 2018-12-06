package ir.adicom.app.mymoney.addeditcategory;

import android.support.annotation.Nullable;
import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.data.source.CategoriesDataSource;

/**
 * AddEditCategoryPresenter
 * Created by Y.P on 20/11/2018.
 */

public class AddEditCategoryPresenter implements AddEditCategoryContract.Presenter {
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

    }

    @Override
    public void saveCategory(String title) {
        if (isNewCategory()) {
            createCategory(title);
        } else {
            updateCategory(title);
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
//        mCategorysRepository.saveCategory(new Category(mCategoryId,  title));
//        mAddCategoryView.showCategorysList(); // After an edit, go back to the list.
    }
}
