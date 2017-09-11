 package com.enation.framework.image.impl;

 import com.enation.framework.image.IThumbnailCreator;
 import com.enation.framework.util.FileUtil;
 import java.awt.Image;
 import java.awt.Toolkit;
 import java.awt.image.BufferedImage;
 import java.io.File;
 import java.io.IOException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.imageio.ImageIO;









 public class JavaImageIOCreator
   implements IThumbnailCreator
 {
   private String srcFile;
   private String destFile;
   private static Map<String, String> extMap = new HashMap(5);
   static { extMap.put("jpg", "JPEG");
     extMap.put("jpeg", "JPEG");
     extMap.put("gif", "GIF");
     extMap.put("png", "PNG");
     extMap.put("bmp", "BMP");
   }

   public JavaImageIOCreator(String sourcefile, String targetFile)
   {
     this.srcFile = sourcefile;
     this.destFile = targetFile;
   }

   public void resize(int w, int h) {
     String ext = FileUtil.getFileExt(this.srcFile).toLowerCase();

     try
     {
       Image img = Toolkit.getDefaultToolkit().getImage(this.srcFile);

       BufferedImage image = FileUtil.toBufferedImage(img);
       ImageIO.write(Lanczos.resizeImage(image, w, h), ext, new File(this.destFile));
     } catch (IOException e) {
       throw new RuntimeException("生成缩略图错误", e);
     }
   }

   public static void main(String[] args) {
     JavaImageIOCreator creator = new JavaImageIOCreator("d:/1.jpg", "d:/1_j_180.jpg");
     creator.resize(180, 180);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\image\impl\JavaImageIOCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */