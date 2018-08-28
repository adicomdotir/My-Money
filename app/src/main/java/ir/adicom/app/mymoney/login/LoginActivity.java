package ir.adicom.app.mymoney.login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ir.adicom.app.mymoney.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, loginFragment);
            transaction.commit();
        }
    }
}
