package ir.adicom.app.mymoney.chart;

import java.util.Map;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;

/**
 * Created by Y.P on 28/08/2018.
 */

public interface ChartContract {
    interface View extends BaseView<Presenter> {
        void initializeChart(Map<Long, Long> result);
    }

    interface Presenter extends BasePresenter {
        void loadCategories();

        String getCategory(Long key);
    }
}
