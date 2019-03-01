package ir.adicom.app.mymoney.util.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.util.CalendarTool;

public class DatePickerDialog extends Dialog {
    private static final String TAG = "TAG";
    private View mainView;
    public String date;
    public DatePickerDialog(final Context context, CalendarTool calendarTool) {
        super(context);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mainView = LayoutInflater.from(context).inflate(R.layout.datepicker_dialog_layout,null);
        addContentView(mainView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final NumberSlider customBtn1 = (NumberSlider) findViewById(R.id.custom_btn_1);
        final NumberSlider customBtn2 = (NumberSlider) findViewById(R.id.custom_btn_2);
        final NumberSlider customBtn3 = (NumberSlider) findViewById(R.id.custom_btn_3);
        try {
            customBtn1.setNumber(calendarTool.getIranianYear());
            customBtn1.setMinMax(1370, 1499);
            customBtn1.setLen(4);
            customBtn1.setUp();
            customBtn2.setNumber(calendarTool.getIranianMonth());
            customBtn2.setMinMax(1, 12);
            customBtn2.setLen(2);
            customBtn2.setUp();
            customBtn3.setNumber(calendarTool.getIranianDay());
            customBtn3.setMinMax(1, 31);
            customBtn3.setLen(2);
            customBtn3.setUp();

            Button button = (Button) mainView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    date = customBtn1.getText() + "/"
                            + customBtn2.getText() + "/"
                            + customBtn3.getText();
                    dismiss();
                }
            });
//            button.setTypeface(Typeface.createFromAsset(context.getAssets(), App.FONT_NAME));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
