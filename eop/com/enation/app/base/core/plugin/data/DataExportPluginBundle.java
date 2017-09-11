 package com.enation.app.base.core.plugin.data;

 import com.enation.framework.plugin.AutoRegisterPluginsBundle;
 import com.enation.framework.plugin.IPlugin;
 import java.util.List;









 public class DataExportPluginBundle
   extends AutoRegisterPluginsBundle
 {
   public String getName()
   {
     return "数据导出插件桩";
   }









   public String exportData()
   {
     List<IPlugin> plugins = getPlugins();
     StringBuffer data = new StringBuffer();
     if (plugins != null) {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof IDataExportEvent)) {
           IDataExportEvent DataExportEvent = (IDataExportEvent)plugin;
           String plugindata = DataExportEvent.onDataExport();
           data.append(plugindata);
         }
       }
     }


     return data.toString();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\plugin\data\DataExportPluginBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */