 package com.enation.framework.database;

 import java.sql.ResultSet;
 import java.sql.SQLException;
 import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

 public class DoubleMapper
   implements ParameterizedRowMapper
 {
   public Object mapRow(ResultSet rs, int rowNum) throws SQLException
   {
     Double dobule = new Double(rs.getDouble(1));
     return dobule;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\database\DoubleMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */