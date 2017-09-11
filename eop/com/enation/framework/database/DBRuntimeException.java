 package com.enation.framework.database;

 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;








 public class DBRuntimeException
   extends RuntimeException
 {
   private static final long serialVersionUID = -368646349014580765L;
   private static final Logger loger = Logger.getLogger(DBRuntimeException.class);


   public DBRuntimeException(String message)
   {
     super(message);
   }

   public DBRuntimeException(Exception e, String sql) {
     super("数据库运行期异常");
     e.printStackTrace();
//     if (loger.isErrorEnabled()) {
       loger.error("数据库运行期异常，相关sql语句为" + sql);
//     }
   }


   public DBRuntimeException(String message, String sql)
   {
     super(message);
//     if (loger.isErrorEnabled()) {
       loger.error(message + "，相关sql语句为" + sql);
//     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\database\DBRuntimeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */