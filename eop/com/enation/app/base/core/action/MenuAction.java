 package com.enation.app.base.core.action;

 import com.enation.app.base.core.model.AuthAction;
 import com.enation.app.base.core.service.auth.IAuthActionManager;
 import com.enation.eop.resource.IMenuManager;
 import com.enation.eop.resource.model.Menu;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.JsonMessageUtil;
 import java.io.File;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.log4j.Logger;


 public class MenuAction
   extends WWAction
 {
   private IMenuManager menuManager;
   private List<Menu> menuList;
   private Menu menu;
   private Integer parentid;
   private Integer id;
   private Integer[] menu_ids;
   private Integer[] menu_sorts;
   private List iconList;
   private int targetid;
   private String movetype;
   private int authid;
   private IAuthActionManager authActionManager;

   public String tree()
   {
     return "tree";
   }

   public String listIcon()
   {
     this.iconList = new ArrayList();

     String dirpath = EopSetting.IMG_SERVER_PATH + "/images/menuicon";
     File dir = new File(dirpath);
     File[] files = dir.listFiles();
     for (File iconFile : files)
     {
       this.iconList.add(iconFile.getName());
     }


     return "icon_list";
   }

   public String move()
   {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try
     {
       this.menuManager.move(this.id.intValue(), this.targetid, this.movetype);
       showSuccessJson("移动成功");
     } catch (Throwable e) {
       this.logger.error("move menu", e);
       showErrorJson("移动菜单出错" + e.getMessage());
     }
     return "json_message";
   }

   public String json() {
     StringBuffer data = new StringBuffer();
     data.append("[");
     this.menuList = this.menuManager.getMenuTree(Integer.valueOf(0));
     int i = 0;
     for (Menu menu : this.menuList) {
       if (i != 0) {
         data.append(",");
       }
       data.append(toJson(menu));
       i++;
     }
     data.append("]");
     this.json = data.toString();
     return "json_message";
   }

   private String toJson(Menu menu)
   {
     StringBuffer data = new StringBuffer();
     data.append("{\"menuid\":" + menu.getId() + ", \"name\":\"" + menu.getTitle() + "\",\"isParent\":" + menu.getHasChildren());
     if (menu.getHasChildren()) {
       data.append(",\"children\":[");
       int i = 0;
       List<Menu> menuList = menu.getChildren();
       for (Menu child : menuList) {
         if (i != 0) {
           data.append(",");
         }
         data.append(toJson(child));
         i++;
       }
       data.append("]");
     }
     data.append("} ");
     return data.toString();
   }

   public String list() {
     this.menuList = this.menuManager.getMenuTree(Integer.valueOf(0));
     return "list";
   }

   public String add() {
     this.menuList = this.menuManager.getMenuTree(Integer.valueOf(0));
     return "add";
   }

   public String saveAdd()
   {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能添加这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try
     {
       this.id = this.menuManager.add(this.menu);

       this.json = JsonMessageUtil.getNumberJson("menuid", this.id);
     } catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }

   public String edit()
   {
     this.menuList = this.menuManager.getMenuTree(Integer.valueOf(0));
     this.menu = this.menuManager.get(this.id);
     return "edit";
   }

   public String saveEdit()
   {
     try {
       this.menuManager.edit(this.menu);
       showSuccessJson("保存成功");
     } catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }


   public String updateSort()
   {
     try
     {
       this.menuManager.updateSort(this.menu_ids, this.menu_sorts);
       this.json = "{result:1}";
     } catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e);
       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
     }
     return "json_message";
   }

   public String delete()
   {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能删除这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try
     {
       this.menuManager.delete(this.id);
       showSuccessJson("删除成功");
       this.json = "{result:1}";
     } catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e);
       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
     }
     return "json_message";
   }

   public String getMenuJson() { StringBuffer data = new StringBuffer();
     data.append("[");
     this.menuList = this.menuManager.getMenuTree(Integer.valueOf(0));
     AuthAction authAction = null;
     if (this.authid != 0)
       authAction = this.authActionManager.get(this.authid);
     int i = 0;
     for (Menu menu : this.menuList) {
       if (i != 0) {
         data.append(",");
       }
       data.append(menutoJson(menu, authAction));
       i++;
     }
     data.append("]");
     this.json = data.toString();
     return "json_message";
   }

   private String menutoJson(Menu menu, AuthAction authAction) { StringBuffer data = new StringBuffer();
     data.append("{\"id\":" + menu.getId() + ", \"text\":\"" + menu.getTitle() + "\"");
     if (authAction != null) {
       String[] menuids = authAction.getObjvalue().split(",");
       if (authAction != null) {
         for (int i = 0; i < menuids.length; i++) {
           if (Integer.parseInt(menuids[i]) == menu.getId().intValue()) {
             data.append(",\"checked\":true");
           }
         }
       }
     }
     if (menu.getHasChildren()) {
       data.append(",\"children\":[");
       int i = 0;
       List<Menu> menuList = menu.getChildren();
       for (Menu child : menuList) {
         if (i != 0) {
           data.append(",");
         }
         if (authAction != null) {
           data.append(menutoJson(child, authAction));
         } else {
           data.append(menutoJson(child, null));
         }
         i++;
       }
       data.append("]");
     }
     data.append("} ");
     return data.toString();
   }

   public IMenuManager getMenuManager() {
     return this.menuManager;
   }

   public void setMenuManager(IMenuManager menuManager) {
     this.menuManager = menuManager;
   }

   public List<Menu> getMenuList() {
     return this.menuList;
   }

   public void setMenuList(List<Menu> menuList) {
     this.menuList = menuList;
   }

   public Integer getParentid() {
     return this.parentid;
   }

   public void setParentid(Integer parentid) {
     this.parentid = parentid;
   }

   public Integer getId() {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public Menu getMenu() {
     return this.menu;
   }

   public void setMenu(Menu menu) {
     this.menu = menu;
   }

   public Integer[] getMenu_ids() {
     return this.menu_ids;
   }

   public void setMenu_ids(Integer[] menuIds) {
     this.menu_ids = menuIds;
   }

   public Integer[] getMenu_sorts() {
     return this.menu_sorts;
   }

   public void setMenu_sorts(Integer[] menuSorts) {
     this.menu_sorts = menuSorts;
   }

   public List getIconList()
   {
     return this.iconList;
   }

   public void setIconList(List iconList)
   {
     this.iconList = iconList;
   }

   public int getTargetid()
   {
     return this.targetid;
   }

   public void setTargetid(int targetid)
   {
     this.targetid = targetid;
   }

   public String getMovetype()
   {
     return this.movetype;
   }

   public void setMovetype(String movetype)
   {
     this.movetype = movetype;
   }

   public int getAuthid()
   {
     return this.authid;
   }

   public void setAuthid(int authid)
   {
     this.authid = authid;
   }

   public IAuthActionManager getAuthActionManager()
   {
     return this.authActionManager;
   }

   public void setAuthActionManager(IAuthActionManager authActionManager)
   {
     this.authActionManager = authActionManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\MenuAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */