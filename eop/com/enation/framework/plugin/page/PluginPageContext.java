 package com.enation.framework.plugin.page;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;









 public class PluginPageContext
 {
   private static Map<String, List<String>> plugin_pages = new HashMap();









   public static void addPage(String type, String page)
   {
     List<String> pagelist = (List)plugin_pages.get(type);
     if (pagelist == null)
       pagelist = new ArrayList();
     pagelist.add(page);
     plugin_pages.put(type, pagelist);
   }







   public static List<String> getPages(String page_type)
   {
     return (List)plugin_pages.get(page_type);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\plugin\page\PluginPageContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */