package ir.adicom.app.mymoney.addeditcategory;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;
import ir.adicom.app.mymoney.categories.CategoriesContract;

/**
 * AddEditCategoryContract
 * Created by Y.P on 20/11/2018.
 */

public interface AddEditCategoryContract {
    interface View extends BaseView<CategoriesContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
