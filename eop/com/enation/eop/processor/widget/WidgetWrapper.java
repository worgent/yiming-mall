 package com.enation.eop.processor.widget;

 import java.util.Map;




 public class WidgetWrapper
   implements IWidgetParser
 {
   protected IWidgetParser widgetPaser;

   public WidgetWrapper(IWidgetParser paser)
   {
     this.widgetPaser = paser;
   }

   public String parse(Map<String, String> params) {
     return this.widgetPaser.parse(params);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\widget\WidgetWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */