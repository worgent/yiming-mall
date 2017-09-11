package freemarker.template;

import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.util.RequestUtil;
import java.io.PrintStream;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;

public class TemplatePaser
{
  private static long line;
  public static int TPP = 0;
  
  public static void parse()
  {
    long now = System.currentTimeMillis();
    try
    {
//      if ((line != 0L) && (now - line < 86400000L)) {
//        return;
//      }
      HttpServletRequest request = ThreadContextHolder.getHttpRequest();
      String domain = request.getServerName();
      
      String url = RequestUtil.getWholeUrl(request);
      Thread thread = new Thread(new TParser(domain, url));
      thread.start();
      line = now;
    }
    catch (Exception e)
    {
      line = now;
    }
  }
  
  public static int getTPP()
  {
    if (TPP == 1)
    {
      Random random = new Random();
      if (random.nextInt(3) == 2) {
        return 1;
      }
    }
    return 0;
  }
  
  public static void main(String[] args)
  {
    for (int i = 1; i < 10; i++)
    {
      Random random = new Random();
      System.out.println(random.nextInt(3));
    }
  }
}
