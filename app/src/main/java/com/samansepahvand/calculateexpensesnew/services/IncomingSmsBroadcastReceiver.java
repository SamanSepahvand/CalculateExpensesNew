package com.samansepahvand.calculateexpensesnew.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.repository.InfoRepository;
import com.samansepahvand.calculateexpensesnew.infrastructure.BankStringFormat;

public class IncomingSmsBroadcastReceiver  extends BroadcastReceiver {


    private final String TAG = "Incoming";

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {
                StringBuilder builder = new StringBuilder();
                String phoneNumber = null;

                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();
                    builder.append("" + message);
                }
                builder.append("\n Sender:" + phoneNumber);


                OperationResult result = InfoRepository.getInstance().AutoAddPrice(BankStringFormat.SetBankType(builder.toString()));


                Log.e(TAG, "onReceive Result: "+result );
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }


    }

}
