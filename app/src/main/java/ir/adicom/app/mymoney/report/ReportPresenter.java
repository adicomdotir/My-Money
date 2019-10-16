package ir.adicom.app.mymoney.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.data.Expense;
import ir.adicom.app.mymoney.data.Filter;
import ir.adicom.app.mymoney.data.source.CategoriesDataSource;
import ir.adicom.app.mymoney.data.source.ExpensesDataSource;
import ir.adicom.app.mymoney.util.CalendarTool;

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
    private Map<String, Long> exensesByCat = new HashMap<>();
    private int categoriesCount = 0;
    private Map<String, Long> filterdExpenses = new HashMap<>();

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

        mExpensesDataSource.getExpensesGroupBy(this);
//        mCategoriesDataSource.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
//            @Override
//            public void onCategoriesLoaded(List<Category> categories) {
//                mCategories = categories;
//                categoriesCount = categories.size();
//                for (int i = 0; i < mCategories.size(); i++) {
//                    loadExpenseByCategory(mCategories.get(i).getId());
//                }
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        });
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

    @Override
    public void loadCategoriesForDialog() {
        mCategoriesDataSource.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<Category> categories) {
                mView.initDialog(categories);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void loadExpenses() {
        mExpensesDataSource.getExpenses(this);

    }

    public void loadExpenseByCategory(Long id) {
        mExpensesDataSource.getExpenseByCategory(id, this);
    }

    @Override
    public void onExpensesLoaded(List<Expense> expenses) {
        for (Expense expense : expenses) {
            addToMap(getStringDate(expense.getDate()), expense.getPrice());
        }

        List<Filter> filters = new ArrayList<>();
        for (Map.Entry<String, Long> entry : filterdExpenses.entrySet()) {
            filters.add(new Filter(entry.getValue(), 0, entry.getKey()));
        }
        mView.setReportList(filters);
    }

    @Override
    public void onDataNotAvailable() {
        mExpense = null;
        categoriesCount--;
    }

    @Override
    public void onFiltersLoaded(List<Filter> filters) {
        mView.setReportList(filters);
    }

    private void addToMap(String date, long price) {
        int lastIndex = date.lastIndexOf("/");
        date = date.substring(0, lastIndex);
        if (filterdExpenses.containsKey(date)) {
            long p = filterdExpenses.get(date);
            p += price;
            filterdExpenses.put(date, p);
        } else {
            filterdExpenses.put(date, price);
        }
    }

    private String getStringDate(Long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        CalendarTool calendarTool = new CalendarTool(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        return calendarTool.getIranianDate();
    }
}
