 package com.enation.framework.directive;

 import com.enation.framework.resource.Resource;
 import com.enation.framework.resource.impl.ResourceBuilder;
 import freemarker.core.Environment;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.util.Map;

 public class CssDirectiveModel
   extends AbstractResourceDirectiveModel
   implements TemplateDirectiveModel
 {
   public void execute(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
     throws TemplateException, IOException
   {
     Resource resource = createResource(params);
     resource.setType("css");
     ResourceBuilder.putCss(resource);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\directive\CssDirectiveModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */