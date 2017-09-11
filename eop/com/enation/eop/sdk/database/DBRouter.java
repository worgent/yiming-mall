 package com.enation.eop.sdk.database;

 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.database.IDBRouter;
 import com.enation.framework.util.StringUtil;
 import org.apache.log4j.Logger;
 import org.springframework.jdbc.core.JdbcTemplate;












 public class DBRouter
   implements IDBRouter
 {
   public static final String EXECUTECHAR = "!-->";
   protected final Logger logger = Logger.getLogger(getClass());

   private JdbcTemplate jdbcTemplate;
   private String prefix;

   public void setPrefix(String prefix)
   {
     this.prefix = prefix;
   }

   public void doSaasInstall(String xmlFile) {
     if ("1".equals(EopSetting.RUNMODE)) {
       return;
     }

     this.prefix = (this.prefix == null ? "" : this.prefix);
     DBSolutionFactory.dbImport(xmlFile, this.prefix);
   }

   public String getTableName(String moudle)
   {
     String result = StringUtil.addPrefix(moudle, this.prefix);
     if ("1".equals(EopSetting.RUNMODE)) {
       return result;
     }

     EopSite site = EopContext.getContext().getCurrentSite();
     Integer userid = site.getUserid();
     Integer siteid = site.getId();

     return StringUtil.addSuffix(result, "_" + userid + "_" + siteid);
   }

   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
     this.jdbcTemplate = jdbcTemplate;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\database\DBRouter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */