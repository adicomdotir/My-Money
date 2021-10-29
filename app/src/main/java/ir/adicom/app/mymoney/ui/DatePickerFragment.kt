package ir.adicom.app.mymoney.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import ir.adicom.app.mymoney.R
import ir.adicom.app.mymoney.utils.PersianDate
import kotlinx.android.synthetic.main.fragment_date_picker.*
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class DatePickerFragment(val ctx: Context) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_date_picker, container)
        requireDialog().setTitle("Date Picker")
        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val year = Calendar.getInstance().get(Calendar.YEAR);
        val month = Calendar.getInstance().get(Calendar.MONTH);
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        val millis = Calendar.getInstance().timeInMillis
//        Log.e("TAG", PersianDate(millis).)

        sb_year.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tv_year.text = "Year: ${p1 + 1350}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        sb_month.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tv_month.text = "Month: ${p1 + 1}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        sb_day.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tv_day.text = "Day: ${p1 + 1}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }
}