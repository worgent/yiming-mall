 package com.enation.app.base.core.plugin.user;

 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.framework.plugin.AutoRegisterPluginsBundle;
 import com.enation.framework.plugin.IPlugin;
 import java.util.ArrayList;
 import java.util.List;







 public class AdminUserPluginBundle
   extends AutoRegisterPluginsBundle
 {
   public String getName()
   {
     return "管理员插件桩";
   }






   public List<String> getInputHtml(AdminUser user)
   {
     List<String> list = new ArrayList();
     List<IPlugin> plugins = getPlugins();
     FreeMarkerPaser freeMarkerPaser;
     if (plugins != null) {
       freeMarkerPaser = FreeMarkerPaser.getInstance();

       for (IPlugin plugin : plugins) {
         if ((plugin instanceof IAdminUserInputDisplayEvent)) {
           IAdminUserInputDisplayEvent event = (IAdminUserInputDisplayEvent)plugin;
           freeMarkerPaser.setClz(event.getClass());
           String html = event.getInputHtml(user);
           list.add(html);
         }
       }
     }

     return list;
   }






   public void onAdd(Integer userid)
   {
     List<IPlugin> plugins = getPlugins();

     if (plugins != null)
     {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof IAdminUserOnAddEvent)) {
           IAdminUserOnAddEvent event = (IAdminUserOnAddEvent)plugin;
           event.onAdd(userid);
         }
       }
     }
   }







   public void onEdit(Integer userid)
   {
     List<IPlugin> plugins = getPlugins();

     if (plugins != null)
     {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof IAdminUserOnEditEvent)) {
           IAdminUserOnEditEvent event = (IAdminUserOnEditEvent)plugin;
           event.onEdit(userid);
         }
       }
     }
   }






   public void onDelete(Integer userid)
   {
     List<IPlugin> plugins = getPlugins();

     if (plugins != null)
     {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof IAdminUserDeleteEvent)) {
           IAdminUserDeleteEvent event = (IAdminUserDeleteEvent)plugin;
           event.onDelete(userid.intValue());
         }
       }
     }
   }


   public void onLogin(AdminUser user)
   {
     List<IPlugin> plugins = getPlugins();

     if (plugins != null)
     {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof IAdminUserLoginEvent)) {
           IAdminUserLoginEvent event = (IAdminUserLoginEvent)plugin;
           event.onLogin(user);
         }
       }
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\plugin\\user\AdminUserPluginBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */