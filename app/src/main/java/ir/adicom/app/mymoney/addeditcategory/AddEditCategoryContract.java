package ir.adicom.app.mymoney.addeditcategory;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;

/**
 * AddEditCategoryContract
 * Created by Y.P on 20/11/2018.
 */

interface AddEditCategoryContract {
    interface View extends BaseView<Presenter> {
        void showEmptyCategoryError();

        void showCategoriesList();

        void setTitle(String title);
    }

    interface Presenter extends BasePresenter {
        void saveCategory(String title);

        void deleteCategory();
    }
}
