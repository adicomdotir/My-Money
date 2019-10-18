package ir.adicom.app.mymoney.util;

/**
 *
 * Created by Y.P on 5/8/2019.
 */

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.report.ReportContract;

public class FilterDialog extends DialogFragment {

    private String[] catArray;
    private ReportContract.ReportDialogListener dialogListener;

    public void setDialogListener(ReportContract.ReportDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.catArray = getArguments().getStringArray("CATEGORY");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.filter_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);

        builder.setTitle("فیلتر");
        builder.setView(view);

        Spinner catSpinner = (Spinner) view.findViewById(R.id.spinner_category);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg_dialog);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, catArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(dataAdapter);

        builder.setNegativeButton("لغو", null);
        builder.setPositiveButton("اعمال", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
                int tag = Integer.parseInt((String) radioButton.getTag());
                dialogListener.dialogClose(tag);
            }
        });

        return builder.create();

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}


