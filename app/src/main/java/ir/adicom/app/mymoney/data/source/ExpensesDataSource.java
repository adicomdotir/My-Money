package ir.adicom.app.mymoney.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import ir.adicom.app.mymoney.data.DaoSession;
import ir.adicom.app.mymoney.data.Expense;
import ir.adicom.app.mymoney.data.Filter;

public interface ExpensesDataSource {
    void setSessionDao(DaoSession dao);

    void getExpenses(@NonNull LoadExpensesCallback callback);

    void getExpense(@NonNull Long id, @NonNull GetExpenseCallback callback);

    void getExpenseByCategory(Long categoryId, LoadExpensesCallback callback);
	
	void getExpensesGroupBy(@NonNull LoadExpensesCallback callback);
	
    void saveExpense(@NonNull Expense expense);

    void updateExpense(Expense expense);

    void deleteAllExpenses();

    void deleteExpense(@NonNull Long id);

    interface LoadExpensesCallback {

        void onExpensesLoaded(List<Expense> expenses);

        void onDataNotAvailable();

        void onFiltersLoaded(List<Filter> filters);
    }

    interface GetExpenseCallback {

        void onExpenseLoaded(Expense expense);

        void onDataNotAvailable();
    }
}
