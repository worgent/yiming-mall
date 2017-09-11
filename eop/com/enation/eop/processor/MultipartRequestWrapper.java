 package com.enation.eop.processor;

 import com.opensymphony.util.TextUtils;
 import java.util.Enumeration;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletRequestWrapper;
 import javazoom.upload.MultipartFormDataRequest;

 public class MultipartRequestWrapper
   extends HttpServletRequestWrapper
 {
   private MultipartFormDataRequest mrequest;

   public MultipartRequestWrapper(HttpServletRequest request, MultipartFormDataRequest _mrequest)
   {
     super(request);
     this.mrequest = _mrequest;
   }

   public String getParameter(String name)
   {
     String value = this.mrequest.getParameter(name);
     value = TextUtils.htmlEncode(value);
     return value;
   }

   public Enumeration getParameterNames()
   {
     return this.mrequest.getParameterNames();
   }

   public String[] getParameterValues(String name)
   {
     return this.mrequest.getParameterValues(name);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\MultipartRequestWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */