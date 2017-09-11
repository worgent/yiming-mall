 package com.enation.framework.util.ip;

 import org.apache.log4j.Level;
 import org.apache.log4j.Logger;


 public class LogFactory
 {
   private static final Logger logger = Logger.getLogger("stdout");
   static { logger.setLevel(Level.DEBUG); }

   public static void log(String info, Level level, Throwable ex)
   {
     logger.log(level, info, ex);
   }

   public static Level getLogLevel() {
     return logger.getLevel();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\\util\ip\LogFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */