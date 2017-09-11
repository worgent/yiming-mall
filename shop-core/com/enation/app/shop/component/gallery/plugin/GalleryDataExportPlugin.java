 package com.enation.app.shop.component.gallery.plugin;

 import com.enation.app.base.core.plugin.data.IDataExportEvent;
 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import org.springframework.stereotype.Component;







 @Component
 public class GalleryDataExportPlugin
   extends AutoRegisterPlugin
   implements IDataExportEvent
 {
   public String onDataExport()
   {
     String[] tables = { "goods_gallery" };

     String insertdata = DBSolutionFactory.dbExport(tables, true, "es_");
     StringBuffer data = new StringBuffer();
     data.append("\t<action>\n");
     data.append("\t\t<command>truncate</command>\n");
     data.append("\t\t<table>es_goods_gallery</table>\n");
     data.append("\t</action>\n");
     data.append(insertdata);
     return data.toString();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\gallery\plugin\GalleryDataExportPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */