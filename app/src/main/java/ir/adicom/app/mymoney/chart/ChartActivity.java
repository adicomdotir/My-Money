package ir.adicom.app.mymoney.chart;

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
import ir.adicom.app.mymoney.expenses.ExpensesActivity;
import ir.adicom.app.mymoney.util.ActivityUtils;
import ir.adicom.app.mymoney.util.AppExecutors;

public class ChartActivity extends AppCompatActivity {

    private ChartContract.Presenter mPresenter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.categories_text);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ChartFragment registerFragment = (ChartFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (registerFragment == null) {
            registerFragment = new ChartFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), registerFragment, R.id.contentFrame);
        }

        AppExecutors appExecutors = new AppExecutors();
        CategoriesDataSource cds = CategoriesLocalDataSource.getInstance(appExecutors, ((App) getApplication()).getDaoSession().getCategoryDao());
        ExpensesDataSource eds = ExpensesLocalDataSource.getInstance(appExecutors, ((App) getApplication()).getDaoSession().getExpenseDao());
        mPresenter = new ChartPresenter(registerFragment, cds, eds);
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
                            case R.id.list_navigation_menu_item_expenses:
                                startActivity(new Intent(getApplicationContext(), ExpensesActivity.class));
                                finish();
                                break;
                            case R.id.list_navigation_menu_item_categories:
                                startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
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