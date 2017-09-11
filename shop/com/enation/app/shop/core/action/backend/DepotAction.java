 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.model.Depot;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;












 @Component
 @Scope("prototype")
 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Action("depot")
 @Results({@org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/shop/admin/depot/add.html"), @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/shop/admin/depot/edit.html"), @org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/shop/admin/depot/list.html")})
 public class DepotAction
   extends WWAction
 {
   private IDepotManager depotManager;
   private Depot room;
   private int id;
   private List depotList;

   public String list()
   {
     return "list";
   }





   public String listJson()
   {
     this.depotList = this.depotManager.list();
     showGridJson(this.depotList);
     return "json_message";
   }



   public String add()
   {
     return "add";
   }





   public String edit()
   {
     this.room = this.depotManager.get(this.id);
     return "edit";
   }




   public String saveAdd()
   {
     try
     {
       this.depotManager.add(this.room);
       showSuccessJson("仓库新增成功");
     } catch (Exception e) {
       showErrorJson("仓库新增失败" + e.getMessage());
       this.logger.error("仓库新增失败", e);
     }
     return "json_message";
   }




   public String saveEdit()
   {
     try
     {
       this.depotManager.update(this.room);
       showSuccessJson("修改仓库成功");
     } catch (Exception e) {
       showErrorJson("修改仓库失败");
       this.logger.error("修改仓库失败", e);
     }
     return "json_message";
   }





   public String delete()
   {
     try
     {
       String message = this.depotManager.delete(this.id);
       if (message.equals("删除成功")) {
         showSuccessJson(message);
       } else {
         showErrorJson(message);
       }
     } catch (Exception e) {
       showErrorJson("仓库删除失败");
       this.logger.error("仓库删除失败", e);
     }
     return "json_message";
   }

   public IDepotManager getDepotManager() {
     return this.depotManager;
   }

   public void setDepotManager(IDepotManager depotManager) {
     this.depotManager = depotManager;
   }

   public Depot getRoom() {
     return this.room;
   }

   public void setRoom(Depot room) {
     this.room = room;
   }

   public int getId() {
     return this.id;
   }

   public void setId(int id) {
     this.id = id;
   }

   public List getDepotList() {
     return this.depotList;
   }

   public void setDepotList(List depotList) {
     this.depotList = depotList;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\DepotAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */