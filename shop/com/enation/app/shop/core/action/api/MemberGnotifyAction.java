 package com.enation.app.shop.core.action.api;

 import com.enation.app.shop.core.service.IGnotifyManager;
 import com.enation.framework.action.WWAction;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.stereotype.Component;




 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("gnotify")
 public class MemberGnotifyAction
   extends WWAction
 {
   private IGnotifyManager gnotifyManager;
   private Integer gnotify_id;
   private Integer goodsid;

   public String gnotifyDel()
   {
     try
     {
       this.gnotifyManager.deleteGnotify(this.gnotify_id.intValue());
       showSuccessJson("删除成功");
     } catch (Exception e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.error(e.getStackTrace());
       }
       showErrorJson("删除失败[" + e.getMessage() + "]");
     }

     return "json_message";
   }

   public String add() {
     try {
       this.gnotifyManager.addGnotify(this.goodsid.intValue());
       showSuccessJson("登记成功");
     } catch (Exception e) {
       showErrorJson("登记失败，请重试");
     }

     return "json_message";
   }


   public IGnotifyManager getGnotifyManager()
   {
     return this.gnotifyManager;
   }

   public void setGnotifyManager(IGnotifyManager gnotifyManager) { this.gnotifyManager = gnotifyManager; }

   public Integer getGnotify_id() {
     return this.gnotify_id;
   }

   public void setGnotify_id(Integer gnotify_id) { this.gnotify_id = gnotify_id; }

   public Integer getGoodsid()
   {
     return this.goodsid;
   }

   public void setGoodsid(Integer goodsid) {
     this.goodsid = goodsid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\MemberGnotifyAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */