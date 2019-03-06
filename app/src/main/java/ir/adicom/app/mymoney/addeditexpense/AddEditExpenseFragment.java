package ir.adicom.app.mymoney.addeditexpense;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.util.CalendarTool;
import ir.adicom.app.mymoney.util.CustomTextWatcher;
import ir.adicom.app.mymoney.util.datepicker.DatePickerDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditExpenseFragment extends Fragment implements AddEditExpenseContract.View {
    public static final String ARGUMENT_EDIT_EXPENSE_ID = "EDIT_EXPENSE_ID";
    private AddEditExpenseContract.Presenter mPresenter;
    private Spinner catSpinner;
    private EditText mTitle;
    private EditText mPrice;
    private Button mDateBtn;
    private List<Category> mCategories;
    private CalendarTool calendarTool;

    public AddEditExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_expense, container, false);
        catSpinner = (Spinner) root.findViewById(R.id.spinner_category);
        mTitle = (EditText) root.findViewById(R.id.add_expense_title);
        mPrice = (EditText) root.findViewById(R.id.et_expense_price);
        mPrice.addTextChangedListener(new CustomTextWatcher(mPrice));

        mDateBtn = (Button) root.findViewById(R.id.btn_date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendarTool = new CalendarTool(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        mDateBtn.setText(calendarTool.getIranianDate());

        mDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog customDatePickerDialog = new DatePickerDialog(getContext(), calendarTool);
                customDatePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        DatePickerDialog dialog = (DatePickerDialog) dialogInterface;
                        mDateBtn.setText(dialog.date);
                    }
                });
                customDatePickerDialog.show();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_category);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitle.getText().toString();
                NumberFormat nf = NumberFormat.getInstance(Locale.US);
                Long price = null;
                try {
                    price = nf.parse(mPrice.getText().toString()).longValue();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String date = mDateBtn.getText().toString();
                Long millis = dateToLong(date);

                // TODO: 1/22/19 Get categoryId from spinner => BAD SMELLS
                Category category = mCategories.get((int) catSpinner.getSelectedItemId());
                mPresenter.saveExpense(title, category.getId(), price, millis);
            }
        });
        FloatingActionButton fabDelete =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_delete_category);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setMessage("ایا شما میخواهید این دسته را حذف کنید؟")
                        .setCancelable(false)
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mPresenter.deleteExpense();
                            }
                        })
                        .setNegativeButton("خیر", null)
                        .show();
            }
        });
    }

    private Long dateToLong(String date) {
        CalendarTool ct = new CalendarTool();
        String[] temp = date.split("/");
        int year = Integer.parseInt(temp[0]);
        int month = Integer.parseInt(temp[1]);
        int day = Integer.parseInt(temp[2]);
        ct.setIranianDate(year, month, day);
        String myDate = ct.getGregorianDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Long millis = null;
        try {
            millis = sdf.parse(myDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millis;
    }

    @Override
    public void setPresenter(AddEditExpenseContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.start();
    }

    @Override
    public void showEmptyExpenseError() {
        Snackbar.make(mTitle, getString(R.string.empty_title_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showExpensesList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();

    }

    @Override
    public void setCategories(List<Category> categories) {
        // TODO: 1/7/19 change this bad smells
        List<String> list = new ArrayList<>();
        mCategories = categories;
        for (Category c: categories) {
            list.add(c.getTitle());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setPrice(Long price) {
        mPrice.setText("" + price);
    }

    @Override
    public void setSelectionCategory(int id) {
        catSpinner.setSelection(id);
    }

    @Override
    public void setDate(Long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendarTool = new CalendarTool(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        mDateBtn.setText(calendarTool.getIranianDate());
    }


}
