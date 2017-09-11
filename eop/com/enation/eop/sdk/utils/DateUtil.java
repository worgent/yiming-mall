 package com.enation.eop.sdk.utils;

 import java.text.SimpleDateFormat;
 import java.util.Date;












 public class DateUtil
 {
   public static Date toDate(String date, String pattern)
   {
     if (("" + date).equals("")) {
       return null;
     }
     if (pattern == null) {
       pattern = "yyyy-MM-dd";
     }
     SimpleDateFormat sdf = new SimpleDateFormat(pattern);
     Date newDate = new Date();
     try {
       newDate = sdf.parse(date);
     } catch (Exception ex) {
       ex.printStackTrace();
     }
     return newDate;
   }






   public static String toString(Date date, String pattern)
   {
     if (date == null) {
       return "";
     }
     if (pattern == null) {
       pattern = "yyyy-MM-dd";
     }
     String dateString = "";
     SimpleDateFormat sdf = new SimpleDateFormat(pattern);
     try {
       dateString = sdf.format(date);
     } catch (Exception ex) {
       ex.printStackTrace();
     }
     return dateString;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\utils\DateUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */