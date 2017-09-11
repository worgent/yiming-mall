 package com.enation.eop.processor.facade;

 import com.enation.eop.processor.Processor;
 import com.enation.eop.processor.core.InputStreamResponse;
 import com.enation.eop.processor.core.Response;
 import com.enation.eop.processor.core.StringResponse;
 import com.enation.framework.resource.impl.ResourceBuilder;
 import java.io.ByteArrayInputStream;
 import java.io.InputStream;
 import java.io.UnsupportedEncodingException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;









 public class ResourceProcessor
   implements Processor
 {
   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
   {
     String id = httpRequest.getParameter("id");
     String type = httpRequest.getParameter("type");

     String content = null;

     if ("javascript".equals(type)) {
       content = ResourceBuilder.getScript(id);
     }

     if ("css".equals(type)) {
       content = ResourceBuilder.getCss(id);
     }


     if (content != null) {
       InputStream in = null;
       try {
         in = new ByteArrayInputStream(content.getBytes("UTF-8"));
       } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
       }
       Response response = new InputStreamResponse(in);
       if ("javscript".equals(type)) {
         response.setContentType("application/x-javascript");
       }
       if ("css".equals(type)) {
         response.setContentType("text/css");
       }
       return response;
     }
     Response response = new StringResponse();
     return response;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\ResourceProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */