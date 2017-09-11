 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.shop.component.spec.service.ISpecManager;
 import com.enation.app.shop.core.model.Specification;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;





 @Component
 public class StoreGoodsSpecTag
   extends BaseFreeMarkerTag
 {
   private ISpecManager specManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     List<Specification> specList = this.specManager.listSpecAndValue();
     return specList;
   }

   public ISpecManager getSpecManager() { return this.specManager; }

   public void setSpecManager(ISpecManager specManager) {
     this.specManager = specManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\StoreGoodsSpecTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */