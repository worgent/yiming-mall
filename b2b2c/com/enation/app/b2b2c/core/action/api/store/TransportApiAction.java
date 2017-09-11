 package com.enation.app.b2b2c.core.action.api.store;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreTemplateManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.action.WWAction;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.stereotype.Component;





 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("transport")
 public class TransportApiAction
   extends WWAction
 {
   private IStoreTemplateManager storeTemplateManager;
   private IStoreMemberManager storeMemberManager;
   private Integer tempid;

   public String del()
   {
     try
     {
       this.storeTemplateManager.delete(this.tempid);
       showSuccessJson("删除成功！");
     } catch (RuntimeException e) {
       showErrorJson(e.getMessage());
     } catch (Exception e) {
       showErrorJson("删除失败！");
     }
     return "json_message";
   }





   public String setDefTemp()
   {
     try
     {
       StoreMember member = this.storeMemberManager.getStoreMember();
       this.storeTemplateManager.setDefTemp(this.tempid, member.getStore_id());
       showSuccessJson("设置成功！");
     } catch (Exception e) {
       showErrorJson("设置失败，请稍后重试！");
     }
     return "json_message";
   }

   public IStoreTemplateManager getStoreTemplateManager() {
     return this.storeTemplateManager;
   }

   public void setStoreTemplateManager(IStoreTemplateManager storeTemplateManager) {
     this.storeTemplateManager = storeTemplateManager;
   }

   public IStoreMemberManager getStoreMemberManager()
   {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }

   public Integer getTempid() {
     return this.tempid;
   }

   public void setTempid(Integer tempid) {
     this.tempid = tempid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\store\TransportApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */