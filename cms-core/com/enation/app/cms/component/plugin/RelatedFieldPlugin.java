 package com.enation.app.cms.component.plugin;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.AbstractFieldPlugin;
 import com.sun.xml.messaging.saaj.util.ByteOutputStream;
 import freemarker.template.Configuration;
 import freemarker.template.DefaultObjectWrapper;
 import freemarker.template.Template;
 import java.io.OutputStreamWriter;
 import java.io.Writer;
 import java.util.HashMap;
 import java.util.Locale;
 import java.util.Map;
 import org.springframework.stereotype.Component;







 @Component
 public class RelatedFieldPlugin
   extends AbstractFieldPlugin
 {
   public int getHaveSelectValue()
   {
     return 0;
   }

   public String onDisplay(DataField field, Object value) {
     try {
       Map data = new HashMap();
       data.put("fieldname", field.getEnglish_name());
       data.put("value", value);
       Configuration cfg = new Configuration();
       cfg.setObjectWrapper(new DefaultObjectWrapper());
       cfg.setDefaultEncoding("UTF-8");
       cfg.setLocale(Locale.CHINA);
       cfg.setEncoding(Locale.CHINA, "UTF-8");

       cfg.setClassForTemplateLoading(getClass(), "");
       Template temp = cfg.getTemplate("RelatedFieldPlugin.html");
       ByteOutputStream stream = new ByteOutputStream();

       Writer out = new OutputStreamWriter(stream);
       temp.process(data, out);

       out.flush();
       return stream.toString();
     }
     catch (Exception e)
     {
       return "挂件解析出错" + e.getMessage();
     }
   }

   public String getId()
   {
     return "related";
   }

   public String getName()
   {
     return "相关文章";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\plugin\RelatedFieldPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */