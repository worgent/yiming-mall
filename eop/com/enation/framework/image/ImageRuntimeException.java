 package com.enation.framework.image;

 public class ImageRuntimeException extends RuntimeException {
   public ImageRuntimeException(String path, String proesstype) {
     super("对图片" + path + "进行" + proesstype + "出错");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\image\ImageRuntimeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */