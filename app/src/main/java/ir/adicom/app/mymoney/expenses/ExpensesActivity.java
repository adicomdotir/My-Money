package ir.adicom.app.mymoney.expenses;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.util.ActivityUtils;

public class ExpensesActivity extends AppCompatActivity {

    private ExpensesPresenter expensesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        ExpensesFragment expensesFragment = (ExpensesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (expensesFragment == null) {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), expensesFragment, R.id.contentFrame);
        }

        expensesPresenter = new ExpensesPresenter(expensesFragment);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
