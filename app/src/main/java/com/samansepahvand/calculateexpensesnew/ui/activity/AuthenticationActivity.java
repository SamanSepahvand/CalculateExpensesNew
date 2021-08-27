package com.samansepahvand.calculateexpensesnew.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.metamodel.UserLoginParam;
import com.samansepahvand.calculateexpensesnew.business.repository.AccountRepository;
import com.samansepahvand.calculateexpensesnew.db.Account;
import com.samansepahvand.calculateexpensesnew.ui.modal.AlertDialogModal;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.Manifest.permission.READ_PHONE_STATE;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String TAG = "LoginActivity";
    public static final int PERMISSION_REQUEST_CODE = 200;
    private EditText edtPassword, edtUserName;
    private Button btnLogin;
    private TextInputLayout filledTextField_password, filledTextField_username;
    private TelephonyManager telephonyManager;
    private String IMEI = "";
    private String simSerialNumber = "";
    private ConstraintLayout root;
    private TextView txtForgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        initView();

    }


    private void initView() {

        root = findViewById(R.id.root);
        btnLogin = findViewById(R.id.btn_login);
        edtUserName = findViewById(R.id.edt_first_name);
        edtPassword = findViewById(R.id.edt_passowrd);

        edtUserName.setTypeface(Constants.CustomStyleElement());
        edtPassword.setTypeface(Constants.CustomStyleElement());

        txtForgetPassword = findViewById(R.id.txt_forget_password);


        filledTextField_password = findViewById(R.id.filledTextField_password);
        filledTextField_username = findViewById(R.id.filledTextField_username);
        filledTextField_password.setTypeface(Constants.CustomStyleElement());
        filledTextField_username.setTypeface(Constants.CustomStyleElement());

        btnLogin.setOnClickListener(this);
        txtForgetPassword.setOnClickListener(this);

        AutoAuthentication();

    }

    private void AutoAuthentication() {

        OperationResult<Account> result = AccountRepository.getInstance().AutoAuthentication();

        if (result.Item != null && !result.Item.getUserName().isEmpty()) {
            edtUserName.setText(result.Item.getUserName());
        }else {
            startActivity(new Intent(AuthenticationActivity.this,SignUpActivity.class));
        }
    }


    public void Login() {


        UserLoginParam obj = new UserLoginParam(edtUserName.getText().toString()
                , edtPassword.getText().toString());

        if (!ValidationEmptyLogin(obj.getUserName(), obj.getPassword())) {
            return;
        }

        OperationResult<Account> result = AccountRepository.getInstance().LoginAuth(obj);
        if (result.IsSuccess) {
            startActivity(new Intent(AuthenticationActivity.this, MainActivity.class));
            finish();
        } else {
            errorEmptyInput(result.Message,true);
        }


    }

    private boolean ValidationEmptyLogin(String userName, String password) {
        if (userName.equals(null) || password.equals(null)) {
            //Utility.ShowToast(this, getString(R.string.UserNamePasswordRequired), Toast.LENGTH_LONG);

            AlertDialogModal modal2 = new AlertDialogModal(this, true, false);
            modal2.setImageTypeCustom(Constants.TypeImageAlertDialog[2]);
            modal2.setButtonConfirmCustom("تلاش مجدد !", Constants.TypeButtonStyleAlertDialog[2]);
            modal2.setTextContent(getString(R.string.UserNamePasswordRequired));
            modal2.setAcceptButton(new AlertDialogModal.OnAcceptInterface() {
                @Override
                public void accept() {

                    edtUserName.setHintTextColor(Color.RED);
                    edtPassword.setHintTextColor(Color.RED);
                }
            });
            modal2.show();

            return false;
        }
        return true;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void tempDisableButton(View viewBtn) {
        final View button = viewBtn;
        button.setEnabled(false);
        button.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setEnabled(true);
            }
        }, 2000);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_login:

                String tempUserName, tempPassword;
                tempUserName = edtUserName.getText().toString();
                tempPassword = edtPassword.getText().toString();

                //    startActivity(new Intent(LoginActivity.this, MainActivity.class));


                if (isEmpty(tempUserName, tempPassword)) {
                    errorEmptyInput(getString(R.string.UserNamePasswordRequired),false);
                } else {
                    tempDisableButton(view);
                    Login();

                }
                break;

            case R.id.txt_forget_password:

//                edtUserName.setText("m.zangane");
//                edtPassword.setText("Mojtaba123");

                break;


        }
    }

    private void errorEmptyInput(String message,boolean isWrong) {
        final AlertDialogModal modal2 = new AlertDialogModal(this, true, false);
        modal2.setImageTypeCustom(isWrong ? Constants.TypeImageAlertDialog[2]:Constants.TypeImageAlertDialog[1]);
        modal2.setButtonConfirmCustom("باشه !", isWrong ? Constants.TypeButtonStyleAlertDialog[2] : Constants.TypeButtonStyleAlertDialog[1]);
        modal2.setTextContent(message);
        modal2.setAcceptButton(new AlertDialogModal.OnAcceptInterface() {
            @Override
            public void accept() {

                modal2.dismiss();

            }
        });
        modal2.show();
    }

    private boolean isEmpty(String tempUserName, String tempPassword) {


        if (tempUserName.equals("") || tempPassword.equals("")) {
            return true;

        } else {

            return false;
        }
    }


    ///handle  permission methods
    private boolean checkPermission() {

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);

        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                    if (locationAccepted)
                        showMessage("کل دسترسی ها با موفقیت تایید شد .", "s");
                    else {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_PHONE_STATE)) {
                                showMessageOKCancel("دسترسی تایید نشد! شما اجازه استفاده از سایر قسمت های سیستم را نداری  ",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_PHONE_STATE},
                                                            PERMISSION_REQUEST_CODE);
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
        if (type.equals("s")) {
            Snackbar snackbar = Snackbar.make(root, body, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (type.equals("t")) {
            Toast.makeText(this, "" + body, Toast.LENGTH_SHORT).show();

        }
    }



}



