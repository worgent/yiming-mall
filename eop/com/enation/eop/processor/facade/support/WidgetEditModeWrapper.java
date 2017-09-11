 package com.enation.eop.processor.facade.support;

 import com.enation.eop.processor.widget.IWidgetParser;
 import com.enation.eop.processor.widget.WidgetWrapper;
 import java.util.Map;











 public class WidgetEditModeWrapper
   extends WidgetWrapper
 {
   public WidgetEditModeWrapper(IWidgetParser paser)
   {
     super(paser);
   }

   public String parse(Map<String, String> params) {
     String content = super.parse(params);
     return wrap(content, params);
   }

   private String wrap(String content, Map<String, String> params) {
     content = "<div  class=\"handle\" ><span><a href=\"javascript:;\"  class=\"edit\">设置</a></span><span><a href=\"javascript:;\" class=\"delete\">删除</a></span><span class=\"adjust\"><input type=\"checkbox\"  />微调</span><span class=\"lockwidth\"><input type=\"checkbox\"  />锁定宽</span></div><div class=\"wrapHelper\"></div>" + content;


     content = "<div class=\"widget\" eop_type=\"widget\" id=\"" + (String)params.get("id") + "\">" + content + "</div>";

     return content;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\support\WidgetEditModeWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */