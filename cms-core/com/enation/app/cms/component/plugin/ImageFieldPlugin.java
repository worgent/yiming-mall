 package com.enation.app.cms.component.plugin;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.AbstractFieldPlugin;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.sun.xml.messaging.saaj.util.ByteOutputStream;
 import freemarker.template.Configuration;
 import freemarker.template.DefaultObjectWrapper;
 import freemarker.template.Template;
 import java.io.OutputStreamWriter;
 import java.io.Writer;
 import java.util.HashMap;
 import java.util.Locale;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;









 @Component
 public class ImageFieldPlugin
   extends AbstractFieldPlugin
 {
   public int getHaveSelectValue()
   {
     return 0;
   }




   public Object onShow(DataField field, Object value)
   {
     if (value != null) {
       value = UploadUtil.replacePath(value.toString());
     }
     return value;
   }


   public String onDisplay(DataField field, Object value)
   {
     try
     {
       Map data = new HashMap();
       data.put("fieldname", field.getEnglish_name());
       if (value != null) {
         value = UploadUtil.replacePath(value.toString());
       }
       data.put("value", value);
       data.put("ctx", ThreadContextHolder.getHttpRequest().getContextPath());
       data.put("ext", EopSetting.EXTENSION);
       Configuration cfg = new Configuration();
       cfg.setObjectWrapper(new DefaultObjectWrapper());
       cfg.setDefaultEncoding("UTF-8");
       cfg.setLocale(Locale.CHINA);
       cfg.setEncoding(Locale.CHINA, "UTF-8");

       cfg.setClassForTemplateLoading(getClass(), "");
       Template temp = cfg.getTemplate("ImageFieldPlugin.html");
       ByteOutputStream stream = new ByteOutputStream();

       Writer out = new OutputStreamWriter(stream);
       temp.process(data, out);

       out.flush();
       return stream.toString();

     }
     catch (Exception e)
     {
       return "CMS插件解析出错" + e.getMessage();
     }
   }

   public void onSave(Map article, DataField field)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String path = request.getParameter(field.getEnglish_name());
     if (path != null) {
       path = path.replaceAll(EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath(), EopSetting.FILE_STORE_PREFIX);
     }
     article.put(field.getEnglish_name(), path);
   }

   public String getId()
   {
     return "image";
   }

   public String getName()
   {
     return "图片";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\plugin\ImageFieldPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */