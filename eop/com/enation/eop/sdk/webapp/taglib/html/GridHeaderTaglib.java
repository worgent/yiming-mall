 package com.enation.eop.sdk.webapp.taglib.html;

 import com.enation.eop.sdk.webapp.taglib.HtmlTaglib;

 public class GridHeaderTaglib extends HtmlTaglib
 {
   protected String postStart()
   {
     return "<thead><tr>";
   }

   protected String postEnd()
   {
     return "</tr></thead>";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\webapp\taglib\html\GridHeaderTaglib.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */