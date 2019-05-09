package ir.adicom.app.mymoney.report;

import java.util.List;
import java.util.Map;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;
import ir.adicom.app.mymoney.data.Category;

/**
 *
 * Created by Y.P on 28/08/2018.
 */

public interface ReportContract {
    interface View extends BaseView<Presenter> {
        void setReportList(Map<String, Long> exensesByCat);

        void initDialog(List<Category> categories);
    }

    interface Presenter extends BasePresenter {
        void loadCategories();

        String getCategory(Long key);

        void loadCategoriesForDialog();

    }
}
