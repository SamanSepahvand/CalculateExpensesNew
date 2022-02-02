package com.samansepahvand.calculateexpensesnew.infrastructure;

import com.samansepahvand.calculateexpensesnew.business.metamodel.AutoPriceModel;

public class BankStringFormat {



    public static class BankName{

        public  static   String SinaBank="بانک سینا";
        public  static  String MelliBank="بانک ملی";


    }




    public static AutoPriceModel SetBankType(String fullSms){
        try{
            if (fullSms.contains("بانک سينا")){
                return  GetPriceFromSinaBank(fullSms) ;

            }else  if (fullSms.contains("بانک ملي")){
                return GetPriceFromMelliBank(fullSms) ;

            }
            return  null;
        }catch (Exception e){
            return null;
        }
    }


    public static  AutoPriceModel GetPriceFromSinaBank(String fullSms){
        try{
//            String temp ="onReceive: *بانک سينا*\n" +
//                    "    برداشت از کارت 5516*****6393\n" +
//                    "    مبلغ: 139,050 ريال\n" +
//                    "    مانده: 39,373,183 ريال\n" +
//                    "    زمان: 11بهمن ماه 1400 ; 19:16:44\n" +
//                    " ";

            if (!fullSms.contains("برداشت"))  return null;


            String[] sms=fullSms.split("مبلغ");
            String[] sms1=sms[1].split("مانده");
            String numberOnly= sms1[0].replaceAll("[^0-9]", "");

            return  new AutoPriceModel(BankName.SinaBank,"خرید",numberOnly);
        }catch (Exception e){
            return null;
        }
    }

    public static  AutoPriceModel GetPriceFromMelliBank(String fullSms){
        try{
//            String temp ="بانک ملي\n" +
//                    "    خريداينترنتي:402,210-\n" +
//                    "    حساب:45001\n" +
//                    "    مانده:17,342,482\n" +
//                    "    1004-10:43" +
//                    " ";
            String[] sms=fullSms.split("-");
            String numberOnly=sms[0].replaceAll("[^0-9]", ""); ;


            String[] name=sms[0].split("ملي");
            String[] name1=name[1].split(":");

            return  new AutoPriceModel(BankName.MelliBank,name1[0],numberOnly);
        }catch (Exception e){
            return null;
        }
    }




}
