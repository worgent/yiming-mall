 package com.enation.app.cms.component.plugin;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.AbstractFieldPlugin;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;







 @Component
 public class RichEditFieldPlugin
   extends AbstractFieldPlugin
 {
   public int getHaveSelectValue()
   {
     return 0;
   }




   public void onSave(Map article, DataField field)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String value = request.getParameter(field.getEnglish_name());
     if (value != null)
     {
       value = value.replaceAll(EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath() + "/attachment/", EopSetting.FILE_STORE_PREFIX + "/attachment/");
     }




     article.put(field.getEnglish_name(), value);
   }





   public Object onShow(DataField field, Object value)
   {
     if (value != null) {
       value = UploadUtil.replacePath(value.toString());
       return value;
     }
     return "";
   }

   public String onDisplay(DataField field, Object value) {
     StringBuffer html = new StringBuffer();
     html.append("<textarea id=\"" + field.getEnglish_name() + "\" name=\"" + field.getEnglish_name() + "\">");
     if (value != null) {
       value = UploadUtil.replacePath(value.toString());
       html.append(value);
     }
     html.append("</textarea>");
     html.append("<script type=\"text/javascript\">");
     html.append("$('#" + field.getEnglish_name() + "' ).ckeditor( );");
     html.append("</script>");

     return html.toString();
   }

   public String getDataType()
   {
     if (EopSetting.DBTYPE.equals("2")) {
       return "clob";
     }
     return "text";
   }

   public String getId()
   {
     return "richedit";
   }

   public String getName()
   {
     return "富文本编辑器";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\plugin\RichEditFieldPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */