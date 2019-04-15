package ir.adicom.app.mymoney.chart;


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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ir.adicom.app.mymoney.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment implements ChartContract.View {

    private ChartContract.Presenter mPresenter;
    private PieChart chart;

    public ChartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chart = (PieChart) view.findViewById(R.id.chart1);
        chart.getDescription().setEnabled(false);
    }

    @Override
    public void setPresenter(ChartContract.Presenter presenter) {
        this.mPresenter = presenter;
        mPresenter.start();
    }

    @Override
    public void initializeChart(Map<Long, Long> result) {
        List<PieEntry> entries = new ArrayList<>();
        Long sumPrice = 0L;
        for (Map.Entry<Long, Long> entry : result.entrySet()) {
            sumPrice += entry.getValue();
            //entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
        DecimalFormat df = new DecimalFormat("#.#");
        List<Integer> colors = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : result.entrySet()) {
            float percent = entry.getValue() * 100 / sumPrice;
            percent = Float.parseFloat(df.format(percent));
            entries.add(new PieEntry(percent, mPresenter.getCategory(entry.getKey())));
            colors.add(generateDarkColor());
        }
//        entries.add(new PieEntry(5, "غذا"));
//        entries.add(new PieEntry(10, "قلیان"));
//        entries.add(new PieEntry(15, "سوخت"));

        PieDataSet dataSet = new PieDataSet(entries, "دسته ها");
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);

        PieData barData = new PieData(dataSet);
        chart.setData(barData);
        chart.invalidate();
    }

    private Integer generateDarkColor() {
        Random r = new Random();
        return Color.rgb(r.nextInt(100), r.nextInt(100), r.nextInt(100));
    }
}
