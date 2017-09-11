 package com.enation.eop.processor.backend.support;

 import com.enation.app.base.core.model.AuthAction;
 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.base.core.service.auth.IAuthActionManager;
 import com.enation.app.base.core.service.auth.IPermissionManager;
 import com.enation.app.base.core.service.auth.impl.PermissionConfig;
 import com.enation.eop.processor.AbstractFacadeProcessor;
 import com.enation.eop.processor.FacadePage;
 import com.enation.eop.processor.core.Response;
 import com.enation.eop.processor.core.StringResponse;
 import com.enation.eop.resource.IMenuManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.resource.model.Menu;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.util.ArrayList;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang3.StringUtils;

 public class MenuJsonGetter
   extends AbstractFacadeProcessor
 {
   public MenuJsonGetter(FacadePage page)
   {
     super(page);
   }

   protected Response process() {
     Response response = new StringResponse();
     String menu = getMenuJson();
     response.setContent(menu);
     return response;
   }

   public String getMenuJson() {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String showall = request.getParameter("showall");
     StringBuffer json = new StringBuffer();




     IMenuManager menuManager = (IMenuManager)SpringContextHolder.getBean("menuManager");
     List<Menu> tempMenuList = menuManager.getMenuList();
     List<Menu> menuList = new ArrayList();
     IPermissionManager permissionManager = (IPermissionManager)SpringContextHolder.getBean("permissionManager");
     IAdminUserManager adminUserManager = (IAdminUserManager)SpringContextHolder.getBean("adminUserManager");
     IAuthActionManager authActionManager = (IAuthActionManager)SpringContextHolder.getBean("authActionManager");
     AdminUser user = adminUserManager.getCurrentUser();
     user = adminUserManager.get(user.getUserid());
     List<AuthAction> authList = permissionManager.getUesrAct(user.getUserid().intValue(), "menu");

     for (Menu menu : tempMenuList) {
       if ((menu.getMenutype().intValue() == 2) &&
         (!"yes".equals(showall))) {
         if (user.getFounder() != 1) {
           if (checkPermssion(menu, authList)) {}
         }
         else
         {
           int superAdminAuthId = PermissionConfig.getAuthId("super_admin");
           AuthAction superAuth = authActionManager.get(superAdminAuthId);
           if ((superAuth != null) && (!checkPermssion(menu, superAuth))) {
             continue;
           }
         }
       }

       menuList.add(menu);
     }
     List<Menu> syslist = getMenuList(1, menuList);
     List<Menu> applist = getMenuList(2, menuList);
     List<Menu> extlist = getMenuList(3, menuList);

     json.append("var menu ={");
     json.append("'sys':[");
     json.append(toJson(syslist, menuList));
     json.append("]");

     json.append(",'app':[");
     json.append(toJson(applist, menuList));
     json.append("]");

     json.append(",'ext':[");
     json.append(toJson(extlist, menuList));
     json.append("]");
     json.append("};");

     json.append("var mainpage=true;");
     json.append("var domain='" + request.getServerName() + "';");
     json.append("var runmode=" + EopSetting.RUNMODE + ";");
     json.append("var app_path='" + request.getContextPath() + "';");

     return json.toString();
   }






   public String toJson(List<Menu> menuList, List<Menu> allList)
   {
     StringBuffer menuItem = new StringBuffer();
     int i = 0;
     for (Menu menu : menuList) {
       if (i != 0)
         menuItem.append(",");
       menuItem.append(toJson(menu, allList));
       i++;
     }
     return menuItem.toString();
   }

   private boolean checkPermssion(Menu menu, List<AuthAction> authList) {
     for (AuthAction auth : authList) {
       if (checkPermssion(menu, auth)) {
         return true;
       }
     }
     return false;
   }

   private boolean checkPermssion(Menu menu, AuthAction auth) {
     String values = auth.getObjvalue();
     if (values != null) {
       String[] value_ar = StringUtils.split(values, ",");
       for (String v : value_ar) {
         if (v.equals("" + menu.getId().intValue())) {
           return true;
         }
       }
     }
     return false;
   }






   private String toJson(Menu menu, List<Menu> menuList)
   {
     String title = menu.getTitle();
     String url = menu.getUrl();
     Integer selected = menu.getSelected();
     String type = menu.getDatatype();
     String target = menu.getTarget();

     if (!"_blank".equals(target)) {
       String ctx = EopSetting.CONTEXT_PATH;
       ctx = ctx.equals("/") ? "" : ctx;
       url = ctx + url;
     }
     StringBuffer menuItem = new StringBuffer();

     menuItem.append("{");
     menuItem.append("id:");
     menuItem.append(menu.getId());

     menuItem.append(",text:'");
     menuItem.append(title);
     menuItem.append("'");

     menuItem.append(",url:'");
     menuItem.append(url);
     menuItem.append("'");

     menuItem.append(",'default':");
     menuItem.append(selected);

     menuItem.append(",children:");
     menuItem.append(getChildrenJson(menu.getId(), menuList));

     menuItem.append(",type:'");
     menuItem.append(type);
     menuItem.append("'");

     menuItem.append(",target:'");
     menuItem.append(target);
     menuItem.append("'");

     menuItem.append("}");

     return menuItem.toString();
   }







   public List<Menu> getMenuList(int menuType, List<Menu> menuList)
   {
     List<Menu> mlist = new ArrayList();

     for (Menu menu : menuList) {
       if ((menu.getMenutype().intValue() == menuType) && (menu.getPid().intValue() == 0))
       {
         mlist.add(menu);
       }
     }
     return mlist;
   }







   private String getChildrenJson(Integer menuId, List<Menu> menuList)
   {
     StringBuffer json = new StringBuffer();
     json.append("[");
     int i = 0;
     for (Menu menu : menuList) {
       if (menuId.intValue() == menu.getPid().intValue()) {
         if (i != 0)
           json.append(",");
         json.append(toJson(menu, menuList));
         i++;
       }
     }
     json.append("]");
     return json.toString();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\backend\support\MenuJsonGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */