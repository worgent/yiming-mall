 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsTagManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;







 @Component
 public class StoreTagsListTag
   extends BaseFreeMarkerTag
 {
   private IStoreGoodsTagManager storeGoodsTagManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     List list = new ArrayList();
     if (params.get("type") == null) {
       StoreMember member = this.storeMemberManager.getStoreMember();
       list = this.storeGoodsTagManager.list(member.getStore_id());
     } else {
       list = this.storeGoodsTagManager.list(Integer.valueOf(Integer.parseInt(params.get("store_id").toString())));
     }
     return list;
   }

   public IStoreGoodsTagManager getStoreGoodsTagManager() { return this.storeGoodsTagManager; }

   public void setStoreGoodsTagManager(IStoreGoodsTagManager storeGoodsTagManager) {
     this.storeGoodsTagManager = storeGoodsTagManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\StoreTagsListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */