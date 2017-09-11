 package com.enation.app.cms.core.taglib.articlesearch;

 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import freemarker.core.Environment;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.io.Writer;
 import java.util.Map;






 public class ArticleSearchDirectiveModel
   implements TemplateDirectiveModel
 {
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.setPageName("article_search");
     String html = freeMarkerPaser.proessPageContent();
     env.getOut().write(html);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\taglib\articlesearch\ArticleSearchDirectiveModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */