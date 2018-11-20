package ir.adicom.app.mymoney.categories;

import java.util.List;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;
import ir.adicom.app.mymoney.data.Category;

/**
 *
 * Created by Y.P on 04/09/2018.
 */

public interface CategoriesContract {
    interface View extends BaseView<Presenter> {

        void showProgressBar();

        void showCategories(List<Category> categories);
    }

    interface Presenter extends BasePresenter {

    }
}