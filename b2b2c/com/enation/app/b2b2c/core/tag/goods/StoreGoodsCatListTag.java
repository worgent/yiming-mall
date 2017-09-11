 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsCatManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;












 @Component
 public class StoreGoodsCatListTag
   extends BaseFreeMarkerTag
 {
   private IStoreGoodsCatManager storeGoodsCatManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer storeid = (Integer)params.get("storeid");
     if ((storeid == null) || (storeid.intValue() == 0)) {
       StoreMember storeMember = this.storeMemberManager.getStoreMember();
       storeid = storeMember.getStore_id();
     }
     Integer type = (Integer)params.get("type");
     Integer catid = (Integer)params.get("catid");
     List list = new ArrayList();
     if ((type != null) && (type.intValue() == 1)) {
       list = this.storeGoodsCatManager.getStoreCatList(catid, storeid);
     } else {
       list = this.storeGoodsCatManager.storeCatList(storeid);
     }
     return list;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager)
   {
     this.storeMemberManager = storeMemberManager;
   }

   public IStoreGoodsCatManager getStoreGoodsCatManager() {
     return this.storeGoodsCatManager;
   }

   public void setStoreGoodsCatManager(IStoreGoodsCatManager storeGoodsCatManager) {
     this.storeGoodsCatManager = storeGoodsCatManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\StoreGoodsCatListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */