 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.model.store.StoreCat;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsCatManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import org.apache.log4j.Logger;
 import org.springframework.stereotype.Component;




 @Component
 public class StoreCatTag
   extends BaseFreeMarkerTag
 {
   private IStoreGoodsCatManager storeGoodsCatManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer catid = (Integer)params.get("catid");
     Map map = new HashMap();
     StoreCat storeCat = new StoreCat();
     try {
       StoreMember storeMember = this.storeMemberManager.getStoreMember();
       map.put("storeid", storeMember.getStore_id());
       map.put("store_catid", catid);
       storeCat = this.storeGoodsCatManager.getStoreCat(map);
     }
     catch (Exception e) {
       this.logger.error("商品分类查询错误", e);
     }
     return storeCat;
   }

   public IStoreGoodsCatManager getStoreGoodsCatManager() {
     return this.storeGoodsCatManager;
   }

   public void setStoreGoodsCatManager(IStoreGoodsCatManager storeGoodsCatManager) {
     this.storeGoodsCatManager = storeGoodsCatManager;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\StoreCatTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */