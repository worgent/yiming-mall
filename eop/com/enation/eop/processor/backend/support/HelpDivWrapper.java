 package com.enation.eop.processor.backend.support;

 import com.enation.eop.processor.AbstractFacadePagetParser;
 import com.enation.eop.processor.FacadePage;
 import com.enation.eop.processor.core.Request;
 import com.enation.eop.processor.core.Response;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import javax.servlet.http.HttpServletRequest;

 public class HelpDivWrapper extends AbstractFacadePagetParser
 {
   public static String THE_Help_SCRIPT = "";

   public HelpDivWrapper(FacadePage page, Request request) {
     super(page, request);
   }

   protected Response parse(Response response)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String content = response.getContent();
     content = content + "<div id=\"HelpCtn\" class=\"popup-info-box\"><div class=\"bl\"><div class=\"br\">";
     content = content + "<div class=\"bd user-info\"><span id=\"HelpClose\" class=\"closebtn\" ></span>";
     content = content + "<span id=\"HelpBody\"></span>";
     content = content + "</div>";
     content = content + "</div>";
     content = content + "</div>";
     content = content + "<div class=\"bt\">";
     content = content + "<div class=\"corner bt-l\"></div>";
     content = content + "<div class=\"mid\"></div>";
     content = content + "<div class=\"corner bt-r\"></div>";
     content = content + "</div>";
     content = content + "</div>";

     if ("y".equals(request.getParameter("cpr"))) {
       content = content + THE_Help_SCRIPT;
     }
     response.setContent(content);
     return response;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\backend\support\HelpDivWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */