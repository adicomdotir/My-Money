package ir.adicom.app.mymoney.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import ir.adicom.app.mymoney.R
import ir.adicom.app.mymoney.ui.expense.NewExpenseFragment
import kotlinx.android.synthetic.main.fragment_date_picker.*
import saman.zamani.persiandate.PersianDate
import java.util.*

class DatePickerFragment(val ctx: Context, val dateDialogListener: DateDialogListener) : DialogFragment() {

    interface DateDialogListener {
        fun onFinishDateDialog(millisecond: Long)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_date_picker, container)
        requireDialog().setTitle("انتخاب تاریخ")
        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val millis = Calendar.getInstance().timeInMillis
        val persianDate = PersianDate(millis)
        val year = persianDate.shYear;
        val month = persianDate.shMonth
        val day = persianDate.shDay

        sb_year.progress = year - 1350
        tv_year.text = "سال: ${year}"
        sb_month.progress = month - 1
        tv_month.text = "ماه: ${month}"
        sb_day.progress = day - 1
        tv_day.text = "روز: ${day}"

        btn_confirm_date.setOnClickListener {
            val dateMillisecond = convertToLong(sb_year.progress + 1350, sb_month.progress + 1, sb_day.progress + 1)
            dateDialogListener.onFinishDateDialog(dateMillisecond)
            dismiss()
        }

        sb_year.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tv_year.text = "سال: ${p1 + 1350}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        sb_month.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tv_month.text = "ماه: ${p1 + 1}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        sb_day.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tv_day.text = "روز: ${p1 + 1}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    private fun convertToLong(year: Int, month: Int, day: Int): Long {
        return PersianDate().initJalaliDate(year, month, day).toDate().time
    }
}