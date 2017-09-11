 package com.enation.app.base.core.plugin.setting;

 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.plugin.AutoRegisterPluginsBundle;
 import com.enation.framework.plugin.IPlugin;
 import java.util.List;
 import java.util.Map;
 import java.util.TreeMap;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;












 public class SettingPluginBundle
   extends AutoRegisterPluginsBundle
 {
   protected static final Logger logger = Logger.getLogger(SettingPluginBundle.class);




   public String getName()
   {
     return "系统设置插件桩";
   }



   public void registerPlugin(IPlugin plugin)
   {
     super.registerPlugin(plugin);
   }

   public Map<Integer, String> onInputShow(Map<String, Map<String, String>> settings)
   {
     Map<Integer, String> htmlMap = new TreeMap();

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     List<IPlugin> plugins = getPlugins();

     if (plugins != null) {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof IOnSettingInputShow)) {
           IOnSettingInputShow event = (IOnSettingInputShow)plugin;
           String groupname = event.getSettingGroupName();
           String pageName = event.onShow();

           freeMarkerPaser.setClz(event.getClass());
           freeMarkerPaser.setPageName(pageName);
           freeMarkerPaser.putData(groupname, settings.get(groupname));
           htmlMap.put(Integer.valueOf(event.getOrder()), freeMarkerPaser.proessPageContent());
         }
       }
     }

     return htmlMap;
   }



   public Map<Integer, String> getTabs()
   {
     Map<Integer, String> tabMap = new TreeMap();
     List<IPlugin> plugins = getPlugins();

     if (plugins != null) {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof IOnSettingInputShow)) {
           IOnSettingInputShow event = (IOnSettingInputShow)plugin;
           String name = event.getTabName();
           tabMap.put(Integer.valueOf(event.getOrder()), name);
         }
       }
     }

     return tabMap;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\plugin\setting\SettingPluginBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */