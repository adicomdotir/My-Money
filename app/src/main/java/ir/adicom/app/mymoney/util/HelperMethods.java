package ir.adicom.app.mymoney.util;

/**
 * Created by Y.P on 10/23/2019.
 */

public class HelperMethods {
    public static String convertToMonth(String value) {
        if (value.equals("1")) {
            return "فروردین";
        } else if (value.equals("2")) {
            return "اردیبهشت";
        } else if (value.equals("3")) {
            return "خرداد";
        } else if (value.equals("4")) {
            return "تیر";
        } else if (value.equals("5")) {
            return "مرداد";
        } else if (value.equals("6")) {
            return "شهریور";
        } else if (value.equals("7")) {
            return "مهر";
        } else if (value.equals("8")) {
            return "ابان";
        } else if (value.equals("9")) {
            return "اذر";
        } else if (value.equals("10")) {
            return "دی";
        } else if (value.equals("11")) {
            return "بهمن";
        } else if (value.equals("12")) {
            return "اسفند";
        }
        return value;
    }
}
