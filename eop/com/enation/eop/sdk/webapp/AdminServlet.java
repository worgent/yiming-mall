 package com.enation.eop.sdk.webapp;

 import com.enation.eop.sdk.EopServlet;
 import com.enation.eop.sdk.utils.JspUtil;
 import java.io.IOException;
 import java.io.PrintWriter;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;




 public class AdminServlet
   implements EopServlet
 {
   public void service(HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     String servletPath = request.getServletPath();
     request.setAttribute("content", JspUtil.getJspOutput(servletPath, request, response));
     String content = JspUtil.getJspOutput("/eop/main_frame.jsp", request, response);

     response.setContentType("text/html; charset=UTF-8");
     response.getWriter().print(content);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\webapp\AdminServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */