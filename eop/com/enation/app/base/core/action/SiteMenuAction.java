 package com.enation.app.base.core.action;

 import com.enation.app.base.core.model.SiteMenu;
 import com.enation.app.base.core.service.ISiteMenuManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
 import java.util.List;








 public class SiteMenuAction
   extends WWAction
 {
   private ISiteMenuManager siteMenuManager;
   private List menuList;
   private Integer[] sortArray;
   private Integer[] menuidArray;
   private Integer menuid;
   private SiteMenu siteMenu;
   private boolean isEdit;

   public String list() { return "list"; }

   public String listJson() {
     this.menuList = this.siteMenuManager.list(Integer.valueOf(0));
     showGridJson(this.menuList);
     return "json_message";
   }

   public String updateSort() {
     try {
       this.siteMenuManager.updateSort(this.menuidArray, this.sortArray);
       showSuccessJson("保存排序成功");
     } catch (Exception e) {
       e.printStackTrace();
       showErrorJson("保存排序失败:" + e.getMessage());
     }
     return "json_message";
   }

   public String add()
   {
     this.isEdit = false;
     this.menuList = this.siteMenuManager.list(Integer.valueOf(0));
     this.siteMenu = new SiteMenu();
     return "input";
   }

   public String addchildren() { this.isEdit = false;
     this.menuList = this.siteMenuManager.list(Integer.valueOf(0));
     this.menuid = this.siteMenuManager.get(this.menuid).getMenuid();
     this.siteMenu = new SiteMenu();
     return "input";
   }

   public String edit() {
     this.isEdit = true;
     this.menuList = this.siteMenuManager.list(Integer.valueOf(0));
     this.siteMenu = this.siteMenuManager.get(this.menuid);
     return "input";
   }

   public String save()
   {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能添加这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }

     if (this.menuid == null) {
       this.siteMenuManager.add(this.siteMenu);
       showSuccessJson("菜单添加成功");
     } else {
       this.siteMenu.setMenuid(this.menuid);
       this.siteMenuManager.edit(this.siteMenu);
       showSuccessJson("菜单修改成功");
     }
     return "json_message";
   }

   public String delete() {
     if ((EopSetting.IS_DEMO_SITE) &&
       (this.menuid.intValue() <= 21)) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try
     {
       this.siteMenuManager.delete(this.menuid);
       showSuccessJson("删除成功");
     } catch (Exception e) {
       showErrorJson("删除失败:" + e.getMessage());
     }
     return "json_message";
   }

   public ISiteMenuManager getSiteMenuManager() {
     return this.siteMenuManager;
   }

   public void setSiteMenuManager(ISiteMenuManager siteMenuManager) { this.siteMenuManager = siteMenuManager; }

   public List getMenuList() {
     return this.menuList;
   }

   public void setMenuList(List menuList) { this.menuList = menuList; }

   public Integer[] getSortArray()
   {
     return this.sortArray;
   }

   public void setSortArray(Integer[] sortArray) {
     this.sortArray = sortArray;
   }

   public Integer[] getMenuidArray() {
     return this.menuidArray;
   }

   public void setMenuidArray(Integer[] menuidArray) {
     this.menuidArray = menuidArray;
   }

   public Integer getMenuid() {
     return this.menuid;
   }

   public void setMenuid(Integer menuid) {
     this.menuid = menuid;
   }

   public SiteMenu getSiteMenu() {
     return this.siteMenu;
   }

   public void setSiteMenu(SiteMenu siteMenu) {
     this.siteMenu = siteMenu;
   }

   public boolean getIsEdit() {
     return this.isEdit;
   }

   public void setEdit(boolean isEdit) {
     this.isEdit = isEdit;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\SiteMenuAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */