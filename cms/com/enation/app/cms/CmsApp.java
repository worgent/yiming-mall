 package com.enation.app.cms;

 import com.enation.app.base.core.service.solution.impl.SqlExportService;
 import com.enation.app.cms.core.model.DataModel;
 import com.enation.app.cms.core.service.IDataModelManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.App;
 import com.enation.framework.cache.CacheFactory;
 import com.enation.framework.cache.ICache;
 import com.enation.framework.database.IDBRouter;
 import com.enation.framework.database.ISqlFileExecutor;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import org.dom4j.Document;
 import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;









 public class CmsApp
   extends App
 {
   private IDBRouter baseDBRouter;
   private IDataModelManager dataModelManager;
   private SqlExportService sqlExportService;
   private SimpleJdbcTemplate simpleJdbcTemplate;
   private ISqlFileExecutor sqlFileExecutor;

   public CmsApp()
   {
     this.tables.add("data_cat");
     this.tables.add("data_model");
     this.tables.add("data_field");
   }

   public void saasInstall() {
     this.baseDBRouter.doSaasInstall("file:com/enation/app/cms/cms.xml");
   }






   @Deprecated
   public String dumpSql()
   {
     List<String> newTbs = new ArrayList();
     newTbs.addAll(this.tables);

     StringBuffer sql = new StringBuffer();
     List<DataModel> modelList = this.dataModelManager.list();

     for (DataModel model : modelList)
     {
       String tbSql = this.sqlExportService.getCreateTableSql(this.baseDBRouter.getTableName(model.getEnglish_name()));
       String tbName = "es_" + model.getEnglish_name() + "_<userid>_<siteid>";
       sql.append("drop table if exists " + tbName + ";\n");
       tbSql = replaceTbName(tbSql, tbName);
       sql.append(tbSql);
       newTbs.add(model.getEnglish_name());
     }

     sql.append(this.sqlExportService.dumpSql(newTbs));

     return sql.toString();
   }

   private static String replaceTbName(String sql, String tbName)
   {
     Pattern p = Pattern.compile("CREATE TABLE `(.*?)`(.*)", 34);
     Matcher m = p.matcher(sql);
     if (m.find()) {
       sql = m.replaceAll("CREATE TABLE `" + tbName + "`$2");
     }
     return sql;
   }



   public void sessionDestroyed(String seesionid, EopSite site) {}



   public String dumpSql(Document setup)
   {
     List<String> newTbs = new ArrayList();
     newTbs.addAll(this.tables);

     StringBuffer sql = new StringBuffer();
     List<DataModel> modelList = this.dataModelManager.list();

     for (DataModel model : modelList)
     {
       String tbSql = this.sqlExportService.getCreateTableSql(this.baseDBRouter.getTableName(model.getEnglish_name()));
       String tbName = "es_" + model.getEnglish_name() + "_<userid>_<siteid>";

       tbSql = replaceTbName(tbSql, tbName);
       sql.append(tbSql);
       newTbs.add(model.getEnglish_name());
     }

     sql.append(this.sqlExportService.dumpSql(newTbs, "recreate", setup));

     return sql.toString();
   }

   public String dumpXml()
   {
     String xml = super.dumpXml();
     StringBuffer sql = new StringBuffer();
     List<DataModel> modelList = this.dataModelManager.list();
     List<String> oldTable = new ArrayList();
     oldTable.addAll(this.tables);
     this.tables.clear();
     for (DataModel model : modelList) {
       this.tables.add(model.getEnglish_name());
     }
     this.dataOnly = false;
     xml = xml + super.dumpXml();
     this.tables.clear();
     this.tables.addAll(oldTable);
     return xml;
   }

   public String getId()
   {
     return "cms";
   }


   public String getName()
   {
     return "cms应用";
   }


   public String getNameSpace()
   {
     return "/cms";
   }

   public void install()
   {
     doInstall("file:com/enation/app/cms/cms.xml");
   }

   protected void cleanCache() {
     super.cleanCache();

     CacheFactory.getCache("cms_data_cat").remove("cms_data_cat_" + this.userid + "_" + this.siteid);
   }

   public ISqlFileExecutor getSqlFileExecutor()
   {
     return this.sqlFileExecutor;
   }


   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) { this.sqlFileExecutor = sqlFileExecutor; }

   public static void main(String[] args) {
     String sql = "CREATE TABLE `es_table_1_3_4` (  \n `id` mediumint(8) NOT NULL AUTO_INCREMENT,\n`add_time` datetime DEFAULT NULL) \n ENGINE=MyISAM AUTO_INCREMENT=168 DEFAULT CHARSET=utf8;";
     sql = replaceTbName(sql, "es_table_1_<userid>_<siteid>");
   }

   public IDBRouter getBaseDBRouter()
   {
     return this.baseDBRouter;
   }

   public void setBaseDBRouter(IDBRouter baseDBRouter) {
     this.baseDBRouter = baseDBRouter;
   }

   public IDataModelManager getDataModelManager()
   {
     return this.dataModelManager;
   }

   public void setDataModelManager(IDataModelManager dataModelManager) {
     this.dataModelManager = dataModelManager;
   }

   public SqlExportService getSqlExportService() {
     return this.sqlExportService;
   }

   public void setSqlExportService(SqlExportService sqlExportService) {
     this.sqlExportService = sqlExportService;
   }

   public SimpleJdbcTemplate getSimpleJdbcTemplate() {
     return this.simpleJdbcTemplate;
   }

   public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
     this.simpleJdbcTemplate = simpleJdbcTemplate;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\CmsApp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */