 package com.enation.framework.directive;

 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.resource.Resource;
 import com.enation.framework.util.StringUtil;
 import java.util.Map;







 public abstract class AbstractResourceDirectiveModel
 {
   protected Resource createResource(Map params)
   {
     String src = params.get("src").toString();
     String compress = getValue(params, "compress");
     String merge = getValue(params, "merge");

     String iscommon = getValue(params, "iscommon");

     if (StringUtil.isEmpty(merge)) merge = "true";
     if (StringUtil.isEmpty(compress)) { compress = "true";
     }


     Resource resource = new Resource();
     resource.setSrc(src);
     resource.setMerge("true".equals(merge) ? 1 : 0);
     resource.setCompress("true".equals(compress) ? 1 : 0);


     resource.setIscommon("true".equals(iscommon));



     if (EopSetting.DEVELOPMENT_MODEL) {
       resource.setMerge(0);
       resource.setIscommon(false);
     }

     return resource;
   }

   protected String getValue(Map params, String name)
   {
     Object value_obj = params.get(name);
     if (value_obj == null) {
       return null;
     }

     return value_obj.toString();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\directive\AbstractResourceDirectiveModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */