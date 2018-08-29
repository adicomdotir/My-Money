package ir.adicom.app.mymoney.login;

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
        }
    }
}
