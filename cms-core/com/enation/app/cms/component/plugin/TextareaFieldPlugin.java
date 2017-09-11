 package com.enation.app.cms.component.plugin;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.AbstractFieldPlugin;
 import org.springframework.stereotype.Component;





 @Component
 public class TextareaFieldPlugin
   extends AbstractFieldPlugin
 {
   public String onDisplay(DataField field, Object value)
   {
     StringBuffer html = new StringBuffer();
     html.append("<textarea");
     html.append(" name=\"");
     html.append(field.getEnglish_name());
     html.append("\" style=\"width:250px;height:100px\" ");
     html.append(wrappValidHtml(field));
     html.append(">");
     if (value != null) {
       html.append(value);
     }

     html.append("</textarea>");

     return html.toString();
   }

   public String getDataType() {
     return "text";
   }

   public String getId()
   {
     return "textarea";
   }

   public String getName()
   {
     return "多行文本框";
   }

   public int getHaveSelectValue()
   {
     return 0;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\plugin\TextareaFieldPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */