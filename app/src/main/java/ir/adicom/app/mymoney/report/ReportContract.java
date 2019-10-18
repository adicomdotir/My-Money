package ir.adicom.app.mymoney.report;

import java.util.List;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;
import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.data.Filter;

/**
 *
 * Created by Y.P on 28/08/2018.
 */

public interface ReportContract {
    interface View extends BaseView<Presenter> {
        void setReportList(List<Filter> exensesByCat);

        void initDialog(List<Category> categories);
    }

    interface Presenter extends BasePresenter {
        void loadCategories();

        String getCategory(Long key);

        void loadCategoriesForDialog();

        void loadExpenses(int tag, long id);

    }

    interface ReportDialogListener {
        void dialogClose(int tag, long id);
    }
}
