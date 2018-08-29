package ir.adicom.app.mymoney.login;

import ir.adicom.app.mymoney.BasePresenter;
import ir.adicom.app.mymoney.BaseView;

/**
 *
 * Created by Y.P on 28/08/2018.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showRegisterScreen();

        void showError(String message);

        void loginSucces();
    }

    interface Presenter extends BasePresenter {
        void clickRegisterBtn();

        void clickLoginBtn(String username, String password);
    }
}
