 package com.enation.eop.processor.facade;

 import com.enation.eop.processor.Processor;
 import com.enation.eop.processor.SafeHttpRequestWrapper;
 import com.enation.eop.processor.core.Response;
 import com.enation.eop.processor.core.StringResponse;
 import com.enation.eop.processor.facade.support.LocalWidgetParser;
 import com.enation.eop.processor.widget.IWidgetParser;
 import com.enation.framework.util.RequestUtil;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;














 public class WidgetProcessor
   implements Processor
 {
   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
   {
     httpRequest = new SafeHttpRequestWrapper(httpRequest);



     Map<String, String> widgetParams = RequestUtil.paramToMap(httpRequest);




     IWidgetParser widgetPaser = new LocalWidgetParser();
     String content = widgetPaser.parse(widgetParams);
     Response response = new StringResponse();

     response.setContent(content);
     return response;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\WidgetProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */