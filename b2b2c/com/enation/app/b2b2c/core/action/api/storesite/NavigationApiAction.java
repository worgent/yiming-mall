 package com.enation.app.b2b2c.core.action.api.storesite;

 import com.enation.app.b2b2c.core.model.Navigation;
 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.INavigationManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.action.WWAction;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.stereotype.Component;




 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("navigation")
 @Results({@org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/themes/default/b2b2c/storesite/navication_edit.html")})
 public class NavigationApiAction
   extends WWAction
 {
   private INavigationManager navigationManager;
   private IStoreMemberManager storeMemberManager;
   private String name;
   private Integer disable;
   private Integer sorts;
   private String contents;
   private String nav_url;
   private Integer target;
   private Integer nav_id;
   private Navigation navication;

   public String getList()
   {
     return null;
   }

   public String add() {
     StoreMember storeMember = this.storeMemberManager.getStoreMember();

     Navigation navigation = new Navigation();
     navigation.setName(this.name);
     navigation.setDisable(this.disable);
     navigation.setSort(this.sorts);
     navigation.setContents(this.contents);
     navigation.setNav_url(this.nav_url);
     navigation.setTarget(this.target);
     navigation.setStore_id(storeMember.getStore_id());
     try
     {
       this.navigationManager.save(navigation);
       showSuccessJson("添加成功");
     } catch (Exception e) {
       showErrorJson("添加失败");
     }
     return "json_message";
   }

   public String getNavcation() {
     this.navication = this.navigationManager.getNavication(this.nav_id);
     return "edit";
   }

   public String edit() {
     StoreMember storeMember = this.storeMemberManager.getStoreMember();

     Navigation navigation = new Navigation();
     navigation.setName(this.name);
     navigation.setDisable(this.disable);
     navigation.setSort(this.sorts);
     navigation.setContents(this.contents);
     navigation.setNav_url(this.nav_url);
     navigation.setTarget(this.target);
     navigation.setId(this.nav_id);
     navigation.setStore_id(storeMember.getStore_id());
     try
     {
       this.navigationManager.edit(navigation);
       showSuccessJson("添加成功");
     } catch (Exception e) {
       showErrorJson("添加失败");
     }
     return "json_message";
   }

   public String delete() {
     try {
       this.navigationManager.delete(this.nav_id);
       showSuccessJson("删除成功");
     } catch (Exception e) {
       showErrorJson("删除失败");
     }
     return "json_message";
   }



   public String getName()
   {
     return this.name;
   }

   public INavigationManager getNavigationManager() {
     return this.navigationManager;
   }

   public void setNavigationManager(INavigationManager navigationManager) {
     this.navigationManager = navigationManager;
   }

   public void setName(String name) {
     this.name = name;
   }

   public Integer getDisable() {
     return this.disable;
   }

   public void setDisable(Integer disable) {
     this.disable = disable;
   }

   public Integer getSorts()
   {
     return this.sorts;
   }

   public void setSorts(Integer sorts) {
     this.sorts = sorts;
   }

   public String getContents() {
     return this.contents;
   }

   public void setContents(String contents) {
     this.contents = contents;
   }

   public String getNav_url() {
     return this.nav_url;
   }

   public void setNav_url(String nav_url) {
     this.nav_url = nav_url;
   }

   public Integer getTarget() {
     return this.target;
   }

   public void setTarget(Integer target) {
     this.target = target;
   }

   public Integer getNav_id() {
     return this.nav_id;
   }

   public void setNav_id(Integer nav_id) {
     this.nav_id = nav_id;
   }

   public Navigation getNavication() {
     return this.navication;
   }

   public void setNavication(Navigation navication) {
     this.navication = navication;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\storesite\NavigationApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */