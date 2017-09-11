 package com.enation.app.base.core.service;

 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.spring.SpringContextHolder;







 public abstract class DataBaseCreaterFactory
 {
   public static IDataBaseCreater getDataBaseCreater()
   {
     if (EopSetting.DBTYPE.equals("1"))
       return (IDataBaseCreater)SpringContextHolder.getBean("mysqlDataBaseCreater");
     if (EopSetting.DBTYPE.equals("2"))
       return (IDataBaseCreater)SpringContextHolder.getBean("oracleDataBaseCreater");
     if (EopSetting.DBTYPE.equals("3")) {
       return (IDataBaseCreater)SpringContextHolder.getBean("mssqlDataBaseCreater");
     }
     throw new RuntimeException("未知的数据库类型");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\DataBaseCreaterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */