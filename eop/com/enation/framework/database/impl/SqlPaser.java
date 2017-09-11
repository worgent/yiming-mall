 package com.enation.framework.database.impl;

 import java.util.regex.Matcher;
 import java.util.regex.Pattern;






 public class SqlPaser
 {
   public static String insertSelectField(String field, String sql)
   {
     sql = "select " + field + "," + sql.substring(6, sql.length());
     return sql;
   }







   public static String findOrderStr(String sql)
   {
     String pattern = "(order\\s*by[\\w|\\W|\\s|\\S]*)";
     Pattern p = Pattern.compile(pattern, 34);
     Matcher m = p.matcher(sql);

     if (m.find()) {
       return m.group();
     }

     return null;
   }

   public static void main(String[] args)
   {
     String sql = "select * from abc where 12=12 order by id asc ";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\database\impl\SqlPaser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */