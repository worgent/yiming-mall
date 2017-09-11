 package com.enation.app.cms.core.plugin;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;


























 public abstract class AbstractFieldPlugin
   extends AutoRegisterPlugin
   implements IFieldSaveEvent, IFieldDispalyEvent, IFieldValueShowEvent
 {
   public abstract String getId();

   public abstract String getName();

   public abstract int getHaveSelectValue();

   public String getType()
   {
     return "field";
   }





   public void onSave(Map article, DataField field)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     article.put(field.getEnglish_name(), request.getParameter(field.getEnglish_name()));
   }







   public Object onShow(DataField field, Object value)
   {
     if (value != null)
       return value.toString();
     return "";
   }






   protected String wrappValidHtml(DataField field)
   {
     StringBuffer html = new StringBuffer();
     if (field.getIs_validate() == 1) {
       html.append(" isrequired=\"true\" ");
     }

     return html.toString();
   }




















   public String getDataType()
   {
     return "varchar(255)";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\plugin\AbstractFieldPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */