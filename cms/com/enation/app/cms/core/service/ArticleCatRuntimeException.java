 package com.enation.app.cms.core.service;

 public class ArticleCatRuntimeException
   extends RuntimeException
 {
   private int error_code;

   public ArticleCatRuntimeException(int code)
   {
     this.error_code = code;
   }


   public int getError_code()
   {
     return this.error_code;
   }

   public void setError_code(int error_code) {
     this.error_code = error_code;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\service\ArticleCatRuntimeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */