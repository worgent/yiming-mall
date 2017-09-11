 package com.enation.app.cms.component.plugin;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.AbstractFieldPlugin;
 import freemarker.template.utility.StringUtil;
 import org.springframework.stereotype.Component;








 @Component
 public class InputFieldPlugin
   extends AbstractFieldPlugin
 {
   public String getId()
   {
     return "input";
   }

   public String getName()
   {
     return "单行文本框";
   }

   public String onDisplay(DataField field, Object value) {
     StringBuffer html = new StringBuffer("<input type=\"text\" class=\"input_text\" style=\"width:450px\" name=\"");
     html.append(field.getEnglish_name());
     html.append("\"");

     if (value != null) {
       html.append(" value=\"");
       value = StringUtil.HTMLEnc(value.toString());
       html.append(value);
       html.append("\"");
     }

     html.append(wrappValidHtml(field));

     html.append(" />");

     return html.toString();
   }

   public int getHaveSelectValue() {
     return 0;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\plugin\InputFieldPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */