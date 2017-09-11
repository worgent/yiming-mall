 package com.enation.app.base.tag;

 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.FileUtil;
 import freemarker.template.TemplateModelException;
 import java.util.Map;

 public class DemoFileReadTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     String filename = (String)params.get("filename");
     if (filename == null) return "文件名不能为空";
     String filePath = EopSetting.EOP_PATH + "/docs/tags/demo/" + filename;
     String content = FileUtil.read(filePath, "UTF-8");
     return content;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\tag\DemoFileReadTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */