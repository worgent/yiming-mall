 package com.enation.app.base.component.widget.menu;

 import com.enation.app.base.core.service.ISiteMenuManager;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;





 @Component("site_menu")
 @Scope("prototype")
 public class MenuWidget
   extends AbstractWidget
 {
   private ISiteMenuManager siteMenuManager;

   protected void display(Map<String, String> params)
   {
     List menuList = this.siteMenuManager.list(Integer.valueOf(0));
     putData("menuList", menuList);

     String isDropDown = (String)params.get("isDropDown");
     isDropDown = StringUtil.isEmpty(isDropDown) ? "off" : "on";
     putData("isDropDown", isDropDown);
   }

   protected void config(Map<String, String> params)
   {
     setPageName("menu_config");
   }

   public ISiteMenuManager getSiteMenuManager() { return this.siteMenuManager; }

   public void setSiteMenuManager(ISiteMenuManager siteMenuManager) {
     this.siteMenuManager = siteMenuManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\menu\MenuWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */