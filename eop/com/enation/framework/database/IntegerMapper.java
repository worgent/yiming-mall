 package com.enation.framework.database;

 import java.sql.ResultSet;
 import java.sql.SQLException;
 import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

 public class IntegerMapper
   implements ParameterizedRowMapper
 {
   public Object mapRow(ResultSet rs, int rowNum) throws SQLException
   {
     Integer v = Integer.valueOf(rs.getInt(1));
     return v;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\database\IntegerMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */