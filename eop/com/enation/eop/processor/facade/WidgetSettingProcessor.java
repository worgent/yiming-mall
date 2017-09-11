 package com.enation.eop.processor.facade;

 import com.enation.eop.processor.Processor;
 import com.enation.eop.processor.core.Response;
 import com.enation.eop.processor.core.StringResponse;
 import com.enation.eop.processor.widget.IWidgetCfgHtmlParser;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.util.RequestUtil;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.log4j.Logger;









 public class WidgetSettingProcessor
   implements Processor
 {
   protected final Logger logger = Logger.getLogger(getClass());












   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
   {
     Map<String, String> params = RequestUtil.paramToMap(httpRequest);
     IWidgetCfgHtmlParser widgetCfgParser = (IWidgetCfgHtmlParser)SpringContextHolder.getBean("localWidgetCfgPaser");

     String content = widgetCfgParser.parse(params);
     Response response = new StringResponse();
     response.setContent(content);
     return response;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\WidgetSettingProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */