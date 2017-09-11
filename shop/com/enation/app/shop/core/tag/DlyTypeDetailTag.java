 package com.enation.app.shop.core.tag;

 import com.enation.app.shop.core.model.DlyType;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;














 @Component
 @Scope("prototype")
 public class DlyTypeDetailTag
   extends BaseFreeMarkerTag
 {
   private IDlyTypeManager dlyTypeManager;

   public Object exec(Map args)
     throws TemplateModelException
   {
     Integer type_id = (Integer)args.get("id");

     if (type_id == null) throw new TemplateModelException("必须传递配送方式id参数");
     DlyType dlyType = this.dlyTypeManager.getDlyTypeById(type_id);

     return dlyType;
   }

   public IDlyTypeManager getDlyTypeManager() { return this.dlyTypeManager; }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
     this.dlyTypeManager = dlyTypeManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\DlyTypeDetailTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */