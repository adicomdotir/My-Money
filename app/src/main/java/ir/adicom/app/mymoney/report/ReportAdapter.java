package ir.adicom.app.mymoney.report;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.data.Filter;

public class ReportAdapter extends BaseAdapter {

        private List<Filter> mItems;

        public ReportAdapter(List<Filter> item) {
            setList(item);
        }

        public void replaceData(List<Filter> item) {
            setList(item);
            notifyDataSetChanged();
        }

        private void setList(List<Filter> item) {
            mItems = item;
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Filter getItem(int i) {
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

            Filter filter = getItem(i);

            TextView tvTitle = (TextView) rowView.findViewById(R.id.tv_title);
            TextView tvPrice = (TextView) rowView.findViewById(R.id.tv_price);
            TextView tvCount = (TextView) rowView.findViewById(R.id.tv_count);

			tvTitle.setText("" + filter.getCategoryId());
            String priceWithFormat = NumberFormat.getNumberInstance(Locale.US).format(filter.getSum());
            tvPrice.setText(priceWithFormat + " تومان");
            tvCount.setText("تعداد : " + filter.getCount());

            return rowView;
        }
    }