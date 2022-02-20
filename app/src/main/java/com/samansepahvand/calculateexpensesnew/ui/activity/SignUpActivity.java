package com.samansepahvand.calculateexpensesnew.ui.activity;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.samansepahvand.calculateexpensesnew.MainApplication;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.repository.AccountRepository;
import com.samansepahvand.calculateexpensesnew.business.repository.InfoRepository;
import com.samansepahvand.calculateexpensesnew.db.Account;
import com.samansepahvand.calculateexpensesnew.services.IncomingSmsBroadcastReceiver;
import com.samansepahvand.calculateexpensesnew.ui.modal.AlertDialogModal;

import org.apache.http.cookie.SM;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtFirstName, edtLastName, edtUserName, edtPassword, edtForgetKey;
    private Button btnSignUp;


    private TextInputLayout tilFirstName,tilLastName,tilUserName,tilPassword,tilForgetKey;


    private IncomingSmsBroadcastReceiver smsBroadcastReceiver=new IncomingSmsBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        intiView();

    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter=new IntentFilter(Intent.ACTION_SEND);
        registerReceiver(smsBroadcastReceiver,filter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_SMS);
        int result22 = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);
        int result224 = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);





        return result == PackageManager.PERMISSION_GRANTED
                && result1 == PackageManager.PERMISSION_GRANTED
                && result22 == PackageManager.PERMISSION_GRANTED
                && result224 == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_PHONE_STATE,



                READ_SMS,RECEIVE_SMS,SEND_SMS


        }, 200);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean camera2Accepted1 = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean camera2Accepted2 = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean camera2Accepted3 = grantResults[4] == PackageManager.PERMISSION_GRANTED;





                    if (locationAccepted && cameraAccepted

                    &&camera2Accepted1
                            &&camera2Accepted2
                            &&camera2Accepted3) {
                        showMessage("کل دسترسی ها با موفقیت تایید شد . صفحه مجدد بروز خواهد شد", "s");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //recreate();
                            }
                        }, 1000);

                    } else {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) &&
                                    shouldShowRequestPermissionRationale(READ_PHONE_STATE) &&
                                    shouldShowRequestPermissionRationale(CAMERA)&&
                            shouldShowRequestPermissionRationale(READ_SMS)&&
                            shouldShowRequestPermissionRationale(RECEIVE_SMS)&&
                            shouldShowRequestPermissionRationale(SEND_SMS)

                            ) {
                                showMessageOKCancel("دسترسی تایید نشد! شما اجازه استفاده از سایر قسمت های سیستم را نداری  ",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{
                                                            WRITE_EXTERNAL_STORAGE, READ_PHONE_STATE, READ_SMS,SEND_SMS,RECEIVE_SMS



                                                            },
                                                            200);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/iran_sans.ttf");
        TextView content = new TextView(this);
        content.setText("برای استفاده از سایر امکانات  برنامه به دسترسی ها نیاز داریم!");
        content.setTypeface(face);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.alert));
        alertDialogBuilder.setTitle("درخواست دسترسی");
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
        alertDialogBuilder.setView(content);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                requestPermission();
            }
        });
        alertDialogBuilder.setNeutralButton("انصراف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void showMessage(String body, String type) {
            Toast.makeText(this, "" + body, Toast.LENGTH_SHORT).show();

    }



    private void intiView() {
        tilFirstName=findViewById(R.id.filledTextField_firstname);
        tilLastName=findViewById(R.id.filledTextField_lastname);
        tilUserName=findViewById(R.id.filledTextField_username);
        tilPassword=findViewById(R.id.filledTextField_password);
        tilForgetKey=findViewById(R.id.filledTextField_forget_key);



        tilFirstName.setTypeface(Constants.CustomStyleElement());
        tilLastName.setTypeface(Constants.CustomStyleElement());
        tilUserName.setTypeface(Constants.CustomStyleElement());
        tilPassword.setTypeface(Constants.CustomStyleElement());
        tilForgetKey.setTypeface(Constants.CustomStyleElement());

        edtFirstName = findViewById(R.id.edt_first_name);
        edtForgetKey = findViewById(R.id.edt_forget_key);
        edtPassword = findViewById(R.id.edt_password);
        edtUserName = findViewById(R.id.edt_user_name);
        edtLastName = findViewById(R.id.edt_last_name);

        btnSignUp = findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(this);


        if (!checkPermission()) {
            requestPermission();
        }


      //  OperationResult result= InfoRepository.getInstance().DeleteItemInfo(29);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void SignUp() {

        String tempUserName = edtUserName.getText().toString();
        String tempPassword = edtPassword.getText().toString();
        String tempFirstName = edtFirstName.getText().toString();
        String tempLastName = edtLastName.getText().toString();
        String tempForgetKey = edtForgetKey.getText().toString();

        if (!tempFirstName.equals("")) {
            if (!tempLastName.equals("")) {
                if (!tempUserName.equals("")) {
                    if (!tempPassword.equals("")) {

                        Account account = new Account();
                        account.setFirstName(tempFirstName);
                        account.setKeyForget(tempForgetKey);
                        account.setPassword(tempPassword);
                        account.setLastName(tempLastName);
                        account.setUserName(tempUserName);
                        OperationResult<Account> result = AccountRepository.getInstance().SignUp(account);
                        if (result.IsSuccess) {

                            Toast.makeText(this, "ثبت شما با موفقیت انجام شد.", Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    startActivity(new Intent(SignUpActivity.this, AuthenticationActivity.class));
                                    finish();
                                }
                            }, 500);
                        } else {
                            errorSignUp(result.Message);
                        }
                    } else {
                        Toast.makeText(this, "خطا : " + " رمز عبور  را وارد کنید !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "خطا : " + " نام کاربری را وارد کنید !", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "خطا : " + " نام خانوادگی را وارد کنید !", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "خطا : " + " نام خود را وارد کنید !", Toast.LENGTH_SHORT).show();
        }
    }


    private void errorSignUp(String result) {
        final AlertDialogModal modal2 = new AlertDialogModal(this, true, false);
        modal2.setImageTypeCustom(Constants.TypeImageAlertDialog[1]);
        modal2.setButtonConfirmCustom("باشه !", Constants.TypeButtonStyleAlertDialog[1]);
        modal2.setTextContent(result);
        modal2.setAcceptButton(new AlertDialogModal.OnAcceptInterface() {
            @Override
            public void accept() {
                modal2.dismiss();
            }
        });
        modal2.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_up:
                SignUp();
                break;
        }
    }
}


