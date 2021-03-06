package ir.adicom.app.mymoney.addeditexpense;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.adicom.app.mymoney.App;
import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.categories.CategoriesActivity;
import ir.adicom.app.mymoney.data.source.CategoriesDataSource;
import ir.adicom.app.mymoney.data.source.ExpensesDataSource;
import ir.adicom.app.mymoney.data.source.local.CategoriesLocalDataSource;
import ir.adicom.app.mymoney.data.source.local.ExpensesLocalDataSource;
import ir.adicom.app.mymoney.chart.ChartActivity;
import ir.adicom.app.mymoney.util.ActivityUtils;
import ir.adicom.app.mymoney.util.AppExecutors;

public class AddEditExpenseActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_EXPENSE = 1;
    private AddEditExpensePresenter addEditExpensePresenter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_expense);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Long expenseId = getIntent().getLongExtra(AddEditExpenseFragment.ARGUMENT_EDIT_EXPENSE_ID, 0);
        expenseId = expenseId == 0 ? null : expenseId;

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (expenseId == null) {
            toolbar.setTitle("هزینه جدید");
        } else {
            toolbar.setTitle("ویرایش هزینه");
        }
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert mDrawerLayout != null;
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        AddEditExpenseFragment addEditExpenseFragment = (AddEditExpenseFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (addEditExpenseFragment == null) {
            addEditExpenseFragment = new AddEditExpenseFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), addEditExpenseFragment, R.id.contentFrame);
        }

        AppExecutors appExecutors = new AppExecutors();
        CategoriesDataSource cds = CategoriesLocalDataSource.getInstance(appExecutors, ((App) getApplication()).getDaoSession().getCategoryDao());
        ExpensesDataSource eds = ExpensesLocalDataSource.getInstance(appExecutors, ((App) getApplication()).getDaoSession().getExpenseDao());
        addEditExpensePresenter = new AddEditExpensePresenter(expenseId, addEditExpenseFragment, cds, eds);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item_categories:
                                startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
                                break;
                            case R.id.list_navigation_menu_item_chart:
                                startActivity(new Intent(getApplicationContext(), ChartActivity.class));
                                finish();
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}
