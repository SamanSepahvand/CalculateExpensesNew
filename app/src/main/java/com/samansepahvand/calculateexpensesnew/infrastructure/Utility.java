package com.samansepahvand.calculateexpensesnew.infrastructure;

import android.content.Context;
import android.widget.EditText;

import com.samansepahvand.calculateexpensesnew.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class Utility {


    public static String splitDigits(int number) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat();
            DecimalFormatSymbols decimalFormatSymbol = new DecimalFormatSymbols();
            decimalFormatSymbol.setGroupingSeparator(',');
            decimalFormat.setDecimalFormatSymbols(decimalFormatSymbol);
            return decimalFormat.format(number);
        } catch (Exception ex) {
            return String.valueOf(number);
        }
    }




    public static int  ChangeIconAddExpenses(int count){
        if (count==1) return R.drawable.ic_baseline_check_24;
        return R.drawable.ic_baseline_arrow_back_ios_24;

    }

}
