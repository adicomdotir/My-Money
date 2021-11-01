package ir.adicom.app.mymoney.utils

import android.text.Editable
import android.text.TextWatcher

class SeparateThousands(val groupingSeparator: String, val decimalSeparator: String) : TextWatcher {

    private var busy = false

    override fun afterTextChanged(s: Editable?) {
        if (s != null && !busy) {
            busy = true

            var place = 0

            val decimalPointIndex = s.indexOf(decimalSeparator)
            var i = if (decimalPointIndex == -1) {
                s.length - 1
            } else {
                decimalPointIndex - 1
            }
            while (i >= 0) {
                val c = s[i]
                if (c == groupingSeparator[0] ) {
                    s.delete(i, i + 1)
                } else {
                    if (place % 3 == 0 && place != 0) {
                        s.insert(i + 1, groupingSeparator)
                    }
                    place++
                }
                i--
            }

            busy = false
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}