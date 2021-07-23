package com.samansepahvand.calculateexpensesnew.ui.modal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.samansepahvand.calculateexpensesnew.MainApplication;
import com.samansepahvand.calculateexpensesnew.R;
import com.samansepahvand.calculateexpensesnew.business.domain.Constants;


public class AlertDialogModal extends Dialog implements View.OnClickListener {


    private Context mContext;


    private TextView txtBody;

    private Button btnConfirm, btnCancel;

    private ImageView imgCloseDialog, imgTypeDialog;


    private OnAcceptInterface acceptListener;
    private OnCancelInterface cancelListener;


    public AlertDialogModal(@NonNull Context context,boolean hasConfirm ,boolean hasCancel) {
        super(context);
        this.mContext = context;
        setContentView(R.layout.dialog_information1);
        this.getWindow().getAttributes().windowAnimations = R.style.AlertDialogAnimation;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView(hasConfirm,hasCancel);
        // initValue(alertDialogModel);


    }


    private void initView(final boolean hasConfirm , final boolean hasCancel) {


        txtBody = findViewById(R.id.txt_dialog_edit);
        btnConfirm = findViewById(R.id.btn_dialog_confirm);
        btnCancel = findViewById(R.id.btn_dialog_cancel);
        imgCloseDialog = findViewById(R.id.img_dialog_close);
        imgTypeDialog = findViewById(R.id.img_type_dialog);

        txtBody.setTextColor(getContext().getResources().getColor(R.color.black));
        btnConfirm.setVisibility(View.GONE);
        txtBody.setVisibility(View.GONE);
        imgTypeDialog.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtBody.startAnimation(MainApplication.SetAnimation("FadeIn"));
                imgTypeDialog.startAnimation(MainApplication.SetAnimation("FadeIn"));
                btnConfirm.startAnimation(MainApplication.SetAnimation("FadeIn"));
                // btnCancel.startAnimation(MainApplication.SetAnimation("FadeIn"));


                if (hasCancel) btnCancel.setVisibility(View.VISIBLE);
                if (hasConfirm) btnConfirm.setVisibility(View.VISIBLE);

                txtBody.setVisibility(View.VISIBLE);
                imgTypeDialog.setVisibility(View.VISIBLE);


            }
        }, Constants.DelayMoreFast);

        imgCloseDialog.startAnimation(MainApplication.SetAnimation("Rotate"));

        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imgCloseDialog.setOnClickListener(this);
        imgTypeDialog.setOnClickListener(this);


    }

//    private void initValue(AlertDialogModel dialog) {
//
//        if (dialog.getActionConfirm().equals("")) {
//            btnConfirm.setVisibility(View.GONE);
//        } else {
//            btnConfirm.setText(dialog.getActionConfirm());
//        }
//
//        if (dialog.getActionCancel().equals("")) {
//            btnCancel.setVisibility(View.GONE);
//        } else {
//            btnCancel.setText(dialog.getActionCancel());
//        }
//
//
//        txtBody.setText(dialog.getContent());
//
//    }


    public Dialog setAcceptButton(OnAcceptInterface listener) {
        this.acceptListener = listener;
        // btnConfirm.setVisibility(View.VISIBLE);
        return this;
    }


    public Dialog setCancelButton(OnCancelInterface cancelListener) {
        this.cancelListener = cancelListener;
        //btnCancel.setVisibility(View.VISIBLE);
        return this;
    }


    public Dialog setButtonConfirmCustom(String title, int alertButtonStyle) {
        //  this.btnConfirm.setVisibility(View.VISIBLE);
        this.btnConfirm.setText(title);
        this.btnConfirm.setBackgroundResource(alertButtonStyle);
        return this;
    }


    public Dialog setTextCancelButtonCustom(String title) {
        //  this.btnCancel.setVisibility(View.VISIBLE);
        this.btnCancel.setText(title);
        return this;
    }

    public Dialog setImageTypeCustom(int alertButtonStyle) {
        //  this.imgTypeDialog.setVisibility(View.VISIBLE);
        this.imgTypeDialog.setImageResource(alertButtonStyle);
        return this;
    }

    public Dialog setForceAction(boolean status) {
        if (status)   this.imgCloseDialog.setEnabled(false);
        return this;
    }


    public Dialog setTextContent(String title) {
        Spanned strHtml = Html.fromHtml(title);

        this.txtBody.setText(strHtml);
        return this;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_dialog_close:
                animOpenCloseDialog();
                break;
            case R.id.btn_dialog_cancel:
                animOpenCloseDialog();
                if (cancelListener != null)
                    cancelListener.cancel();
                dismiss();
                break;
            case R.id.btn_dialog_confirm:
                if (acceptListener != null)
                    acceptListener.accept();
                dismiss();
                break;


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void animOpenCloseDialog() {
        imgCloseDialog.startAnimation(MainApplication.SetAnimation("Rotate"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, Constants.DelayTimeDialogAnimation);
    }

    public interface OnAcceptInterface {
        void accept();
    }

    public interface OnCancelInterface {
        void cancel();
    }
}
