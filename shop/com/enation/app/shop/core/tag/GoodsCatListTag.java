 package com.enation.app.shop.core.tag;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;













 @Component
 @Scope("prototype")
 public class GoodsCatListTag
   extends BaseFreeMarkerTag
 {
   private IGoodsCatManager goodsCatManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer catid = (Integer)params.get("catid");
     if (catid == null) {
       catid = Integer.valueOf(0);
     }
     List<Cat> catList = this.goodsCatManager.listAllChildren(catid);
     return catList;
   }

   public IGoodsCatManager getGoodsCatManager() {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) { this.goodsCatManager = goodsCatManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\GoodsCatListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */