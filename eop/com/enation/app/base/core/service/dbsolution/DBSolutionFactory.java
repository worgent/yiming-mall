 package com.enation.app.base.core.service.dbsolution;

 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.spring.SpringContextHolder;
 import java.sql.Connection;
 import java.sql.SQLException;
 import javax.sql.DataSource;
 import org.springframework.jdbc.core.JdbcTemplate;






 public class DBSolutionFactory
 {
   public static IDBSolution getDBSolution()
   {
     IDBSolution result = null;
     if (EopSetting.DBTYPE.equals("1")) {
       result = (IDBSolution)SpringContextHolder.getBean("mysqlSolution");
     } else if (EopSetting.DBTYPE.equals("2")) {
       result = (IDBSolution)SpringContextHolder.getBean("oracleSolution");
     } else if (EopSetting.DBTYPE.equals("3")) {
       result = (IDBSolution)SpringContextHolder.getBean("sqlserverSolution");
     } else {
       throw new RuntimeException("未知的数据库类型");
     }
     return result;
   }

   public static Connection getConnection(JdbcTemplate jdbcTemplate) {
     if (jdbcTemplate == null) {
       jdbcTemplate = (JdbcTemplate)SpringContextHolder.getBean("jdbcTemplate");
     }
     try {
       return jdbcTemplate.getDataSource().getConnection();
     } catch (SQLException e) {
       e.printStackTrace(); }
     return null;
   }

   public static boolean dbImport(String xml, String prefix)
   {
     Connection conn = getConnection(null);
     IDBSolution dbsolution = getDBSolution();
     dbsolution.setPrefix(prefix);
     dbsolution.setConnection(conn);
//     boolean result;
     boolean result; if (EopSetting.RUNMODE.equals("1")) {
       result = dbsolution.dbImport(xml);
     } else {
       EopSite site = EopContext.getContext().getCurrentSite();
       Integer userid = site.getUserid();
       Integer siteid = site.getId();
       result = dbsolution.dbSaasImport(xml, userid.intValue(), siteid.intValue());
     }
     try {
       conn.close();
     } catch (SQLException e) {
       e.printStackTrace();
       return false;
     }
     return result;
   }

   public static String dbExport(String[] tables, boolean dataOnly, String prefix) {
     Connection conn = getConnection(null);
     IDBSolution dbsolution = getDBSolution();
     dbsolution.setPrefix(prefix);
     dbsolution.setConnection(conn);
     String result = "";

     result = dbsolution.dbExport(tables, dataOnly);





     try
     {
       conn.close();
     } catch (SQLException e) {
       e.printStackTrace();
       return null;
     }
     return result;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\dbsolution\DBSolutionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */