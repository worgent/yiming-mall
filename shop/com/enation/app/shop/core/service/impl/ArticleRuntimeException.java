 package com.enation.app.shop.core.service.impl;
 @Deprecated
 public class ArticleRuntimeException extends RuntimeException {
   private int error_code;

   public ArticleRuntimeException(int code) { this.error_code = code; }



   public int getError_code()
   {
     return this.error_code;
   }

   public void setError_code(int error_code) {
     this.error_code = error_code;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\ArticleRuntimeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */