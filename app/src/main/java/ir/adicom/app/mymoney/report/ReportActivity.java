package ir.adicom.app.mymoney.report;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.util.ActivityUtils;

public class ReportActivity extends AppCompatActivity {

    private ReportContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ReportFragment registerFragment = (ReportFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (registerFragment == null) {
            registerFragment = new ReportFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), registerFragment, R.id.contentFrame);
        }

        mPresenter = new ReportPresenter(registerFragment);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
