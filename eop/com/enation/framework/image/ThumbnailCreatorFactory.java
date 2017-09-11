 package com.enation.framework.image;

 import com.enation.framework.image.impl.ImageMagickCreator;
 import com.enation.framework.image.impl.JavaImageIOCreator;
 import java.io.IOException;








 public abstract class ThumbnailCreatorFactory
 {
   public static String CREATORTYPE = "javaimageio";








   public static final IThumbnailCreator getCreator(String source, String target)
   {
     if (CREATORTYPE.equals("javaimageio")) {
       return new JavaImageIOCreator(source, target);
     }

     if (CREATORTYPE.equals("imagemagick")) {
       try {
         return new ImageMagickCreator(source, target);
       }
       catch (IOException e) {
         e.printStackTrace();
       }
     }


     return new JavaImageIOCreator(source, target);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\image\ThumbnailCreatorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */