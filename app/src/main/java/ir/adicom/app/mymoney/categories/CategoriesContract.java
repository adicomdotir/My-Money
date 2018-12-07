package ir.adicom.app.mymoney.categories;

import android.support.annotation.NonNull;

import java.util.List;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;
import ir.adicom.app.mymoney.data.Category;

/**
 * interface CategoriesContract
 * Created by Y.P on 04/09/2018.
 */

public interface CategoriesContract {
    interface View extends BaseView<Presenter> {

        void showProgressBar();

        void showCategories(List<Category> categories);

        void showAddCategory();

        void showCategoryDetailsUi(Long categoryId);
    }

    interface Presenter extends BasePresenter {

        void addNewCategory();

        void openCategoryDetail(@NonNull Category requestedCategory);
    }
}
