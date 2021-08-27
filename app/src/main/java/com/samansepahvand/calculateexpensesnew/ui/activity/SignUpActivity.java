package com.samansepahvand.calculateexpensesnew.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.samansepahvand.calculateexpensesnew.MainApplication;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.repository.AccountRepository;
import com.samansepahvand.calculateexpensesnew.db.Account;
import com.samansepahvand.calculateexpensesnew.ui.modal.AlertDialogModal;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtFirstName, edtLastName, edtUserName, edtPassword, edtForgetKey;
    private Button btnSignUp;


    private TextInputLayout tilFirstName,tilLastName,tilUserName,tilPassword,tilForgetKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        intiView();

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