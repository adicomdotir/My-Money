package ir.adicom.app.mymoney.login;

import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by Y.P on 28/08/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {
    @Override
    public void start() {

    }

    public void checkLogin(View arg0) {

//        final String email = emailEditText.getText().toString();
//        if (!isValidEmail(email)) {
//            //Set error message for email field
//            emailEditText.setError("Invalid Email");
//        }
//
//        final String pass = passEditText.getText().toString();
//        if (!isValidPassword(pass)) {
//            //Set error message for password field
//            passEditText.setError("Password cannot be empty");
//        }
//
//        if(isValidEmail(email) && isValidPassword(pass))
//        {
//            // Validation Completed
//        }

    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 4) {
            return true;
        }
        return false;
    }
}
