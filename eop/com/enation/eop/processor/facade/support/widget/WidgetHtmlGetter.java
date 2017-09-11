 package com.enation.eop.processor.facade.support.widget;

 import com.enation.eop.processor.facade.support.LocalWidgetParser;
 import com.enation.eop.processor.facade.support.WidgetEditModeWrapper;
 import com.enation.eop.processor.widget.IWidgetHtmlGetter;
 import com.enation.eop.processor.widget.IWidgetParser;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import java.util.Map;







 public class WidgetHtmlGetter
   implements IWidgetHtmlGetter
 {
   public String process(Map<String, String> params, String page)
   {
     IWidgetParser widgetPaser = new LocalWidgetParser();
     widgetPaser = new BorderWrapper(widgetPaser);
     if ((UserServiceFactory.getUserService().isUserLoggedIn()) && ("edit".equals(params.get("mode")))) {
       widgetPaser = new WidgetEditModeWrapper(widgetPaser);
     }
     String html = widgetPaser.parse(params);
     return html;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\support\widget\WidgetHtmlGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */