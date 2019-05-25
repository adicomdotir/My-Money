package ir.adicom.app.mymoney.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReportAdapter extends BaseAdapter {

        private List<String> mItems;

        public ReportAdapter(List<String> item) {
            setList(item);
        }

        public void replaceData(List<String> item) {
            setList(item);
            notifyDataSetChanged();
        }

        private void setList(List<String> item) {
            mItems = item;
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public String getItem(int i) {
            return mItems.get(i);
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
                rowView = inflater.inflate(R.layout.report_item, viewGroup, false);
            }

            final String[] strings = getItem(i).split(",");

            TextView tvTitle = (TextView) rowView.findViewById(R.id.tv_title);
            TextView tvPrice = (TextView) rowView.findViewById(R.id.tv_price);
            
			tvTitle.setText(strings[0]);
            String priceWithFormat = NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(strings[1]));
            tvPrice.setText(priceWithFormat + " تومان");

            return rowView;
        }
    }