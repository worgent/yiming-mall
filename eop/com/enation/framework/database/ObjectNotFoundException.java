 package com.enation.framework.database;


 public class ObjectNotFoundException
   extends DBRuntimeException
 {
   private static final long serialVersionUID = -3403302876974180460L;


   public ObjectNotFoundException(String message)
   {
     super(message);
   }

   public ObjectNotFoundException(Exception e, String sql) {
     super(e, sql);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\database\ObjectNotFoundException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */