package com.samansepahvand.calculateexpensesnew.infrastructure;

import android.content.Context;
import android.widget.EditText;

import com.google.gson.internal.$Gson$Types;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.ui.modal.AlertDialogModal;

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

    public static int GetPrice(String priceString) {

        try{
            String split=priceString.replace(",","");
            return Integer.parseInt(split);
        }catch (Exception e){
            return 0;
        }
    }



    public static   void DialogSuccess(String  info,Context context){
        final AlertDialogModal modal2 = new AlertDialogModal(context, true, false);
        modal2.setImageTypeCustom(Constants.TypeImageAlertDialog[3]);
        modal2.setButtonConfirmCustom("باشه !", Constants.TypeButtonStyleAlertDialog[4]);

        modal2.setTextContent(info);
        modal2.setAcceptButton(new AlertDialogModal.OnAcceptInterface() {
            @Override
            public void accept() {
                modal2.dismiss();
            }
        });
        modal2.show();

    }

    public  static   void DialogFailed(String  info,Context context){
        final AlertDialogModal modal2 = new AlertDialogModal(context, true, false);
        modal2.setImageTypeCustom(Constants.TypeImageAlertDialog[2]);
        modal2.setButtonConfirmCustom("باشه !", Constants.TypeButtonStyleAlertDialog[2]);

        modal2.setTextContent(info);
        modal2.setAcceptButton(new AlertDialogModal.OnAcceptInterface() {
            @Override
            public void accept() {
                modal2.dismiss();
            }
        });
        modal2.show();

    }


}
