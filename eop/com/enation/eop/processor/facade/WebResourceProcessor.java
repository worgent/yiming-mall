 package com.enation.eop.processor.facade;

 import com.enation.eop.processor.Processor;
 import com.enation.eop.processor.core.InputStreamResponse;
 import com.enation.eop.processor.core.Response;
 import java.io.InputStream;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;








 public class WebResourceProcessor
   implements Processor
 {
   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
   {
     String path = httpRequest.getServletPath();

     path = path.replaceAll("/resource/", "");

     try
     {
       InputStream in = getClass().getClassLoader().getResourceAsStream(path);
       Response response = new InputStreamResponse(in);




       if (path.toLowerCase().endsWith(".js")) response.setContentType("application/x-javascript");
       if (path.toLowerCase().endsWith(".css")) response.setContentType("text/css");
       if (path.toLowerCase().endsWith(".jpg")) response.setContentType("image/jpeg");
       if (path.toLowerCase().endsWith(".gif")) response.setContentType("image/gif");
       if (path.toLowerCase().endsWith(".png")) response.setContentType("image/png");
       if (path.toLowerCase().endsWith(".swf")) { response.setContentType("application/x-shockwave-flash");
       }
       return response;
     }
     catch (RuntimeException e) {
       e.printStackTrace(); }
     return null;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\WebResourceProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */