package ir.adicom.app.mymoney.expenses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.addeditexpense.AddEditExpenseActivity;
import ir.adicom.app.mymoney.addeditexpense.AddEditExpenseFragment;
import ir.adicom.app.mymoney.data.Expense;
import ir.adicom.app.mymoney.util.CalendarTool;
import ir.adicom.app.mymoney.util.HelperMethods;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesFragment extends Fragment implements ExpensesContract.View {

    private ExpensesContract.Presenter mExpensesPresenter;
    ExpenseItemListener mItemListener = new ExpenseItemListener() {
        @Override
        public void onExpenseClick(Expense clickedExpense) {
            mExpensesPresenter.openExpenseDetail(clickedExpense);
        }
    };
    private ListView lvExpenses;
    private ExpensesAdapter mExpensesAdapter;
    private Spinner spCategory, spDate;
    private List<String> mCategoryList = null;
    private List<String> mDateList = null;
    private List<Expense> mExpenses = null;
    private String categoryTitle = "همه", dateTitle = "همه";

    public ExpensesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expenses, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvExpenses = (ListView) view.findViewById(R.id.expenses_list);

        spCategory = (Spinner) view.findViewById(R.id.sp_category);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryTitle = mCategoryList.get(i);
                setFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spDate = (Spinner) view.findViewById(R.id.sp_date);
        spDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dateTitle = mDateList.get(i);
                setFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_expense);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExpensesPresenter.addNewExpense();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExpensesAdapter = new ExpensesAdapter(new ArrayList<Expense>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mExpensesPresenter.start();
    }

    @Override
    public void setPresenter(ExpensesContract.Presenter presenter) {
        this.mExpensesPresenter = presenter;
    }

    @Override
    public void showProgressBar() {
        // progress bar
    }

    @Override
    public void showExpenses(List<Expense> expenses) {
        mExpensesAdapter.replaceData(expenses);
        lvExpenses.setAdapter(mExpensesAdapter);
        mExpenses = expenses;
        setSpinner();
    }

    @Override
    public void showAddExpense() {
        Intent intent = new Intent(getContext(), AddEditExpenseActivity.class);
        startActivityForResult(intent, AddEditExpenseActivity.REQUEST_ADD_EXPENSE);
    }

    @Override
    public void showExpenseDetailsUi(Long expenseId) {
        Intent intent = new Intent(getContext(), AddEditExpenseActivity.class);
        intent.putExtra(AddEditExpenseFragment.ARGUMENT_EDIT_EXPENSE_ID, expenseId);
        startActivity(intent);
    }

    private void setSpinner() {
        HashSet<String> stringTreeSet = new HashSet<>();
        HashSet<String> dateTreeSet = new HashSet<>();
        stringTreeSet.add("همه");
        dateTreeSet.add("همه");
        for (Expense ex : mExpenses) {
            stringTreeSet.add(ex.getCategory().getTitle());
            String d = getDateString(ex.getDate());
            dateTreeSet.add(d);
        }
        mCategoryList = new ArrayList<>(stringTreeSet);
        mDateList = new ArrayList<>(dateTreeSet);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mCategoryList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(dataAdapter);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mDateList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDate.setAdapter(arrayAdapter);
    }

    private void setFilter() {
        List<Expense> expenses = new ArrayList<>();
        if (categoryTitle.equals("همه") && dateTitle.equals("همه")) {
            expenses = mExpenses;
        } else {
            for (Expense ex : mExpenses) {
                boolean isCategory = true;
                boolean isDate = true;
                if (!categoryTitle.equals("همه")) {
                    if (!ex.getCategory().getTitle().equals(categoryTitle)) {
                        isCategory = false;
                    }
                }
                if (!dateTitle.equals("همه")) {
                    if (!getDateString(ex.getDate()).equals(dateTitle)) {
                        isDate = false;
                    }
                }
                if (isCategory && isDate) {
                    expenses.add(ex);
                }
            }
        }
        mExpensesAdapter.replaceData(expenses);
        lvExpenses.setAdapter(mExpensesAdapter);
    }

    private String getDateString(Long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        CalendarTool calendarTool = new CalendarTool(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        int index = calendarTool.getIranianDate().lastIndexOf("/");
        String[] arr = calendarTool.getIranianDate().substring(0, index).split("/");
        return arr[0] + " " + HelperMethods.convertToMonth(arr[1]);
    }

    public interface ExpenseItemListener {
        void onExpenseClick(Expense clickedExpense);
    }

    private static class ExpensesAdapter extends BaseAdapter {

        private List<Expense> mExpenses;
        private ExpenseItemListener mItemListener;

        public ExpensesAdapter(List<Expense> expenses, ExpenseItemListener itemListener) {
            setList(expenses);
            mItemListener = itemListener;
        }

        public void replaceData(List<Expense> expenses) {
            setList(expenses);
            notifyDataSetChanged();
        }

        private void setList(List<Expense> expenses) {
            mExpenses = expenses;
        }

        @Override
        public int getCount() {
            return mExpenses.size();
        }

        @Override
        public Expense getItem(int i) {
            return mExpenses.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.expense_item, viewGroup, false);
            }

            final Expense expense = getItem(i);
            TextView tvTitle = (TextView) rowView.findViewById(R.id.tv_title);
            TextView tvPrice = (TextView) rowView.findViewById(R.id.tv_price);
            TextView tvDate = (TextView) rowView.findViewById(R.id.tv_date);
            TextView tvCategory = (TextView) rowView.findViewById(R.id.tv_category);
            tvTitle.setText("عنوان : " + expense.getTitle());
            String priceWithFormat = NumberFormat.getNumberInstance(Locale.US).format(expense.getPrice());
            tvPrice.setText("هزینه :‌ " + priceWithFormat + " تومان");
            tvCategory.setText("دسته بندی : " + expense.getCategory().getTitle());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(expense.getDate());
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            CalendarTool calendarTool = new CalendarTool(mYear, mMonth + 1, mDay);
            tvDate.setText("تاریخ :‌ " + calendarTool.getIranianDate() + "");


            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.onExpenseClick(expense);
                }
            });

            return rowView;
        }
    }
}
