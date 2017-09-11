 package com.enation.eop.processor.backend.support;

 import com.enation.eop.processor.AbstractFacadePagetParser;
 import com.enation.eop.processor.FacadePage;
 import com.enation.eop.processor.core.Request;
 import com.enation.eop.processor.core.Response;
 import com.enation.eop.sdk.utils.JspUtil;
 import javax.servlet.http.HttpServletRequest;




 public class BackTemplateWrapper
   extends AbstractFacadePagetParser
 {
   public BackTemplateWrapper(FacadePage page, Request request)
   {
     super(page, request);
   }

   protected Response parse(Response response) {
     this.httpRequest.setAttribute("content", response.getContent());
     String content = JspUtil.getJspOutput("/admin/main_frame.jsp", this.httpRequest, this.httpResponse);
     response.setContent(content);
     return response;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\backend\support\BackTemplateWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */