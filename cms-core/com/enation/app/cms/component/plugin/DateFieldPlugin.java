 package com.enation.app.cms.component.plugin;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.AbstractFieldPlugin;
 import org.springframework.stereotype.Component;






 @Component
 public class DateFieldPlugin
   extends AbstractFieldPlugin
 {
   public int getHaveSelectValue()
   {
     return 0;
   }

   public String onDisplay(DataField field, Object value) {
     StringBuffer html = new StringBuffer();

     html.append("<input type=\"text\" name=\"");
     html.append(field.getEnglish_name());
     html.append("\" readonly=\"true\"");
     if (value != null) {
       html.append("value=\"");
       html.append(value);
       html.append("\"");
     }
     html.append(" class=\"dateinput\" ");
     html.append(">");

     return html.toString();
   }


   public String getId()
   {
     return "dateinput";
   }


   public String getName()
   {
     return "日期控件";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\plugin\DateFieldPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */