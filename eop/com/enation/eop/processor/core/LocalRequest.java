 package com.enation.eop.processor.core;

 import com.enation.eop.sdk.utils.JspUtil;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;


















 public class LocalRequest
   implements Request
 {
   public void setExecuteParams(Map<String, String> params) {}

   public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
   {
     String content = JspUtil.getJspOutput(uri, httpRequest, httpResponse);

     Response response = new StringResponse();
     response.setContent(content);

     return response;
   }




   public Response execute(String uri)
   {
     return null;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\core\LocalRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */