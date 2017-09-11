 package com.enation.app.base.core.action;

 import com.enation.app.base.core.model.Role;
 import com.enation.app.base.core.service.auth.IAuthActionManager;
 import com.enation.app.base.core.service.auth.IRoleManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import org.apache.log4j.Logger;








 public class RoleAction
   extends WWAction
 {
   private IRoleManager roleManager;
   private IAuthActionManager authActionManager;
   private List roleList;
   private List authList;
   private int roleid;
   private Role role;
   private int[] acts;
   private int isEdit;

   public String list()
   {
     this.roleList = this.roleManager.list();
     return "list";
   }

   public String listJson() { this.roleList = this.roleManager.list();
     showGridJson(this.roleList);
     return "json_message";
   }


   public String add()
   {
     this.authList = this.authActionManager.list();
     return "add";
   }


   public String edit()
   {
     this.authList = this.authActionManager.list();
     this.isEdit = 1;
     this.role = this.roleManager.get(this.roleid);
     return "edit";
   }



   public String saveAdd()
   {
     try
     {
       this.roleManager.add(this.role, this.acts);
       showSuccessJson("新增角色成功");
     } catch (Exception e) {
       showErrorJson("新增角色失败");
       this.logger.error("新增角色失败", e);
     }
     return "json_message";
   }

   public String saveEdit()
   {
     try
     {
       this.roleManager.edit(this.role, this.acts);
       showSuccessJson("角色修改成功");
     } catch (Exception e) {
       showErrorJson("角色修改失败");
       this.logger.error("角色修改失败", e);
     }
     return "json_message";
   }


   public String delete()
   {
     if ((EopSetting.IS_DEMO_SITE) &&
       (this.roleid <= 5)) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }

     try
     {
       this.roleManager.delete(this.roleid);
       showSuccessJson("角色删除成功");
     } catch (Exception e) {
       showErrorJson("角色删除失败");
       this.logger.error("角色删除失败", e);
     }
     return "json_message";
   }

   public IRoleManager getRoleManager()
   {
     return this.roleManager;
   }

   public void setRoleManager(IRoleManager roleManager) { this.roleManager = roleManager; }



   public IAuthActionManager getAuthActionManager()
   {
     return this.authActionManager;
   }


   public void setAuthActionManager(IAuthActionManager authActionManager)
   {
     this.authActionManager = authActionManager;
   }


   public List getRoleList()
   {
     return this.roleList;
   }


   public void setRoleList(List roleList)
   {
     this.roleList = roleList;
   }


   public List getAuthList()
   {
     return this.authList;
   }


   public void setAuthList(List authList)
   {
     this.authList = authList;
   }


   public int getRoleid()
   {
     return this.roleid;
   }


   public void setRoleid(int roleid)
   {
     this.roleid = roleid;
   }


   public Role getRole()
   {
     return this.role;
   }

   public void setRole(Role role) {
     this.role = role;
   }

   public int[] getActs() {
     return this.acts;
   }

   public void setActs(int[] acts) { this.acts = acts; }

   public int getIsEdit()
   {
     return this.isEdit;
   }

   public void setIsEdit(int isEdit) {
     this.isEdit = isEdit;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\RoleAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */