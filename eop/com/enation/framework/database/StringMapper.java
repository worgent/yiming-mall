 package com.enation.framework.database;

 import java.sql.ResultSet;
 import java.sql.SQLException;
 import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

 public class StringMapper
   implements ParameterizedRowMapper
 {
   public Object mapRow(ResultSet rs, int rowNum) throws SQLException
   {
     String str = rs.getString(1);
     return str;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\database\StringMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */