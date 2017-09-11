 package com.enation.eop.processor.facade;

 import com.enation.eop.processor.IPageParamJsonGetter;
 import com.enation.eop.processor.IPageParser;
 import com.enation.eop.processor.IPageUpdater;
 import com.enation.eop.processor.Processor;
 import com.enation.eop.processor.SafeHttpRequestWrapper;
 import com.enation.eop.processor.core.Response;
 import com.enation.eop.processor.core.StringResponse;
 import com.enation.eop.processor.facade.support.HeaderResourcePageWrapper;
 import com.enation.eop.processor.facade.support.PageEditModeWrapper;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.util.RequestUtil;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;

















 public class FacadePageProcessor
   implements Processor
 {
   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
   {
     httpRequest = new SafeHttpRequestWrapper(httpRequest);
     String ctx = httpRequest.getContextPath();
     if (ctx.equals("/")) {
       ctx = "";
     }
     String method = RequestUtil.getRequestMethod(httpRequest);
     String uri = RequestUtil.getRequestUrl(httpRequest);
     Response response = new StringResponse();
     if (uri.startsWith(ctx + "/docs")) {
       IPageParser parser = (IPageParser)SpringContextHolder.getBean("docsPageParser");
       String content = parser.parse(uri);
       response.setContent(content);
       return response;
     }

     if (method.equals("GET")) {
       IPageParser parser = (IPageParser)SpringContextHolder.getBean("facadePageParser");


       if ((UserServiceFactory.getUserService().isUserLoggedIn()) && (httpRequest.getParameter("mode") != null))
       {
         parser = new PageEditModeWrapper(parser);
       }
       parser = new HeaderResourcePageWrapper(parser);
       String content = parser.parse(uri);
       response.setContent(content);
     }


     if (method.equals("PUT")) {
       String params = httpRequest.getParameter("widgetParams");
       String content = httpRequest.getParameter("bodyHtml");
       IPageUpdater pageUpdater = (IPageUpdater)SpringContextHolder.getBean("facadePageUpdater");
       pageUpdater.update(uri, content, params);
       response.setContent("{'state':0,'message':'页面保存成功'}");
     }


     if (method.equals("PARAMJSON")) {
       IPageParamJsonGetter pageParamJsonGetter = (IPageParamJsonGetter)SpringContextHolder.getBean("pageParamJsonGetter");
       String json = pageParamJsonGetter.getJson(uri);
       response.setContent(json);
     }
     return response;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\FacadePageProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */