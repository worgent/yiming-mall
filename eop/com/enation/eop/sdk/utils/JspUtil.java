 package com.enation.eop.sdk.utils;

 import java.io.IOException;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;

 public abstract class JspUtil
 {
   public static String getJspOutput(String jsppath, HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
   {
     WrapperResponse wrapperResponse = new WrapperResponse(response);
     wrapperResponse.setCharacterEncoding("UTF-8");
     try
     {
       request.getRequestDispatcher(jsppath).include(request, wrapperResponse);
     } catch (ServletException e) {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     }

     return wrapperResponse.getContent();
   }

   public static String getJspOutput1(String jsppath, HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
     WrapperResponse wrapperResponse = new WrapperResponse(response);
     try {
       request.getRequestDispatcher(jsppath).forward(request, wrapperResponse);
     } catch (ServletException e) {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     }
     return wrapperResponse.getContent();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\utils\JspUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */