 package com.enation.app.base.tag;

 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.util.FileUtil;
 import freemarker.core.Environment;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.util.Map;


 public class DemoFileReadDirectiveModel
   implements TemplateDirectiveModel
 {
   public void execute(Environment arg0, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
     throws TemplateException, IOException
   {
     String filename = (String)params.get("filename");
     String filePath = EopSetting.EOP_PATH + "/docs/tags/demo/" + filename;
     String content = FileUtil.read(filePath, "UTF-8");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\tag\DemoFileReadDirectiveModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */