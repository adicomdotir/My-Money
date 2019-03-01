package ir.adicom.app.mymoney.util.datepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.adicom.app.mymoney.R;

/**
 * Created by adicom on 12/5/16.
 */

public class NumberSlider extends LinearLayout {

    private ImageView btnDown, btnUp;
    private TextView tvText;
    private int number = 1;
    private int max = 0, min = 0;
    private int len = 4;

    public void setLen(int len) {
        this.len = len;
        invalidate();
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }


    public NumberSlider(Context context) {
        super(context);
        init(context);
    }

    public NumberSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumberSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflate(context, R.layout.custom_layout, null);
        View view = inflater.inflate(R.layout.number_slider_layout, this, true);

        tvText = (TextView) view.findViewById(R.id.tv_custom);
//        tvText.setTypeface(Typeface.createFromAsset(context.getAssets(), App.FONT_NAME));
        btnDown = (ImageView) view.findViewById(R.id.btn_custom_down);
        btnUp = (ImageView) view.findViewById(R.id.btn_custom_up);
        btnUp.setColorFilter(getResources().getColor(R.color.colorPrimary));
        btnDown.setColorFilter(getResources().getColor(R.color.colorPrimary));
//        addView(view);
    }

    public void setUp() {
        tvText.setText(getFormat());

        btnUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number<max)
                    number++;
                else
                    number = min;
                tvText.setText(getFormat());
            }
        });

        btnDown.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number>min)
                    number--;
                else
                    number = max;
                tvText.setText(getFormat());
            }
        });
    }

    private String getFormat() {
        String format = "%0" + len + "d";
        String str = String.format(format, number);
        char[] arabicChars = {'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<str.length();i++) {
            if(Character.isDigit(str.charAt(i))) {
                builder.append(arabicChars[(int)(str.charAt(i))-48]);
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    public String getText() {
        return tvText.getText().toString();
    }
}