 package com.enation.app.base.core.service.dbsolution.impl;

 import com.enation.eop.sdk.utils.DateUtil;
 import java.sql.Timestamp;
 import java.util.Date;
 import java.util.List;
 import org.dom4j.Element;











 public class SQLServerSolution
   extends DBSolution
 {
   protected boolean setIdentity(String table, boolean on)
   {
     this.sqlExchange = ("SET IDENTITY_INSERT " + table + " " + (on ? "ON" : "OFF"));
     return true;
   }

   protected boolean beforeInsert(String table, String fields, String values)
   {
     return setIdentity(table, true);
   }

   protected void afterInsert(String table, String field, String values)
   {
     setIdentity(table, false);
   }












   public String toLocalType(String type, String size)
   {
     if ("int".equals(type)) {
       if ("1".equals(size)) {
         return "smallint";
       }
       return "int";
     }
     if ("smallint".equals(type)) {
       return "smallint";
     }
     if ("bigint".equals(type)) {
       return "bigint";
     }
     if ("memo".equals(type)) {
       return "text";
     }
     if ("datetime".equals(type)) {
       return "datetime";
     }
     if ("long".equals(type)) {
       return "bigint";
     }
     return type + "(" + size + ")";
   }

   public String getCreateSQL(Element action)
   {
     String table = getTableName(action.elementText("table"));
     List<Element> fields = action.elements("field");
     String sql = getDropSQL(table) + "!-->";
     sql = sql + "create table " + table + " (";

     String pk = "";
     for (int i = 0; i < fields.size(); i++) {
       String nl = "";
       Element field = (Element)fields.get(i);
       String name = "[" + field.elementText("name") + "]";
       String size = field.elementText("size");
       String type = toLocalType(field.elementText("type").toLowerCase(), size);

       String option = field.elementText("option");
       String def = field.elementText("default");

       if ("1".equals(option.substring(1, 2))) {
         nl = " not null";
       }
       if (def != null) {
         nl = nl + " default " + def;
       }
       if ("1".equals(option.substring(0, 1))) {
         pk = "constraint PK_" + table.toUpperCase() + " primary key nonclustered (" + name + "),";

         nl = nl + " identity";
       }

       sql = sql + name + " " + type + nl + ",";
     }
     sql = sql + pk;
     sql = sql.substring(0, sql.length() - 1) + ")";

     return sql;
   }




   public String[] getFuncName()
   {
     String[] name = { "time" };
     return name;
   }

   public String getFieldValue(int fieldType, Object fieldValue)
   {
     if ((fieldValue instanceof Timestamp)) {
       Date value = DateUtil.toDate(fieldValue.toString(), "yyyy-MM-dd HH:mm:ss.S");
       return "time(" + value.getTime() + ")";
     }
     return super.getFieldValue(fieldType, fieldValue);
   }

   public String getDropSQL(String table)
   {
     String sql = "if exists (select 1 from sysobjects where id = object_id('" + table + "')" + "and type = 'U') drop table " + table;




     return sql;
   }

   public String getSaasCreateSQL(Element action, int userid, int siteid)
   {
     String table = getSaasTableName(action.elementText("table"), userid, siteid);
     List<Element> fields = action.elements("field");
     String sql = getDropSQL(table) + "!-->";
     sql = sql + "create table " + table + " (";

     String pk = "";
     for (int i = 0; i < fields.size(); i++) {
       String nl = "";
       Element field = (Element)fields.get(i);
       String name = "[" + field.elementText("name") + "]";
       String size = field.elementText("size");
       String type = toLocalType(field.elementText("type").toLowerCase(), size);

       String option = field.elementText("option");
       String def = field.elementText("default");

       if ("1".equals(option.substring(1, 2))) {
         nl = " not null";
       }
       if (def != null) {
         nl = nl + " default " + def;
       }
       if ("1".equals(option.substring(0, 1))) {
         pk = "constraint PK_" + table.toUpperCase() + " primary key nonclustered (" + name + "),";

         nl = nl + " identity";
       }

       sql = sql + name + " " + type + nl + ",";
     }
     sql = sql + pk;
     sql = sql.substring(0, sql.length() - 1) + ")";

     return sql;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\dbsolution\impl\SQLServerSolution.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */