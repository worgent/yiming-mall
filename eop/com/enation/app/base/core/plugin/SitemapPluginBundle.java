 package com.enation.app.base.core.plugin;

 import com.enation.framework.plugin.AutoRegisterPluginsBundle;
 import com.enation.framework.plugin.IPlugin;
 import java.util.List;

 public class SitemapPluginBundle extends AutoRegisterPluginsBundle
 {
   public String getName()
   {
     return "站点地图插件桩";
   }



   public void onRecreateMap()
   {
     List<IPlugin> plugins = getPlugins();

     if (plugins != null) {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof IRecreateMapEvent)) {
           IRecreateMapEvent event = (IRecreateMapEvent)plugin;
           event.onRecreateMap();
         }
       }
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\plugin\SitemapPluginBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */