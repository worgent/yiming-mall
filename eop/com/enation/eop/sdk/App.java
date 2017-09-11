 package com.enation.eop.sdk;

 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.log4j.Logger;








 public abstract class App
   implements IApp
 {
   protected final Logger logger = Logger.getLogger(getClass());

   protected boolean dataOnly = true;


   protected List<String> tables;


   protected int userid;


   protected int siteid;



   protected boolean exceptTable(String table)
   {
     table = table.toLowerCase();

     if (table.startsWith("eop_"))
       return true;
     if ((table.endsWith("menu")) && (!table.endsWith("site_menu")))
       return true;
     if (table.endsWith("themeuri"))
       return true;
     if (table.endsWith("theme"))
       return true;
     if (table.endsWith("admintheme")) {
       return true;
     }
     return false;
   }






   protected String[] toArray(List<String> list)
   {
     String[] values = new String[list.size()];
     return (String[])list.toArray(values);
   }

   public App() {
     this.tables = new ArrayList();
   }

   protected void doInstall(String xmlFile) {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug(getName() + " 开始执行 " + xmlFile + "...");
     }
     DBSolutionFactory.dbImport(xmlFile, "es_");






     if (this.logger.isDebugEnabled()) {
       this.logger.debug(getName() + " 执行 " + xmlFile + "成功！");
     }

     cleanCache();
   }

   protected void cleanCache() {
     EopSite site = EopContext.getContext().getCurrentSite();
     this.userid = site.getUserid().intValue();
     this.siteid = site.getId().intValue();
   }

   public String dumpXml() {
     List<String> dataTable = new ArrayList();
     int i = 0; for (int len = this.tables.size(); i < len; i++) {
       String table = (String)this.tables.get(i);
       if (!exceptTable(table))
         dataTable.add(table);
     }
     StringBuffer xml = new StringBuffer();
     xml.append(DBSolutionFactory.dbExport(toArray(dataTable), this.dataOnly, "es_"));
     return xml.toString();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\App.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */