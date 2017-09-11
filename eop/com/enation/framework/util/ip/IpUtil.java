 package com.enation.framework.util.ip;

 import java.io.UnsupportedEncodingException;
 import java.util.StringTokenizer;









 public class IpUtil
 {
   public static byte[] getIpByteArrayFromString(String ip)
   {
     byte[] ret = new byte[4];
     StringTokenizer st = new StringTokenizer(ip, ".");
     try {
       ret[0] = ((byte)(Integer.parseInt(st.nextToken()) & 0xFF));
       ret[1] = ((byte)(Integer.parseInt(st.nextToken()) & 0xFF));
       ret[2] = ((byte)(Integer.parseInt(st.nextToken()) & 0xFF));
       ret[3] = ((byte)(Integer.parseInt(st.nextToken()) & 0xFF));
     } catch (Exception e) {
       e.printStackTrace();
     }

     return ret;
   }



   public static String getIpStringFromBytes(byte[] ip)
   {
     StringBuilder sb = new StringBuilder();
     sb.append(ip[0] & 0xFF);
     sb.append('.');
     sb.append(ip[1] & 0xFF);
     sb.append('.');
     sb.append(ip[2] & 0xFF);
     sb.append('.');
     sb.append(ip[3] & 0xFF);
     return sb.toString();
   }







   public static String getString(byte[] b, int offset, int len, String encoding)
   {
     try
     {
       return new String(b, offset, len, encoding);
     } catch (UnsupportedEncodingException e) {}
     return new String(b, offset, len);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\\util\ip\IpUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */