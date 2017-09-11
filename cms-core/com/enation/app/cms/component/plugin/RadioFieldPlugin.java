 package com.enation.app.cms.component.plugin;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.AbstractFieldPlugin;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.stereotype.Component;





 @Component
 public class RadioFieldPlugin
   extends AbstractFieldPlugin
 {
   public String onDisplay(DataField field, Object value)
   {
     StringBuffer html = new StringBuffer();

     String values = field.getSave_value();
     int i = 0;
     if (values != null) {
       String[] valueAr = StringUtils.split(values, ",");
       for (String v : valueAr) {
         html.append("<input type=\"radio\"");
         html.append(" name=\"");
         html.append(field.getEnglish_name());
         html.append("\" value=\"");
         html.append(i);
         html.append("\"");
         if ((value == null) && (i == 0)) {
           html.append(" checked=\"true\"");
         }
         if ((value != null) && (i == Integer.valueOf("" + value).intValue())) {
           html.append(" checked=\"true\"");
         }

         html.append(" />");
         html.append(v);
         i++;
       }
     }
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
     return "radio";
   }

   public String getName()
   {
     return "单选按钮";
   }

   public int getHaveSelectValue() {
     return 1;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\plugin\RadioFieldPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */