package com.samansepahvand.calculateexpensesnew.business.metamodel;


import com.samansepahvand.calculateexpensesnew.business.repository.AccountRepository;

public class UserInformations {

    private static int userId;
    private static String userName;
    private static String password;
    private static String fullName;


//    public static void SetValues(int puserId,String psessionId,int pwarehouseId,int psalesManId ,int proleId,int pcurrentDate,String puserName,String ppassword){
//        userId = puserId;
//        sessionId = psessionId;
//        warehouseId = pwarehouseId;
//        salesManId = psalesManId;
//        roleId = proleId;
//        currentDate = pcurrentDate;
//        userName = puserName;
//        password = ppassword;
//
//    }
    public static void SetValues(int userIdd,String puserName, String passwordd, String fullNames){
        userName = puserName;
        password = passwordd;
        fullName=fullNames;
        userId=userIdd;

    }
    public static int getUserId(){
        if(userId == 0){
            AccountRepository.FillShared(AccountRepository.getInstance().getAccount());
        }
        return userId;
    }

    public static String getUserName(){
        if(userName == null){
            AccountRepository.FillShared(AccountRepository.getInstance().getAccount());
        }
        return userName;
    }

    public static String getPassword(){
        if(password == null){
            AccountRepository.FillShared(AccountRepository.getInstance().getAccount());
        }
        return password;
    }



    public static String getFullName() {
        if (fullName==null)
            AccountRepository.FillShared(AccountRepository.getInstance().getAccount());
        return fullName;
    }
}
