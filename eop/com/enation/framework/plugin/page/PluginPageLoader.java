 package com.enation.framework.plugin.page;

 import com.enation.framework.util.StringUtil;
 import java.io.File;
















 public class PluginPageLoader
 {
   private String path;
   private String type;

   public PluginPageLoader(String type, String path)
   {
     path = path.endsWith("/") ? (path = path.substring(0, path.length() - 1)) : path;
     this.type = type;
     this.path = path;
     initPages();
   }




   private void initPages()
   {
     String root_path = StringUtil.getRootPath();
     File file = new File(root_path + this.path);
     File[] files = file.listFiles();
     for (File f : files)
     {
       PluginPageContext.addPage(this.type, this.path + "/" + f.getName());
     }
   }



   public static void main(String[] args)
   {
     PluginPageLoader pluginPageLoader = new PluginPageLoader("userlist", "/admin/user/plugin");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\plugin\page\PluginPageLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */