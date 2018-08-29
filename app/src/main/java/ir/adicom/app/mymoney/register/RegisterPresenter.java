package ir.adicom.app.mymoney.register;

/**
 *
 * Created by Y.P on 28/08/2018.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View mRegisterView;

    public RegisterPresenter(RegisterContract.View registerView) {
        this.mRegisterView = registerView;
        this.mRegisterView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void submitRegister(String email, String username, String password) {
        if (email.length() == 0 || username.length() == 0 || password.length() == 0) {
            mRegisterView.failedRegister("ایمیل یا نام کاربری یا رمز عبور وارد نشده است!");
        } else {
            mRegisterView.successRegister();
        }
    }
}
