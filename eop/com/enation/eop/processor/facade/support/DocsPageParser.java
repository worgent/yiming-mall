 package com.enation.eop.processor.facade.support;

 import com.enation.eop.processor.IPageParser;
 import com.enation.eop.processor.core.UrlNotFoundException;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.utils.FreeMarkerUtil;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.TagCreator;
 import com.enation.framework.util.StringUtil;
 import com.sun.xml.messaging.saaj.util.ByteOutputStream;
 import freemarker.template.Configuration;
 import freemarker.template.Template;
 import java.io.FileNotFoundException;
 import java.io.OutputStreamWriter;
 import java.io.Writer;
 import java.util.Enumeration;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;




 public class DocsPageParser
   implements IPageParser
 {
   public String parse(String uri)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     if (uri.indexOf('?') > 0) {
       uri = uri.substring(0, uri.indexOf('?'));
     }

     String ctx = request.getContextPath();
     if (ctx.equals("/")) {
       ctx = "";
     }
     uri = uri.replaceAll(ctx, "");
     Map<String, Object> widgetData = new HashMap();
     try
     {
       Enumeration<String> paramNames = request.getParameterNames();
       while (paramNames.hasMoreElements()) {
         String name = (String)paramNames.nextElement();
         String value = request.getParameter(name);
         widgetData.put(name, value);
       }

       widgetData.put("newTag", new TagCreator());
       widgetData.put("staticserver", EopSetting.IMG_SERVER_DOMAIN);
       widgetData.put("ctx", request.getContextPath());

       String themeFld = EopSetting.EOP_PATH;
       Configuration cfg = FreeMarkerUtil.getFolderCfg(themeFld);

       Template temp = cfg.getTemplate(uri);
       ByteOutputStream stream = new ByteOutputStream();

       Writer out = new OutputStreamWriter(stream);
       temp.process(widgetData, out);

       out.flush();
       return stream.toString();
     }
     catch (FileNotFoundException e)
     {
       throw new UrlNotFoundException();
     }
     catch (Exception e) {
       HttpServletResponse httpResponse = ThreadContextHolder.getHttpResponse();

       httpResponse.setStatus(500);
       String error = StringUtil.getStackTrace(e);
       if (StringUtil.isEmpty(error)) {
         error = "";
       }
       return error.replaceAll("\n", "<br>");
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\support\DocsPageParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */