 package com.enation.eop.sdk.utils;














 public class HtmlUtil
 {
   public static String appendTo(String html, String nodeName, String content)
   {
     return html.replaceAll("</" + nodeName + ">", content + "</" + nodeName + ">");
   }







   public static String insertTo(String html, String nodeName, String content)
   {
     return html.replaceAll("<" + nodeName + ">", "<" + nodeName + ">" + content);
   }

   public static void main(String[] args) {
     String html = "<html><head>adfbb</head><body>adfadsf</body></html>";
     html = insertTo(html, "head", "abc");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\utils\HtmlUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */