package ir.adicom.app.mymoney.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.data.Expense;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements ReportContract.View {

    private ReportContract.Presenter mPresenter;
    private ListView lvReport;
    private ReportAdapter mReportAdapter;

    public ReportFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvReport = (ListView) view.findViewById(R.id.report_list);
    }

    @Override
    public void setPresenter(ReportContract.Presenter presenter) {
        this.mPresenter = presenter;
        mPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReportAdapter = new ReportAdapter(new ArrayList<Expense>(0));
    }

    @Override
    public void setReportList() {
        mReportAdapter.replaceData(expenses);
        lvReport.setAdapter(mReportAdapter);
    }

    private static class ReportAdapter extends BaseAdapter {

        private List<Expense> mExpenses;

        public ReportAdapter(List<Expense> expenses) {
            setList(expenses);
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
                rowView = inflater.inflate(R.layout.category_item, viewGroup, false);
            }

            final Expense expense = getItem(i);
            TextView tvItem = (TextView) rowView.findViewById(R.id.tv_item);
            tvItem.setText(expense.getTitle());

            return rowView;
        }
    }
}
