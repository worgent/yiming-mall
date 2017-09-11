 package com.enation.eop.processor.core;

 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;







 public class RequestWrapper
   implements Request
 {
   protected Logger logger = Logger.getLogger(getClass());


   protected Request request;


   public RequestWrapper(Request request)
   {
     this.request = request;
   }






   public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
   {
     return this.request.execute(uri, httpResponse, httpRequest);
   }




   public Response execute(String uri)
   {
     return this.request.execute(uri);
   }

   public Request getRequest() {
     return this.request;
   }

   public void setExecuteParams(Map<String, String> params) {
     this.request.setExecuteParams(params);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\core\RequestWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */