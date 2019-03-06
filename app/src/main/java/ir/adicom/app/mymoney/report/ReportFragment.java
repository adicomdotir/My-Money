package ir.adicom.app.mymoney.report;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import ir.adicom.app.mymoney.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements ReportContract.View {

    private ReportContract.Presenter mPresenter;

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

        PieChart chart = (PieChart) view.findViewById(R.id.chart1);
        chart.getDescription().setEnabled(false);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(5, "غذا"));
        entries.add(new PieEntry(10, "قلیان"));
        entries.add(new PieEntry(15, "سوخت"));

        PieDataSet dataSet = new PieDataSet(entries, "دسته ها");
        dataSet.setValueFormatter(new PercentFormatter());

        int[] colors = new int[]{
                Color.parseColor("#488f31"),
                Color.parseColor("#3d9c73"),
                Color.parseColor("#63b179"),
                Color.parseColor("#88c580"),
                Color.parseColor("#aed987"),
                Color.parseColor("#d6ec91"),
                Color.parseColor("#ffff9d"),
                Color.parseColor("#fee17e"),
                Color.parseColor("#fcc267"),
                Color.parseColor("#f7a258"),
                Color.parseColor("#ef8250"),
                Color.parseColor("#e4604e"),
                Color.parseColor("#de425b")
        };
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);

        PieData barData = new PieData(dataSet);
        chart.setData(barData);
        chart.invalidate();
    }
    @Override
    public void setPresenter(ReportContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}