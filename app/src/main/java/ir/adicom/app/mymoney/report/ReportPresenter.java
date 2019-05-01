package ir.adicom.app.mymoney.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.data.Expense;
import ir.adicom.app.mymoney.data.source.CategoriesDataSource;
import ir.adicom.app.mymoney.data.source.ExpensesDataSource;

/**
 * Created by Y.P on 28/08/2018.
 * Modified 01/05/2019
 */

public class ReportPresenter implements ReportContract.Presenter, ExpensesDataSource.LoadExpensesCallback {

    private ReportContract.View mView;
    private CategoriesDataSource mCategoriesDataSource;
    private ExpensesDataSource mExpensesDataSource;
    private List<Category> mCategories;
    private List<Expense> mExpense;
    private Map<Long, Long> exensesByCat = new HashMap<>();
    private int categoriesCount = 0;

    public ReportPresenter(ReportContract.View registerView, CategoriesDataSource cds, ExpensesDataSource eds) {
        this.mView = registerView;
        mCategoriesDataSource = cds;
        mExpensesDataSource = eds;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadCategories();
    }

    @Override
    public void loadCategories() {
        mCategoriesDataSource.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<Category> categories) {
                mCategories = categories;
                categoriesCount = categories.size();
                for (int i = 0; i < mCategories.size(); i++) {
                    loadExpenseByCategory(mCategories.get(i).getId());
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public String getCategory(Long key) {
        for (Category category : mCategories) {
            if (category.getId() == key) {
                return category.getTitle();
            }
        }
        return null;
    }

    public void loadExpenseByCategory(Long id) {
        mExpensesDataSource.getExpenseByCategory(id, this);
    }

    @Override
    public void onExpensesLoaded(List<Expense> expenses) {
        mExpense = expenses;
        for (int i = 0; i < expenses.size(); i++) {
            Long price = exensesByCat.get(expenses.get(i).getCategoryId());
            price = price == null ? 0 : price;
            price = price + expenses.get(i).getPrice();
            exensesByCat.put(expenses.get(i).getCategoryId(), price);
        }
        categoriesCount--;
        if (categoriesCount == 0) {
//            mView.initializeChart(exensesByCat);
            mView.setReportList();
        }
    }

    @Override
    public void onDataNotAvailable() {
        mExpense = null;
        categoriesCount--;
    }
}
