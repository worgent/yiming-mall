 package com.enation.framework.util;

 import java.util.List;
 import net.sf.json.JSONArray;
 import net.sf.json.JSONObject;









 public class JsonMessageUtil
 {
   public static String getObjectJson(Object object)
   {
     if (object == null) {
       return getErrorJson("object is null");
     }

     try
     {
       String objStr = JSONObject.fromObject(object).toString();

       return "{\"result\":1,\"data\":" + objStr + "}";
     } catch (Exception e) {
       e.printStackTrace(); }
     return "";
   }




   public static String getObjectJson(Object object, String objectname)
   {
     if (object == null) {
       return getErrorJson("object is null");
     }

     try
     {
       String objStr = JSONObject.fromObject(object).toString();

       return "{\"result\":1,\"" + objectname + "\":" + objStr + "}";
     } catch (Exception e) {
       e.printStackTrace(); }
     return "";
   }


   public static String getStringJson(String name, String value)
   {
     return "{\"result\":1,\"" + name + "\":\"" + value + "\"}";
   }

   public static String getNumberJson(String name, Object value) {
     return "{\"result\":1,\"" + name + "\":" + value + "}";
   }

   public static String getListJson(List list) {
     if (list == null) {
       return getErrorJson("list is null");
     }
     String listStr = JSONArray.fromObject(list).toString();
     return "{\"result\":1,\"data\":" + listStr + "}";
   }

   public static String getErrorJson(String message)
   {
     return "{\"result\":0,\"message\":\"" + message + "\"}";
   }


   public static String getSuccessJson(String message)
   {
     return "{\"result\":1,\"message\":\"" + message + "\"}";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\\util\JsonMessageUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */