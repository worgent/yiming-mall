 package com.enation.eop.sdk.webapp.taglib;

 import javax.servlet.jsp.JspException;

 public abstract class HtmlTaglib extends BaseTaglibSupport
 {
   protected String startHtml = "";
   protected String endHtml = "";

   protected void startAppend(String html) {
     this.startHtml += html;
   }

   protected void endAppend(String html) { this.endHtml += html; }

   public int doStartTag() throws JspException
   {
     this.startHtml = "";
     print(postStart());
     return 1;
   }

   public int doAfterBody() {
     String content = postEnd();
     print(content);
     return 0;
   }

   protected abstract String postStart();

   protected abstract String postEnd();
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\webapp\taglib\HtmlTaglib.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */