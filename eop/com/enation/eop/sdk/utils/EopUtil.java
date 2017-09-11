 package com.enation.eop.sdk.utils;

 import java.util.regex.Matcher;
 import java.util.regex.Pattern;




















 public class EopUtil
 {
   public static String wrapcss(String html, String wrapPath)
   {
     String pattern = "<link([^<|^>]*?)href=\"([^http|/eop|].*?)\"([^<|^>]*?)>";

     Pattern p = Pattern.compile(pattern, 34);
     Matcher m = p.matcher(html);
     if (m.find()) {
       html = m.replaceAll("<link$1href=\"" + wrapPath + "$2\"$3>");
     }

     return html;
   }









   public static String wrapjavascript(String html, String wrapPath)
   {
     String pattern = "<script([^<|^>]*?)src=\"([^http|/eop].*?)\"([^<|^>]*?)>";

     Pattern p = Pattern.compile(pattern, 34);
     Matcher m = p.matcher(html);

     if (m.find()) {
       html = m.replaceAll("<script$1src=\"" + wrapPath + "$2\"$3>");
     }



     return html;
   }









   public static String wrapimage(String content, String wrapPath)
   {
     String pattern = "<img([^<|^>]*?)src=\"([^http|/eop].*?)\"([^<|^>]*?)>";

     Pattern p = Pattern.compile(pattern, 34);
     Matcher m = p.matcher(content);

     if (m.find()) {
       content = m.replaceAll("<img$1src=\"" + wrapPath + "$2\"$3>");
     }

     return content;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\utils\EopUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */