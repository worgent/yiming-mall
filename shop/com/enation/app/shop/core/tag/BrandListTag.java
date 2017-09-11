 package com.enation.app.shop.core.tag;

 import com.enation.app.shop.core.model.Brand;
 import com.enation.app.shop.core.service.IBrandManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;










 @Component
 @Scope("prototype")
 public class BrandListTag
   extends BaseFreeMarkerTag
 {
   private IBrandManager brandManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     List<Brand> brandList = this.brandManager.list();
     return brandList;
   }

   public IBrandManager getBrandManager() {
     return this.brandManager;
   }

   public void setBrandManager(IBrandManager brandManager) { this.brandManager = brandManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\BrandListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */