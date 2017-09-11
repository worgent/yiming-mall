 package com.enation.framework.database.impl;

 import com.enation.eop.sdk.context.EopSetting;
 import org.springframework.jdbc.core.ColumnMapRowMapper;
 import org.springframework.jdbc.core.JdbcTemplate;
 import org.springframework.jdbc.core.RowMapper;






 public class LowerCaseJdbcTemplate
   extends JdbcTemplate
 {
   protected RowMapper getColumnMapRowMapper()
   {
     if ("2".equals(EopSetting.DBTYPE))
       return new OracleColumnMapRowMapper();
     if ("1".equals(EopSetting.DBTYPE)) {
       return new MySqlColumnMapRowMapper();
     }
     return new ColumnMapRowMapper();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\database\impl\LowerCaseJdbcTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */