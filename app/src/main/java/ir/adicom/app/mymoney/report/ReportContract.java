package ir.adicom.app.mymoney.report;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;

/**
 * Created by Y.P on 28/08/2018.
 */

public interface ReportContract {
    interface View extends BaseView<Presenter> {

        void successRegister();

        void failedRegister(String message);
    }

    interface Presenter extends BasePresenter {
        void submitRegister(String email, String username, String password);
    }
}
