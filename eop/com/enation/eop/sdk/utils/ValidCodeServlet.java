 package com.enation.eop.sdk.utils;

 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics;
 import java.awt.image.BufferedImage;
 import java.io.IOException;
 import java.util.Random;
 import javax.imageio.ImageIO;
 import javax.servlet.ServletException;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;






 public class ValidCodeServlet
   extends HttpServlet
 {
   public static final String SESSION_VALID_CODE = "valid_code";
   private Random generator = new Random();

   private static char[] captchars = { 'a', 'b', 'c', 'd', 'e', 'f', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z', '2', '3', '4', '5', '6', '7', '8' };






   private Font getFont()
   {
     Random random = new Random();
     Font[] font = new Font[5];
     font[0] = new Font("Ravie", 0, 45);
     font[1] = new Font("Antique Olive Compact", 0, 45);
     font[2] = new Font("Forte", 0, 45);
     font[3] = new Font("Wide Latin", 0, 40);
     font[4] = new Font("Gill Sans Ultra Bold", 0, 45);
     return font[random.nextInt(5)];
   }





   private Color getRandColor()
   {
     Random random = new Random();
     Color[] color = new Color[10];
     color[0] = new Color(32, 158, 25);
     color[1] = new Color(218, 42, 19);
     color[2] = new Color(31, 75, 208);
     return color[random.nextInt(3)];
   }

   protected Color getRandColor(int fc, int bc) {
     Random random = new Random();
     if (fc > 255)
       fc = 255;
     if (bc > 255)
       bc = 255;
     int r = fc + random.nextInt(bc - fc);
     int g = fc + random.nextInt(bc - fc);
     int b = fc + random.nextInt(bc - fc);
     return new Color(r, g, b);
   }





   public void doGet(HttpServletRequest req, HttpServletResponse resp)
     throws ServletException, IOException
   {
     int width = 64;int height = 22;
     resp.addHeader("Cache-Control", "no-cache");
     resp.addHeader("Cache-Control", "no-store");
     resp.addHeader("Cache-Control", "must-revalidate");
     resp.setHeader("Pragma", "no-cache");
     resp.setDateHeader("Expires", -1L);

     String vtype = "";

     if (req.getParameter("vtype") != null) {
       vtype = req.getParameter("vtype");
     }

     BufferedImage image = new BufferedImage(width, height, 1);

     if (req.getParameter("vtype") != null) {
       vtype = req.getParameter("vtype");
     }


     Graphics g = image.getGraphics();


     Random random = new Random();


     g.setColor(getRandColor(200, 250));
     g.fillRect(0, 0, width, height);


     g.setFont(new Font("Times New Roman", 0, 18));






     g.setColor(getRandColor(160, 200));
     for (int i = 0; i < 155; i++) {
       int x = random.nextInt(width);
       int y = random.nextInt(height);
       int xl = random.nextInt(12);
       int yl = random.nextInt(12);
       g.drawLine(x, y, x + xl, y + yl);
     }



















     String sRand = "";
     int car = captchars.length - 1;
     for (int i = 0; i < 4; i++) {
       String rand = "" + captchars[(this.generator.nextInt(car) + 1)];
       sRand = sRand + rand;
       g.setColor(new Color(30 + random.nextInt(80), 30 + random.nextInt(80), 30 + random.nextInt(80)));
       g.drawString(rand, 15 * i + 4, 16);
     }

     ThreadContextHolder.getSessionContext().setAttribute("valid_code" + vtype, sRand);


     g.dispose();


     ImageIO.write(image, "JPEG", resp.getOutputStream());
     resp.getOutputStream().flush();
     resp.getOutputStream().close();
   }




































































   private void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, int thickness, Color c)
   {
     g.setColor(c);
     int dX = x2 - x1;
     int dY = y2 - y1;

     double lineLength = Math.sqrt(dX * dX + dY * dY);

     double scale = thickness / (2.0D * lineLength);



     double ddx = -scale * dY;
     double ddy = scale * dX;
     ddx += (ddx > 0.0D ? 0.5D : -0.5D);
     ddy += (ddy > 0.0D ? 0.5D : -0.5D);
     int dx = (int)ddx;
     int dy = (int)ddy;


     int[] xPoints = new int[4];
     int[] yPoints = new int[4];

     xPoints[0] = (x1 + dx);
     yPoints[0] = (y1 + dy);
     xPoints[1] = (x1 - dx);
     yPoints[1] = (y1 - dy);
     xPoints[2] = (x2 - dx);
     yPoints[2] = (y2 - dy);
     xPoints[3] = (x2 + dx);
     yPoints[3] = (y2 + dy);

     g.fillPolygon(xPoints, yPoints, 4);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\utils\ValidCodeServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */