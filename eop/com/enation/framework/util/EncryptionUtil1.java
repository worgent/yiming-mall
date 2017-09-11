 package com.enation.framework.util;

 import java.net.URLEncoder;
 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;








 public class EncryptionUtil1
 {
   public static String GLOBAL_AUTH_KEY = "e317b362fafa0c96c20b8543d054b850";


















   public static String authcode(String $string, String $operation, String $key, int $expiry)
   {
     if (($string != null) &&
       ($operation.equals("DECODE"))) {
       try {
         $string = $string.replaceAll("\\.0\\.", " ");
         $string = $string.replaceAll("\\.1\\.", "=");
         $string = $string.replaceAll("\\.2\\.", "+");
         $string = $string.replaceAll("\\.3\\.", "/");
       }
       catch (Exception ex) {}
     }

     int $ckey_length = 4;




     $key = md5($key != null ? $key : GLOBAL_AUTH_KEY);
     String $keya = md5(substr($key, 0, 16));
     String $keyb = md5(substr($key, 16, 16));
     String $keyc = $ckey_length > 0 ? substr(md5(microtime()), -$ckey_length) : $operation.equals("DECODE") ? substr($string, 0, $ckey_length) : "";

     String $cryptkey = $keya + md5(new StringBuilder().append($keya).append($keyc).toString());
     int $key_length = $cryptkey.length();

     $string = sprintf("%010d", $expiry > 0 ? $expiry + time() : 0L) + substr(md5(new StringBuilder().append($string).append($keyb).toString()), 0, 16) + $string;
     int $string_length = $string.length();

     StringBuffer $result1 = new StringBuffer();

     int[] $box = new int['Ā'];
     for (int i = 0; i < 256; i++) {
       $box[i] = i;
     }

     int[] $rndkey = new int['Ā'];
     for (int $i = 0; $i <= 255; $i++) {
       $rndkey[$i] = $cryptkey.charAt($i % $key_length);
     }

     int $j = 0;
     for (int $i = 0; $i < 256; $i++) {
       $j = ($j + $box[$i] + $rndkey[$i]) % 256;
       int $tmp = $box[$i];
       $box[$i] = $box[$j];
       $box[$j] = $tmp;
     }

     $j = 0;
     int $a = 0;
     for (int $i = 0; $i < $string_length; $i++) {
       $a = ($a + 1) % 256;
       $j = ($j + $box[$a]) % 256;
       int $tmp = $box[$a];
       $box[$a] = $box[$j];
       $box[$j] = $tmp;

       $result1.append((char)($string.charAt($i) ^ $box[(($box[$a] + $box[$j]) % 256)]));
     }


     if ($operation.equals("DECODE")) {
       String $result = $result1.substring(0, $result1.length());
       if (((Integer.parseInt(substr($result.toString(), 0, 10)) == 0) || (Long.parseLong(substr($result.toString(), 0, 10)) - time() > 0L)) && (substr($result.toString(), 10, 16).equals(substr(md5(substr($result.toString(), 26) + $keyb), 0, 16)))) {
         return substr($result.toString(), 26);
       }
       return "";
     }

     String str = $keyc + base64_encode($result1.toString());
     try {
       str = str.replaceAll(" ", ".0.");
       str = str.replaceAll("=", ".1.");
       str = str.replaceAll("\\+", ".2.");
       str = str.replaceAll("\\/", ".3.");
     }
     catch (Exception ex) {}
     return str;
   }



   private static String urlencode(String value) { return URLEncoder.encode(value); }

   private static String md5(String input) {
     MessageDigest md;
     try {
       md = MessageDigest.getInstance("MD5");
     } catch (NoSuchAlgorithmException e) {
       e.printStackTrace();
       return null;
     }
     return byte2hex(md.digest(input.getBytes()));
   }

   private static String md5(long input) { return md5(String.valueOf(input)); }

   private static String base64_decode(String input)
   {
     try {
       return new String(Base64.decode(input.toCharArray()), "iso-8859-1");
     } catch (Exception e) {
       return e.getMessage();
     }
   }

   private static String base64_encode(String input) {
     try {
       return new String(Base64.encode(input.getBytes("iso-8859-1")));
     } catch (Exception e) {
       return e.getMessage();
     }
   }

   private static String byte2hex(byte[] b) { StringBuffer hs = new StringBuffer();
     String stmp = "";
     for (int n = 0; n < b.length; n++) {
       stmp = Integer.toHexString(b[n] & 0xFF);
       if (stmp.length() == 1) {
         hs.append("0").append(stmp);
       } else
         hs.append(stmp);
     }
     return hs.toString();
   }

   private static String substr(String input, int begin, int length) { return input.substring(begin, begin + length); }

   private static String substr(String input, int begin) {
     if (begin > 0) {
       return input.substring(begin);
     }
     return input.substring(input.length() + begin);
   }

   private static long microtime() {
     return System.currentTimeMillis();
   }

   private static long time() { return System.currentTimeMillis() / 1000L; }

   private static String sprintf(String format, long input) {
     String temp = "0000000000" + input;
     return temp.substring(temp.length() - 10);
   }

   public static void main(String[] args) {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\\util\EncryptionUtil1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */