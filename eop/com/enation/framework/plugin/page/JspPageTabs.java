 package com.enation.framework.plugin.page;

 import java.util.HashMap;
 import java.util.LinkedHashMap;
 import java.util.Map;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;








 public class JspPageTabs
 {
   private static final Logger loger = Logger.getLogger(JspPageTabs.class);



   private static Map<String, Map> tabs = new HashMap();



   public static void addTab(String plugintype, Integer tabid, String tabname)
   {
     Map<Integer, String> plugin_tab = tabs.get(plugintype) == null ? new LinkedHashMap() : (Map)tabs.get(plugintype);
     plugin_tab.put(tabid, tabname);

     tabs.put(plugintype, plugin_tab);
     if (loger.isDebugEnabled()) {
       loger.debug("添加" + plugintype + "选项卡" + tabid + " tabname is  " + tabname);
     }
   }

   public static Map getTabs(String plugintype)
   {
     return (Map)tabs.get(plugintype);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\plugin\page\JspPageTabs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */