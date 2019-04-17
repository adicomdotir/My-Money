package ir.adicom.app.mymoney.login;

import android.content.Context;

import ir.adicom.app.mymoney.util.MySharedPreferences;

/**
 *
 * Created by Y.P on 28/08/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;

    public LoginPresenter(LoginContract.View loginView) {
        this.mLoginView = loginView;
        this.mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void clickRegisterBtn() {
        mLoginView.showRegisterScreen();
    }

    @Override
    public void clickLoginBtn(String username, String password) {
        if (username.length() == 0 || password.length() == 0) {
            mLoginView.showError("نام کاربری یا رمز عبور وارد نشده است!");
        } else {
            mLoginView.loginSuccess();
        }
    }

    @Override
    public void isSaveLogin(Context context) {
        if (MySharedPreferences.getPreferences(context, "saveLogin").equals("true")) {
            String tempUsername = MySharedPreferences.getPreferences(context, "username");
            String tempPassword = MySharedPreferences.getPreferences(context, "password");
            mLoginView.initLoginInfo(tempUsername, tempPassword);
        }
    }

    @Override
    public boolean saveLogin(Context context, boolean checked) {
        if (checked) {
            MySharedPreferences.savePreferences(context, "saveLogin", "true");
        } else {
            MySharedPreferences.removeAllSharedPreferences(context);
        }
        return checked;
    }

    @Override
    public void saveLoginInfo(Context context, String username, String password) {
        MySharedPreferences.savePreferences(context, "username", username);
        MySharedPreferences.savePreferences(context, "password", password);
    }
}
