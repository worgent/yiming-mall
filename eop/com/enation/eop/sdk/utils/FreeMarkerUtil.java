 package com.enation.eop.sdk.utils;

 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.directive.DirectiveFactory;
 import com.sun.xml.messaging.saaj.util.ByteOutputStream;
 import freemarker.cache.MruCacheStorage;
 import freemarker.template.Configuration;
 import freemarker.template.DefaultObjectWrapper;
 import freemarker.template.Template;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.File;
 import java.io.IOException;
 import java.io.OutputStreamWriter;
 import java.io.Writer;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Locale;
 import java.util.Map;
 import java.util.Set;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;












 public class FreeMarkerUtil
 {
   public static Configuration getServletCfg(String pageFolder)
   {
     Configuration cfg = new Configuration();
     cfg.setServletContextForTemplateLoading(ThreadContextHolder.getHttpRequest().getSession().getServletContext(), pageFolder);

     cfg.setObjectWrapper(new DefaultObjectWrapper());
     return cfg;
   }

   public static Configuration getCfg()
   {
     Configuration cfg = new Configuration();
     cfg.setTemplateUpdateDelay(6000);
     cfg.setCacheStorage(new MruCacheStorage(20, 250));

     Map<String, TemplateDirectiveModel> directiveMap = DirectiveFactory.getCommonDirective();
     Iterator<String> keyIter = directiveMap.keySet().iterator();

     while (keyIter.hasNext()) {
       String key = (String)keyIter.next();
       cfg.setSharedVariable(key, (TemplateModel)directiveMap.get(key));
     }


     cfg.setObjectWrapper(new DefaultObjectWrapper());
     cfg.setDefaultEncoding("UTF-8");
     cfg.setLocale(Locale.CHINA);
     cfg.setEncoding(Locale.CHINA, "UTF-8");
     cfg.setNumberFormat("#.##");
     return cfg;
   }



   public static Configuration getFolderCfg(String pageFolder)
     throws IOException
   {
     Configuration cfg = getCfg();
     cfg.setDirectoryForTemplateLoading(new File(pageFolder));

     return cfg;
   }

   public static void test()
   {
     try
     {
       Configuration cfg = getFolderCfg("D:/workspace/eopnew/eop/WebContent/WEB-INF/classes/com/enation/app/shop/core/widget/goodscat");

       Template temp = cfg.getTemplate("GoodsCat.html");
       ByteOutputStream stream = new ByteOutputStream();

       Writer out = new OutputStreamWriter(stream, "UTF-8");
       temp.process(new HashMap(), out);

       out.flush();
        String html;
       html = stream.toString();
     } catch (IOException e) {
       e.printStackTrace();
     } catch (TemplateException e) {
       e.printStackTrace();
     }
   }

   public static void main(String[] args)
     throws IOException, TemplateException
   {
     Configuration cfg = getFolderCfg("D:/workspace/eopnew/eop/WebContent/WEB-INF/classes/com/enation/app/shop/core/widget/goodscat");

     Template temp = cfg.getTemplate("GoodsCat.html");
     ByteOutputStream stream = new ByteOutputStream();

     Writer out = new OutputStreamWriter(stream, "UTF-8");
     temp.process(new HashMap(), out);

     out.flush();
     String html = stream.toString();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\utils\FreeMarkerUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */