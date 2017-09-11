 package com.enation.app.cms.component.plugin;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.AbstractFieldPlugin;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.stereotype.Component;





 @Component
 public class SelectFieldPlugin
   extends AbstractFieldPlugin
 {
   public int getHaveSelectValue()
   {
     return 1;
   }

   public String onDisplay(DataField field, Object value) {
     StringBuffer html = new StringBuffer();
     html.append("<select name=\"");

     html.append(field.getEnglish_name());
     html.append("\"");
     html.append(wrappValidHtml(field));
     html.append(">");

     html.append("<option value=\"0\">全部</option>");
     String values = field.getSave_value();
     int i = 0;
     if (values != null) {
       String[] valueAr = StringUtils.split(values, ",");
       for (String v : valueAr) {
         html.append("<option ");
         html.append(" value=\"");
         html.append(i);
         html.append("\"");

         if ((value != null) && (i == Integer.valueOf("" + value).intValue())) {
           html.append(" selected=\"true\"");
         }

         html.append(" >");
         html.append(v);
         html.append("</option>");
         i++;
       }
     }
     html.append("</select>");

     return html.toString();
   }

   public Object onShow(DataField field, Object value) {
     if (value != null) {
       int index = Integer.valueOf(value.toString()).intValue();
       String valueStr = field.getSave_value();
       if (valueStr != null) {
         String[] values = StringUtils.split(valueStr, ",");
         return values[index];
       }
       return "";
     }
     return "";
   }

   public String getId()
   {
     return "select";
   }

   public String getName()
   {
     return "下拉框";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\plugin\SelectFieldPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */