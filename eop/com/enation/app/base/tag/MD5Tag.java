 package com.enation.app.base.tag;

 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.Map;


 public class MD5Tag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     String str = (String)params.get("str");
     if (str == null) throw new TemplateModelException("必须传入str参数");
     str = StringUtil.md5(str);
     return str;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\tag\MD5Tag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */