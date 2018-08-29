package ir.adicom.app.mymoney.register;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.util.ActivityUtils;

public class RegisterActivity extends AppCompatActivity {

    private RegisterContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterFragment registerFragment =  (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (registerFragment == null) {
            registerFragment = new RegisterFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), registerFragment, R.id.contentFrame);
        }

        mPresenter = new RegisterPresenter(registerFragment);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
