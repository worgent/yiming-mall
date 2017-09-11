 package com.enation.framework.taglib;

 import java.io.IOException;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;








 public abstract class EnationTagSupport
   extends BodyTagSupport
 {
   protected Logger logger = Logger.getLogger(getClass());




   protected Long getShopId()
   {
     return (Long)getRequest().getAttribute("shopid");
   }






   protected ServletContext getServletContext()
   {
     ServletContext servletContext = this.pageContext.getServletContext();

     return servletContext;
   }





   public HttpServletRequest getRequest()
   {
     HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();

     return request;
   }

   public HttpServletResponse getResponse()
   {
     HttpServletResponse response = (HttpServletResponse)this.pageContext.getResponse();
     return response;
   }





   protected JspWriter getWriter()
   {
     return this.pageContext.getOut();
   }



   protected void print(String s)
   {
     try
     {
       getWriter().write(s);
     } catch (IOException e) {
       this.logger.error("taglib print error", e);
     }
   }




   protected void println(String s)
   {
     print(s + "\n");
   }






   protected WebApplicationContext getWebApplicationContext()
     throws RuntimeException
   {
     ServletContext servletContext = getServletContext();
     Object ob = servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

     if (ob == null) {
       this.logger.error("Get WebApplicationContext from ServletContext." + WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE + " is null.");



       throw new RuntimeException(" get obj form spring is null ");
     }

     WebApplicationContext context = (WebApplicationContext)ob;

     return context;
   }


   protected Object getBean(String name)
   {
     return getWebApplicationContext().getBean(name);
   }

   protected Object getBean(String name, Object[] args)
   {
     return getWebApplicationContext().getBean(name, args);
   }

   protected String getContextPath()
   {
     return getRequest().getContextPath();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\taglib\EnationTagSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */