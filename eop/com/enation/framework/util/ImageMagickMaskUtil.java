 package com.enation.framework.util;

 import java.awt.Dimension;
 import java.awt.Font;
 import magick.DrawInfo;
 import magick.ImageInfo;
 import magick.MagickException;
 import magick.MagickImage;
 import magick.PixelPacket;





 public class ImageMagickMaskUtil
 {
   static
   {
     System.setProperty("jmagick.systemclassloader", "no");
   }









   public void mask(String source, String text, String fontcolor, int fontsize, int pos, String fontPath)
   {
     try
     {
       ImageInfo info = new ImageInfo(source);
       MagickImage aImage = new MagickImage(info);

       Double width = Double.valueOf(aImage.getDimension().getWidth());
       Double height = Double.valueOf(aImage.getDimension().getHeight());

       DrawInfo aInfo = new DrawInfo(info);

       aInfo.setFill(PixelPacket.queryColorDatabase(fontcolor));
       aInfo.setOpacity(0);
       aInfo.setPointsize(fontsize);

       if (fontPath != null)
         aInfo.setFont(fontPath);
       Font f = new Font("宋体", 1, fontsize);

       aInfo.setTextAntialias(true);
       aInfo.setText(text);



       int textwidth = 100;
       int textheight = 20;
       int[] xy = getXy(width.intValue(), height.intValue(), textwidth, textheight, pos);
       aInfo.setGeometry("+" + xy[0] + "+" + xy[1]);

       aImage.annotateImage(aInfo);

       aImage.setFileName(source);
       aImage.writeImage(info);
       aImage.destroyImages();
       aImage = null;
     } catch (MagickException e) {
       e.printStackTrace();
     }
   }


   private int[] getXy(int width, int height, int textwidth, int textheight, int pos)
   {
     int x = 0;int y = 0;
     int margin = 20;
     switch (pos) {
     case 1:
       x = margin;
       y = margin;
       break;
     case 2:
       y = margin;
       x = width / 2 - textwidth / 2;
       break;
     case 3:
       y = margin;
       x = width - margin - textwidth;
       break;
     case 4:
       y = height / 2 - textheight / 2;
       x = margin;
       break;
     case 5:
       y = height / 2 - textheight / 2;
       x = width / 2 - textwidth / 2;
       break;
     case 6:
       y = height / 2 - textheight / 2;
       x = width - margin - textwidth;
       break;
     case 7:
       x = margin;
       y = height - textheight;
       break;
     case 8:
       x = width / 2 - textwidth / 2;
       y = height - textheight;
       break;
     case 9:
       x = width - margin - textwidth;
       y = height - textheight;
       break;
     }



     return new int[] { x, y };
   }

   public static void main(String[] args) {
     ImageMagickMaskUtil magickMask = new ImageMagickMaskUtil();
     magickMask.mask("d:/temp.jpg", "易族智汇", "#000000", 16, 5, "e:/st.TTF");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\\util\ImageMagickMaskUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */