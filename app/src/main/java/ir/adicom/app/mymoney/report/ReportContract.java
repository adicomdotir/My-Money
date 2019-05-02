package ir.adicom.app.mymoney.report;

import java.util.Map;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;

/**
 *
 * Created by Y.P on 28/08/2018.
 */

public interface ReportContract {
    interface View extends BaseView<Presenter> {
        void setReportList(Map<String, Long> exensesByCat);
    }

    interface Presenter extends BasePresenter {
        void loadCategories();

        String getCategory(Long key);
    }
}
