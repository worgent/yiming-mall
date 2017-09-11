 package com.enation.app.base.core.service.auth.impl;

 import com.enation.framework.util.FileUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.InputStream;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.Properties;

 public class PermissionConfig
 {
   private static Map<String, Integer> authMap = new HashMap();

   static {
     try {
       InputStream in = FileUtil.getResourceAsStream("auth.properties");
       Properties props = new Properties();
       props.load(in);

       int super_admin = StringUtil.toInt(props.getProperty("auth.super_admin"), true);
       int goods = StringUtil.toInt(props.getProperty("auth.goods"), true);
       int order = StringUtil.toInt(props.getProperty("auth.order"), true);
       int depot_admin = StringUtil.toInt(props.getProperty("auth.depot_admin"), true);
       int finance = StringUtil.toInt(props.getProperty("auth.finance"), true);
       int customer_service = StringUtil.toInt(props.getProperty("auth.customer_service"), true);
       int depot_ship = StringUtil.toInt(props.getProperty("auth.depot_ship"), true);

       authMap.put("super_admin", Integer.valueOf(super_admin));
       authMap.put("goods", Integer.valueOf(goods));
       authMap.put("order", Integer.valueOf(order));
       authMap.put("depot_admin", Integer.valueOf(depot_admin));
       authMap.put("finance", Integer.valueOf(finance));
       authMap.put("customer_service", Integer.valueOf(customer_service));
     }
     catch (Exception e) {
       e.printStackTrace();
     }
   }

   public static int getAuthId(String type) {
     return ((Integer)authMap.get(type)).intValue();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\auth\impl\PermissionConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */