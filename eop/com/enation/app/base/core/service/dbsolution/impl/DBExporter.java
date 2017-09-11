 package com.enation.app.base.core.service.dbsolution.impl;

 import java.io.FileWriter;
 import java.sql.Connection;
 import java.sql.DatabaseMetaData;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.util.HashMap;
 import java.util.Map;





 public class DBExporter
   extends DBPorter
 {
   private String prefix = "";
   private String beginLine = "\t";
   private String endLine = "\n";

   public DBExporter(DBSolution solution) {
     super(solution);
   }





   private String beginLine(int count)
   {
     String result = "";
     for (int i = 0; i < count; i++) {
       result = result + this.beginLine;
     }
     return result;
   }








   protected String getFieldOption(ResultSetMetaData rsmd, int index)
     throws SQLException
   {
     String auto = "0";
     String nullable = "0";

     if (rsmd.isAutoIncrement(index))
       auto = "1";
     if (rsmd.isNullable(index) == 0) {
       nullable = "1";
     }
     return auto + nullable;
   }








   private void createAction(String table, StringBuilder xmlFile, String command)
   {
     xmlFile.append(this.beginLine + "<action>" + this.endLine);
     xmlFile.append(beginLine(2) + "<command>" + command + "</command>" + this.endLine);

     xmlFile.append(beginLine(2) + "<table>" + table.toLowerCase() + "</table>" + this.endLine);
   }







   private boolean createTableXml(String table, StringBuilder xmlFile)
   {
     createAction(table, xmlFile, "create");
     try
     {
       createFieldXml(table, xmlFile);
     } catch (SQLException e) {
       e.printStackTrace();
       return false;
     }

     xmlFile.append(this.beginLine + "</action>" + this.endLine);
     return true;
   }

   private boolean createTableXml(String table, int userid, int siteid, StringBuilder xmlFile) {
     createAction(table, xmlFile, "create");
     try
     {
       createFieldXml(table + "_" + userid + "_" + siteid, xmlFile);
     } catch (SQLException e) {
       e.printStackTrace();
       return false;
     }

     xmlFile.append(this.beginLine + "</action>" + this.endLine);
     return true;
   }






   protected String getFieldTypeName(int type)
   {
     String result = "";
     switch (type) {
     case 4:
     case 5:
       result = "int";
       break;
     case 12:
       result = "varchar";
       break;
     case -16:
     case -1:
     case 2005:
       result = "memo";
       break;
     case -5:
       result = "long";
       break;
     case 3:
       result = "decimal";
       break;
     case 91:
       result = "date";
       break;
     case 93:
       result = "datetime";
       break;
     default:
       result = "varchar";
     }
     return result;
   }







   private void createFieldXml(String table, StringBuilder xmlFile)
     throws SQLException
   {
     Statement st = this.solution.conn.createStatement();
     ResultSet rs = st.executeQuery("select * from " + this.prefix + table);

     DatabaseMetaData metaData = this.solution.conn.getMetaData();
     Map<String, String> columns = new HashMap();
     ResultSet mdrs = metaData.getColumns(null, null, table.toUpperCase(), "%");



     while (mdrs.next()) {
       columns.put(mdrs.getString("COLUMN_NAME"), mdrs.getString("COLUMN_DEF"));
     }

     ResultSetMetaData rsmd = rs.getMetaData();
     for (int i = 1; i <= rsmd.getColumnCount(); i++) {
       String columnName = rsmd.getColumnName(i);

       xmlFile.append(beginLine(2) + "<field>");

       xmlFile.append("<name>" + rsmd.getColumnName(i).toLowerCase() + "</name>");
       xmlFile.append("<type>" + getFieldTypeName(rsmd.getColumnType(i)) + "</type>");

       xmlFile.append("<size>" + rsmd.getPrecision(i) + "</size>");
       xmlFile.append("<option>" + getFieldOption(rsmd, i) + "</option>");

       if (columns.get(columnName) != null) {
         String value = this.solution.getFieldValue(rsmd.getColumnType(i), columns.get(columnName));
         value = value.replaceAll("\\(", "");
         value = value.replaceAll("\\)", "");
         xmlFile.append("<default>" + value + "</default>");
       }
       xmlFile.append("</field>" + this.endLine);
     }
   }






   private boolean saveDocument(String xml, String text)
   {
     try
     {
       FileWriter file = new FileWriter(xml);
       file.write(text);
       file.close();
       return true;
     } catch (Exception e) {}
     return false;
   }







   private boolean insertDataXml(String table, StringBuilder xmlFile)
   {
     try
     {
       Statement st = this.solution.conn.createStatement();
       ResultSet rs = st.executeQuery("select * from " + table);
       ResultSetMetaData rsmd = rs.getMetaData();

       while (rs.next()) {
         String fields = "";
         String values = "";
         for (int i = 1; i <= rsmd.getColumnCount(); i++) {
           Object value = rs.getObject(i);
           if (value != null) {
             fields = fields + rsmd.getColumnName(i) + ",";
             values = values + this.solution.getFieldValue(rsmd.getColumnType(i), value) + ",";
           }
         }
         createAction(table, xmlFile, "insert");
         xmlFile.append(beginLine(2) + "<fields>" + fields.substring(0, fields.length() - 1).toLowerCase() + "</fields>" + this.endLine);


         xmlFile.append(beginLine(2) + "<values>" + this.solution.encode(values.substring(0, values.length() - 1)) + "</values>" + this.endLine);



         xmlFile.append(this.beginLine + "</action>" + this.endLine);
       }
     }
     catch (SQLException e) {
       e.printStackTrace();
       return false;
     }

     return true;
   }

   private boolean insertDataXml(String table, int userid, int siteid, StringBuilder xmlFile) {
     try {
       Statement st = this.solution.conn.createStatement();
       ResultSet rs = st.executeQuery("select * from " + table + "_" + userid + "_" + siteid);
       ResultSetMetaData rsmd = rs.getMetaData();

       while (rs.next()) {
         String fields = "";
         String values = "";
         for (int i = 1; i <= rsmd.getColumnCount(); i++) {
           Object value = rs.getObject(i);
           if (value != null) {
             fields = fields + rsmd.getColumnName(i) + ",";
             values = values + this.solution.getFieldValue(rsmd.getColumnType(i), value) + ",";
           }
         }
         createAction(table, xmlFile, "insert");
         xmlFile.append(beginLine(2) + "<fields>" + fields.substring(0, fields.length() - 1).toLowerCase() + "</fields>" + this.endLine);


         xmlFile.append(beginLine(2) + "<values>" + this.solution.encode(values.substring(0, values.length() - 1)) + "</values>" + this.endLine);



         xmlFile.append(this.beginLine + "</action>" + this.endLine);
       }
     }
     catch (SQLException e) {
       e.printStackTrace();
       return false;
     }

     return true;
   }








   public String doExport(String prefix, String[] tables, boolean dataOnly)
   {
     StringBuilder xml = new StringBuilder();

     if (!dataOnly) {
       for (int i = 0; i < tables.length; i++) {
         if (tables[i].toUpperCase().startsWith("EOP_")) {
           createTableXml(tables[i], xml);
         } else
           createTableXml(prefix + tables[i], xml);
       }
     }
     for (int i = 0; i < tables.length; i++) {
       if (tables[i].toUpperCase().startsWith("EOP_")) {
         insertDataXml(tables[i], xml);
       } else
         insertDataXml(prefix + tables[i], xml);
     }
     return xml.toString();
   }

   public String doExport(String prefix, String[] tables, boolean dataOnly, int userid, int siteid) {
     StringBuilder xml = new StringBuilder();

     if (!dataOnly) {
       for (int i = 0; i < tables.length; i++) {
         if (tables[i].toUpperCase().startsWith("EOP_")) {
           createTableXml(tables[i], xml);
         } else
           createTableXml(prefix + tables[i], userid, siteid, xml);
       }
     }
     for (int i = 0; i < tables.length; i++) {
       if (tables[i].toUpperCase().startsWith("EOP_")) {
         insertDataXml(tables[i], xml);
       } else
         insertDataXml(prefix + tables[i], userid, siteid, xml);
     }
     return xml.toString();
   }








   public boolean doExport(String prefix, String[] tables, String xml)
   {
     this.prefix = prefix;

     StringBuilder xmlFile = new StringBuilder();
     xmlFile.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + this.endLine);
     xmlFile.append("<dbsolution>" + this.endLine);
     xmlFile.append(doExport(prefix, tables, false));
     xmlFile.append("</dbsolution>" + this.endLine);

     return saveDocument(xml, xmlFile.toString());
   }

   public boolean doExport(String prefix, String[] tables, String xml, int userid, int siteid) {
     this.prefix = prefix;

     StringBuilder xmlFile = new StringBuilder();
     xmlFile.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + this.endLine);
     xmlFile.append("<dbsolution>" + this.endLine);
     xmlFile.append(doExport(prefix, tables, false, userid, siteid));
     xmlFile.append("</dbsolution>" + this.endLine);

     return saveDocument(xml, xmlFile.toString());
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\dbsolution\impl\DBExporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */