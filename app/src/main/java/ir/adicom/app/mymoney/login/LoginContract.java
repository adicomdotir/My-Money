package ir.adicom.app.mymoney.login;

import android.content.Context;

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

        void initLoginInfo(String username, String password);
    }

    interface Presenter extends BasePresenter {
        void clickRegisterBtn();

        void clickLoginBtn(String username, String password);

        void isSaveLogin(Context context);

        boolean saveLogin(Context context, boolean checked);

        void saveLoginInfo(Context context, String string, String string1);
    }
}
