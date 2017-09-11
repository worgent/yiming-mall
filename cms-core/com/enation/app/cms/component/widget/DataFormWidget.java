 package com.enation.app.cms.component.widget;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.ArticlePluginBundle;
 import com.enation.app.cms.core.plugin.IFieldDispalyEvent;
 import com.enation.app.cms.core.service.IDataFieldManager;
 import com.enation.app.cms.core.service.IDataManager;
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
 import org.apache.log4j.Logger;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;











 @Component("dataForm")
 @Scope("prototype")
 public class DataFormWidget
   extends AbstractWidget
 {
   private IDataManager dataManager;
   private IDataFieldManager dataFieldManager;
   private ArticlePluginBundle articlePluginBundle;

   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String action = request.getParameter("action");
     if ("post".equals(action)) {
       try {
         Integer modelid = Integer.valueOf(request.getParameter("modelid"));
         Integer catid = Integer.valueOf(request.getParameter("catid"));
         this.dataManager.add(modelid, catid);
         setPageFolder("/commons");
         setPageName("json");
         putData("json", "{result:1}");
       } catch (RuntimeException e) {
         this.logger.error(e.getMessage(), e);
         putData("json", "{result:0,message:'" + e.getMessage() + "'}");
       }
     } else {
       Integer modelid = Integer.valueOf((String)params.get("modelid"));
       Integer catid = Integer.valueOf((String)params.get("catid"));
       putData("modelid", modelid);
       putData("catid", catid);

       String page = (String)params.get("formpage");
       String folder = (String)params.get("formfolder");

       String message = (String)params.get("message");
       message = StringUtil.isEmpty(message) ? "信息提交成功" : message;
       putData("message", message);
       String html = getFormHtml(modelid, page, folder);
       putData("html", html);
     }
   }

   private String getFormHtml(Integer modelid, String page, String folder)
   {
     Map data = new HashMap();
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

   public IDataManager getDataManager() {
     return this.dataManager;
   }

   public void setDataManager(IDataManager dataManager) {
     this.dataManager = dataManager;
   }

   public IDataFieldManager getDataFieldManager() {
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


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\widget\DataFormWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */