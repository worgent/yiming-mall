 package com.enation.eop.processor.core.freemarker;

 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.utils.EopUtil;
 import com.enation.eop.sdk.utils.FreeMarkerUtil;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.TagCreator;
 import com.sun.xml.messaging.saaj.util.ByteOutputStream;
 import freemarker.template.Configuration;
 import freemarker.template.DefaultObjectWrapper;
 import freemarker.template.Template;
 import freemarker.template.TemplateModelException;
 import java.io.OutputStreamWriter;
 import java.io.Writer;
 import java.util.HashMap;
 import java.util.Locale;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;









 public final class FreeMarkerPaser
 {
   private static final Logger log = Logger.getLogger(FreeMarkerPaser.class);
   private static ThreadLocal<FreeMarkerPaser> managerLocal = new ThreadLocal();
   private Class clazz;

   public FreeMarkerPaser() { this.data = new HashMap();
     this.clazz = null;
     this.pageFolder = null;
   }

   public void setClz(Class clz) {
     this.clazz = clz;
   }





   public static final FreeMarkerPaser getInstance()
   {
     if (managerLocal.get() == null) {
       throw new RuntimeException("freemarker paser is null");
     }
     FreeMarkerPaser fmp = (FreeMarkerPaser)managerLocal.get();

     fmp.setPageFolder(null);
     fmp.setWrapPath(true);
     fmp.setPageName(null);
     return fmp;
   }

   public static final FreeMarkerPaser getCurrInstance() {
     if (managerLocal.get() == null) {
       throw new RuntimeException("freemarker paser is null");
     }
     FreeMarkerPaser fmp = (FreeMarkerPaser)managerLocal.get();

     return fmp;
   }

   public static final void set(FreeMarkerPaser fp) {
     managerLocal.set(fp);
   }

   public static final void remove() {
     managerLocal.remove();
   }


   public FreeMarkerPaser(Class clz)
   {
     this.clazz = clz;
     this.data = new HashMap();
   }

   public FreeMarkerPaser(Class clz, String folder) {
     this.clazz = clz;
     this.pageFolder = folder;
     this.data = new HashMap();
   }






   public void putData(String key, Object value)
   {
     if ((key != null) && (value != null))
       this.data.put(key, value);
   }

   public void putData(Map map) {
     if (map != null)
       this.data.putAll(map);
   }

   public Object getData(String key) {
     if (key == null) {
       return null;
     }
     return this.data.get(key);
   }

   private boolean wrapPath = true;
   private Map<String, Object> data;

   public void setWrapPath(boolean wp) { this.wrapPath = wp; }

   public String proessPageContent()
   {
     String name = this.clazz.getSimpleName();


     try
     {
       int pos = name.indexOf("$$EnhancerByCGLIB$$");
       if (pos > 0) {
         name = name.substring(0, pos);
       }

       this.pageExt = (this.pageExt == null ? ".html" : this.pageExt);
       name = this.pageName == null ? name : this.pageName;

       Configuration cfg = getCfg();


       Template temp = cfg.getTemplate(name + this.pageExt);
       ByteOutputStream stream = new ByteOutputStream();
       Writer out = new OutputStreamWriter(stream);
       temp.process(this.data, out);
       out.flush();
       String content = stream.toString();
       if (this.wrapPath)
       {
         content = EopUtil.wrapjavascript(content, getResPath()); }
       return EopUtil.wrapcss(content, getResPath());

     }
     catch (Exception e)
     {
       log.error("template", e);

       log.debug(this.clazz.getSimpleName() + " pageFolder [" + this.pageFolder + "] pagename [" + this.pageName + "]");
       e.printStackTrace();
     }
     return "widget  processor error";
   }






   private String pathPrefix;





   private String pageName;




   private String pageExt;




   private String pageFolder;




   private Configuration getCfg()
   {
     Configuration cfg = FreeMarkerUtil.getCfg();

     this.pathPrefix = (this.pathPrefix == null ? "" : this.pathPrefix);

     if (this.pageFolder == null) {
       cfg.setClassForTemplateLoading(this.clazz, this.pathPrefix);
     } else {
       cfg.setServletContextForTemplateLoading(ThreadContextHolder.getHttpRequest().getSession().getServletContext(), this.pageFolder);
     }
     try {
       cfg.setSharedVariable("request", ThreadContextHolder.getHttpRequest());
       cfg.setSharedVariable("newTag", new TagCreator());
     }
     catch (TemplateModelException e) {
       e.printStackTrace();
     }
     cfg.setObjectWrapper(new DefaultObjectWrapper());
     cfg.setDefaultEncoding("UTF-8");
     cfg.setLocale(Locale.CHINA);
     cfg.setEncoding(Locale.CHINA, "UTF-8");
     return cfg;
   }





   public void setPathPrefix(String path)
   {
     this.pathPrefix = path;
   }





   public void setPageName(String pageName)
   {
     this.pageName = pageName;
     if (this.clazz != null) {
       log.debug(this.clazz.getSimpleName() + "set pageName [" + pageName + "]");
     }
   }




   public void setPageExt(String pageExt)
   {
     this.pageExt = pageExt;
   }

   public void setPageFolder(String pageFolder) {
     this.pageFolder = pageFolder;
     if (this.clazz != null) {
       log.debug(this.clazz.getSimpleName() + "set folder [" + pageFolder + "]");
     }
   }




   private String getResPath()
   {
     String ctx = EopSetting.CONTEXT_PATH;
     ctx = ctx.equals("/") ? "" : ctx;
     if (this.pageFolder == null) {
       return ctx + "/resource/" + this.clazz.getPackage().getName().replaceAll("\\.", "/") + "/";
     }
     return ctx + this.pageFolder + "/";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\core\freemarker\FreeMarkerPaser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */