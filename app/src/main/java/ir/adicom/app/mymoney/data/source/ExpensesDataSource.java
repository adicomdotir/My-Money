package ir.adicom.app.mymoney.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import ir.adicom.app.mymoney.data.Expense;

public interface ExpensesDataSource {
    void getExpenses(@NonNull LoadExpensesCallback callback);

    void getExpense(@NonNull Long id, @NonNull GetExpenseCallback callback);

    void getExpenseByCategory(Long categoryId, LoadExpensesCallback callback);

    void saveExpense(@NonNull Expense expense);

    void updateExpense(Expense expense);

    void deleteAllExpenses();

    void deleteExpense(@NonNull Long id);

    interface LoadExpensesCallback {

        void onExpensesLoaded(List<Expense> expenses);

        void onDataNotAvailable();
    }

    interface GetExpenseCallback {

        void onExpenseLoaded(Expense expense);

        void onDataNotAvailable();
    }
}
