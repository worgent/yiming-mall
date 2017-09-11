 package com.enation.framework.database.impl;

 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.util.Map;
 import org.springframework.jdbc.support.JdbcUtils;

 public class FilterColumnMapRowMapper extends org.springframework.jdbc.core.ColumnMapRowMapper
 {
   private IRowMapperColumnFilter filter;

   public FilterColumnMapRowMapper(IRowMapperColumnFilter _filter)
   {
     this.filter = _filter;
   }

   public Object mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException
   {
     ResultSetMetaData rsmd = rs.getMetaData();
     int columnCount = rsmd.getColumnCount();
     Map mapOfColValues = createColumnMap(columnCount);
     for (int i = 1; i <= columnCount; i++) {
       String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
       key = key.toLowerCase();
       Object obj = getColumnValue(rs, i);
       mapOfColValues.put(key, obj);
       this.filter.filter(mapOfColValues, rs);
     }
     return mapOfColValues;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\database\impl\FilterColumnMapRowMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */