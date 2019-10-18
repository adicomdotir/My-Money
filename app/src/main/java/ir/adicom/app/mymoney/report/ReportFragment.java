package ir.adicom.app.mymoney.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.data.Filter;
import ir.adicom.app.mymoney.util.FilterDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements ReportContract.View, ReportContract.ReportDialogListener {

    private ReportContract.Presenter mPresenter;
    private ListView lvReport;
    private ReportAdapter mReportAdapter;
    private FloatingActionButton fab;

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

        mPresenter.loadCategoriesForDialog();

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_filter);
    }

    @Override
    public void setPresenter(ReportContract.Presenter presenter) {
        this.mPresenter = presenter;
        mPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReportAdapter = new ReportAdapter(new ArrayList<Filter>(0));
    }

    @Override
    public void setReportList(List<Filter> filters) {
        mReportAdapter.replaceData(filters);
        lvReport.setAdapter(mReportAdapter);
    }

    @Override
    public void initDialog(List<Category> categories) {
        final String[] array = new String[categories.size() + 1];
        array[0] = "همه";
        for (int i = 0; i < categories.size(); i++) {
            array[i + 1] = categories.get(i).getTitle();
        }

        final ReportContract.ReportDialogListener rdl = this;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterDialog filterDialog = new FilterDialog();
                filterDialog.setDialogListener(rdl);
                Bundle bundle = new Bundle();
                bundle.putStringArray("CATEGORY", array);
                filterDialog.setArguments(bundle);
                filterDialog.show(getActivity().getSupportFragmentManager(), "filter_dialog");
            }
        });
    }

    @Override
    public void dialogClose(int tag) {
        mPresenter.loadExpenses(tag);
    }
}
