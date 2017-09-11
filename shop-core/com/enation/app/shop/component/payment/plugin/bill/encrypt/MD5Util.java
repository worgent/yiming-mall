 package com.enation.app.shop.component.payment.plugin.bill.encrypt;

 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;



















 public class MD5Util
 {
   static MessageDigest getDigest()
   {
     try
     {
       return MessageDigest.getInstance("MD5");
     } catch (NoSuchAlgorithmException e) {
       throw new RuntimeException(e);
     }
   }








   public static byte[] md5(byte[] data)
   {
     return getDigest().digest(data);
   }








   public static byte[] md5(String data)
   {
     return md5(data.getBytes());
   }








   public static String md5Hex(byte[] data)
   {
     return HexUtil.toHexString(md5(data));
   }








   public static String md5Hex(String data)
   {
     return HexUtil.toHexString(md5(data));
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\payment\plugin\bill\encrypt\MD5Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */