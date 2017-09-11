 package com.enation.eop.processor.backend.support;

 import com.enation.eop.processor.AbstractFacadePagetParser;
 import com.enation.eop.processor.FacadePage;
 import com.enation.eop.processor.core.Request;
 import com.enation.eop.processor.core.Response;




 public class GetPointJsWrapper
   extends AbstractFacadePagetParser
 {
   public GetPointJsWrapper(FacadePage page, Request request)
   {
     super(page, request);
   }

   protected Response parse(Response response) {
     String content = response.getContent();
     content = content + "<script>$(function(){Eop.Point.init();});</script>";
     response.setContent(content);
     return response;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\backend\support\GetPointJsWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */