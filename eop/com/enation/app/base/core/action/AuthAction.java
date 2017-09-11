 package com.enation.app.base.core.action;

 import com.enation.app.base.core.service.auth.IAuthActionManager;
 import com.enation.framework.action.WWAction;
 import org.apache.log4j.Logger;




 public class AuthAction
   extends WWAction
 {
   private String name;
   private int authid;
   private int isEdit;
   private IAuthActionManager authActionManager;
   private String menuids;
   private com.enation.app.base.core.model.AuthAction auth;

   public String add()
   {
     this.isEdit = 0;
     return "input";
   }

   public String edit() {
     this.isEdit = 1;
     this.auth = this.authActionManager.get(this.authid);
     return "input";
   }

   public String saveEdit()
   {
     try {
       com.enation.app.base.core.model.AuthAction act = new com.enation.app.base.core.model.AuthAction();
       act.setName(this.name);
       act.setType("menu");
       act.setActid(Integer.valueOf(this.authid));

       act.setObjvalue(this.menuids);
       this.authActionManager.edit(act);
       showSuccessJson("修改成功");
     } catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e.fillInStackTrace());
       showErrorJson("修改失败:" + e.getMessage());
     }
     return "json_message";
   }

   public String saveAdd() {
     try {
       com.enation.app.base.core.model.AuthAction act = new com.enation.app.base.core.model.AuthAction();
       act.setName(this.name);
       act.setType("menu");

       act.setObjvalue(this.menuids);
       this.authid = this.authActionManager.add(act);
       showSuccessJson("添加成功");
     } catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e.fillInStackTrace());
       showErrorJson("添加失败:" + e.getMessage());
     }
     return "json_message";
   }

   public String delete() {
     try {
       com.enation.app.base.core.model.AuthAction authaction = this.authActionManager.get(this.authid);
       if (authaction.getChoose() == 0) {
         this.authActionManager.delete(this.authid);
         showSuccessJson("删除成功");
       }
       else {
         showErrorJson("此权限点为系统默认权限点，不能进行删除!");
       }
     } catch (RuntimeException e) {
       showErrorJson("删除失败:" + e.getMessage());
     }
     return "json_message";
   }

   public String list() { return "list"; }

   public String listJson() {
     showGridJson(this.authActionManager.list());
     return "json_message";
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public int getAuthid() {
     return this.authid;
   }

   public void setAuthid(int authid) {
     this.authid = authid;
   }

   public int getIsEdit() {
     return this.isEdit;
   }

   public void setIsEdit(int isEdit) {
     this.isEdit = isEdit;
   }

   public IAuthActionManager getAuthActionManager() {
     return this.authActionManager;
   }

   public void setAuthActionManager(IAuthActionManager authActionManager) {
     this.authActionManager = authActionManager;
   }

   public String getMenuids() {
     return this.menuids;
   }

   public void setMenuids(String menuids) {
     this.menuids = menuids;
   }

   public com.enation.app.base.core.model.AuthAction getAuth() {
     return this.auth;
   }

   public void setAuth(com.enation.app.base.core.model.AuthAction auth) {
     this.auth = auth;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\AuthAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */