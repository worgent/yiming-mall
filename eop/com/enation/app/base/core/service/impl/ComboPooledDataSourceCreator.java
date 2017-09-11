 package com.enation.app.base.core.service.impl;

 import com.enation.app.base.core.service.IDataSourceCreator;
 import com.mchange.v2.c3p0.ComboPooledDataSource;
 import java.beans.PropertyVetoException;
 import java.sql.Connection;
 import javax.sql.DataSource;

 public class ComboPooledDataSourceCreator implements IDataSourceCreator
 {
   private DataSource dataSource;

   public DataSource createDataSource(String driver, String url, String username, String password)
   {
     try
     {
       ComboPooledDataSource comboPooledDataSource = (ComboPooledDataSource)this.dataSource;

       comboPooledDataSource.setUser(username);
       comboPooledDataSource.setPassword(password);
       comboPooledDataSource.setJdbcUrl(url);
       comboPooledDataSource.setDriverClass(driver);

       return comboPooledDataSource;
     } catch (PropertyVetoException e) {
       e.printStackTrace();
     }

     return null;
   }

   public static void main(String[] args) throws java.sql.SQLException
   {
     IDataSourceCreator creator = new ComboPooledDataSourceCreator();
     DataSource dataSource = creator.createDataSource("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@localhost:1521:XE", "javashop", "javashop");
     Connection con = dataSource.getConnection();
     java.sql.Statement st = con.createStatement();
     st.execute("delete from test");
   }

   public DataSource getDataSource()
   {
     return this.dataSource;
   }

   public void setDataSource(DataSource dataSource)
   {
     this.dataSource = dataSource;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\impl\ComboPooledDataSourceCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */