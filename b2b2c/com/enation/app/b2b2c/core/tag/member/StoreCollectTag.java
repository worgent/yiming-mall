 package com.enation.app.b2b2c.core.tag.member;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreCollectManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import org.springframework.stereotype.Component;











 @Component
 public class StoreCollectTag
   extends BaseFreeMarkerTag
 {
   private IStoreCollectManager storeCollectManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     StoreMember storeMember = this.storeMemberManager.getStoreMember();
     Integer memberid = storeMember.getMember_id();
     Page webpage = this.storeCollectManager.getList(memberid, getPage(), getPageSize());

     Map result = new HashMap();

     Long totalCount = Long.valueOf(webpage.getTotalCount());
     result.put("page", Integer.valueOf(getPage()));
     result.put("pageSize", Integer.valueOf(getPageSize()));
     result.put("totalCount", totalCount);
     result.put("storelist", webpage);
     return result;
   }

   public IStoreCollectManager getStoreCollectManager() {
     return this.storeCollectManager;
   }

   public void setStoreCollectManager(IStoreCollectManager storeCollectManager) { this.storeCollectManager = storeCollectManager; }

   public IStoreMemberManager getStoreMemberManager()
   {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\member\StoreCollectTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */