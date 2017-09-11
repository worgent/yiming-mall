 package com.enation.app.base.core.service.impl.database;

 import com.enation.app.base.core.service.IDataBaseCreater;
 import com.enation.framework.database.ISqlFileExecutor;






 public class MysqlDataBaseCreater
   implements IDataBaseCreater
 {
   private ISqlFileExecutor sqlFileExecutor;

   public void create()
   {
     this.sqlFileExecutor.execute("file:com/enation/eop/eop_mysql.sql");
     this.sqlFileExecutor.execute("file:com/enation/app/shop/javashop_mysql.sql");
     this.sqlFileExecutor.execute("file:com/enation/app/cms/cms_mysql.sql");
   }

   public ISqlFileExecutor getSqlFileExecutor() { return this.sqlFileExecutor; }

   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
     this.sqlFileExecutor = sqlFileExecutor;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\impl\database\MysqlDataBaseCreater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */