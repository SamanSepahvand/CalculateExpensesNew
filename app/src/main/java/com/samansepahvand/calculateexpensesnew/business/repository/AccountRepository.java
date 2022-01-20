package com.samansepahvand.calculateexpensesnew.business.repository;

import android.content.Context;
import android.util.Log;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.samansepahvand.calculateexpensesnew.business.metamodel.DetailMainInfo;
import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.metamodel.UserInformations;
import com.samansepahvand.calculateexpensesnew.business.metamodel.UserLoginParam;
import com.samansepahvand.calculateexpensesnew.db.Account;
import com.samansepahvand.calculateexpensesnew.db.Info;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class AccountRepository {
    private static final String TAG = "AccountRepository";
    private static AccountRepository accountRepository = null;
    private Context context;

    private AccountRepository() {

    }


    public static AccountRepository getInstance() {
        if (accountRepository == null) {
            synchronized (AccountRepository.class) {
                if (accountRepository == null) {
                    accountRepository = new AccountRepository();
                }
            }
        }
        return accountRepository;
    }

    public Account getAccount() {

        return new Select().from(Account.class).executeSingle();
    }



    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public OperationResult<Account> LoginAuth(UserLoginParam userLoginParam) {


        try {

            String hashPassword = md5(userLoginParam.getPassword());

            Account account = new Select().from(Account.class)
                    .where(Account.PASSWORD + "=?", hashPassword)
                    .where(Account.USER_NAME + "=?", userLoginParam.getUserName())
                    .executeSingle();

            if (account == null)
                return new OperationResult<>("نام کاربری  را رمز عبور اشتباه می باشد!", false, null);


            return new OperationResult<>(null, true, null, account, null);
        } catch (Exception e) {
            return new OperationResult<>(e.getMessage(), false, null);
        }

    }

    public static void FillShared(Account ac) {
        if (ac == null) return;
        Log.e("TAG", "FillShared: "+"load" );
        UserInformations.SetValues(ac.getId().intValue(), ac.getUserName(), ac.getPassword(),ac.getFirstName()+" "+ac.getLastName());
    }




    public OperationResult<Account> AutoAuthentication() {

        try {
            Account account = new Select().from(Account.class)
                    .executeSingle();
            if (account == null) return new OperationResult<>("کاربری یافت نشد !", false, null);


            return new OperationResult<>(null, true, null, account, null);
        } catch (Exception e) {
            return new OperationResult<>(e.getMessage(), false, null);
        }


    }


    public OperationResult<Account> SignUp(Account account) {

        try {
            account.setPassword(md5(account.getPassword()));
            account.save();
            return new OperationResult<>(null, true, null, account, null);
        } catch (Exception e) {
            return new OperationResult<>(e.getMessage(), false, null);
        }


    }
}