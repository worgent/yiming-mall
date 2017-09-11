 package com.enation.framework.pager;

 import com.enation.framework.pager.impl.SimplePageHtmlBuilder;
 import freemarker.core.Environment;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.io.Writer;
 import java.util.Map;





 public class PagerDirectiveModel
   implements TemplateDirectiveModel
 {
   public void execute(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
     throws TemplateException, IOException
   {
     String pageno = params.get("pageno").toString();
     String pagesize = params.get("pagesize").toString();
     String totalcount = params.get("totalcount").toString();
     int _pageNum = Integer.valueOf(pageno).intValue();
     int _totalCount = Integer.valueOf(totalcount).intValue();
     int _pageSize = Integer.valueOf(pagesize).intValue();
     SimplePageHtmlBuilder pageHtmlBuilder = new SimplePageHtmlBuilder(_pageNum, _totalCount, _pageSize);
     String html = pageHtmlBuilder.buildPageHtml();
     env.getOut().write(html);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\pager\PagerDirectiveModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */