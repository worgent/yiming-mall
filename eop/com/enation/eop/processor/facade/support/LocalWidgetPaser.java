 package com.enation.eop.processor.facade.support;

 import com.enation.eop.processor.core.EopException;
 import com.enation.eop.processor.core.UrlNotFoundException;
 import com.enation.eop.processor.widget.IWidgetParser;
 import com.enation.eop.sdk.widget.IWidget;
 import com.enation.framework.component.context.WidgetContext;
 import com.enation.framework.context.spring.SpringContextHolder;
 import java.util.Map;
 import org.apache.log4j.Logger;






 @Deprecated
 public class LocalWidgetPaser
   implements IWidgetParser
 {
   protected final Logger logger = Logger.getLogger(getClass());

   public String parse(Map<String, String> params) {
     if (params == null) { throw new EopException("挂件参数不能为空");
     }
     String widgetType = (String)params.get("type");
     if (widgetType == null) { throw new EopException("挂件类型不能为空");
     }
     if ((!"product_install".equals(widgetType)) &&
       (!WidgetContext.getWidgetState(widgetType).booleanValue()))
     {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug("挂件[" + widgetType + "]已停用");
       }

       return "此挂件已停用";
     }
     try
     {
       IWidget widget = (IWidget)SpringContextHolder.getBean(widgetType);
       String content;
//       String content;
       if (widget == null) { content = "widget[" + widgetType + "]not found";
       } else {
         content = widget.process(params);
         widget.update(params);
       }

       return content;
     }
     catch (UrlNotFoundException e) {
       throw e;
     } catch (Exception e) {
       e.printStackTrace(); }
     return "widget[" + widgetType + "]pase error ";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\support\LocalWidgetPaser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */