 package com.enation.app.b2b2c.core.action.api.store;

 import com.enation.app.b2b2c.core.service.store.IStoreSildeManager;
 import com.enation.framework.action.WWAction;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.stereotype.Component;









 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("storeSilde")
 public class StoreSildeApiAction
   extends WWAction
 {
   private IStoreSildeManager storeSildeManager;
   private String[] silde_url;
   private String[] store_fs;
   private Integer[] silde_id;

   public String editStoreSilde()
   {
     try
     {
       this.storeSildeManager.edit(this.silde_id, this.store_fs, this.silde_url);
       showSuccessJson("修改成功");
     } catch (Exception e) {
       showErrorJson("修改失败");
     }
     return "json_message";
   }

   public IStoreSildeManager getStoreSildeManager() { return this.storeSildeManager; }

   public void setStoreSildeManager(IStoreSildeManager storeSildeManager) {
     this.storeSildeManager = storeSildeManager;
   }

   public String[] getSilde_url() { return this.silde_url; }

   public void setSilde_url(String[] silde_url) {
     this.silde_url = silde_url;
   }

   public String[] getStore_fs() { return this.store_fs; }

   public void setStore_fs(String[] store_fs) {
     this.store_fs = store_fs;
   }

   public Integer[] getSilde_id() { return this.silde_id; }

   public void setSilde_id(Integer[] silde_id) {
     this.silde_id = silde_id;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\store\StoreSildeApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */