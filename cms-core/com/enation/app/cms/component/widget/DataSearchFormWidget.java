 package com.enation.app.cms.component.widget;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.ArticlePluginBundle;
 import com.enation.app.cms.core.plugin.IFieldDispalyEvent;
 import com.enation.app.cms.core.service.IDataFieldManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.utils.FreeMarkerUtil;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.plugin.IPlugin;
 import com.enation.framework.util.StringUtil;
 import com.sun.xml.messaging.saaj.util.ByteOutputStream;
 import freemarker.template.Configuration;
 import freemarker.template.Template;
 import java.io.OutputStreamWriter;
 import java.io.Writer;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.log4j.Logger;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;











 @Component("dataSearchForm")
 @Scope("prototype")
 public class DataSearchFormWidget
   extends AbstractWidget
 {
   private IDataFieldManager dataFieldManager;
   private ArticlePluginBundle articlePluginBundle;

   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     String page = (String)params.get("formpage");
     String folder = (String)params.get("formfolder");
     Integer modelid = Integer.valueOf((String)params.get("modelid"));

     String html = getFormHtml(modelid, page, folder, params);
     putData("html", html);
   }

   private String getFormHtml(Integer modelid, String page, String folder, Map<String, String> params) {
     Map data = new HashMap();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String inputnames = (String)params.get("inputnames");
     if (!StringUtil.isEmpty(inputnames)) {
       String[] namear = StringUtils.split(inputnames, ",");
       for (String name : namear) {
         String value = request.getParameter(name);
         data.put(name, value);
       }
     }

     data.put("modelid", modelid);
     List<DataField> fieldList = this.dataFieldManager.list(modelid);
     for (DataField field : fieldList) {
       IPlugin plugin = this.articlePluginBundle.findPlugin(field.getShow_form());
       if ((plugin != null) &&
         ((plugin instanceof IFieldDispalyEvent))) {
         String inpuhtml = ((IFieldDispalyEvent)plugin).onDisplay(field, null);
         data.put(field.getEnglish_name() + "_input", inpuhtml);
       }
     }


     EopSite site = EopContext.getContext().getCurrentSite();
     String contextPath = EopContext.getContext().getContextPath();
     if (StringUtil.isEmpty(folder)) {
       folder = contextPath + "/themes/" + site.getThemepath();
     } else {
       folder = contextPath + "/themes/" + site.getThemepath() + "/" + folder;
     }
     try {
       Configuration cfg = FreeMarkerUtil.getFolderCfg(EopSetting.EOP_PATH + folder);
       Template temp = cfg.getTemplate(page + ".html");
       ByteOutputStream stream = new ByteOutputStream();

       Writer out = new OutputStreamWriter(stream);
       temp.process(data, out);

       out.flush();
       return stream.toString();
     }
     catch (Exception e)
     {
       this.logger.error(e.getMessage(), e);
       return "挂件解析出错" + e.getMessage();
     }
   }

   public IDataFieldManager getDataFieldManager()
   {
     return this.dataFieldManager;
   }

   public void setDataFieldManager(IDataFieldManager dataFieldManager) {
     this.dataFieldManager = dataFieldManager;
   }

   public ArticlePluginBundle getArticlePluginBundle() {
     return this.articlePluginBundle;
   }

   public void setArticlePluginBundle(ArticlePluginBundle articlePluginBundle) {
     this.articlePluginBundle = articlePluginBundle;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\widget\DataSearchFormWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */