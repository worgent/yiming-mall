 package com.enation.eop.processor.facade.support;

 import com.enation.eop.processor.IPageParser;
 import com.enation.eop.processor.PageWrapper;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.utils.FreeMarkerUtil;
 import com.enation.eop.sdk.utils.HtmlUtil;
 import com.sun.xml.messaging.saaj.util.ByteOutputStream;
 import freemarker.template.Configuration;
 import freemarker.template.Template;
 import java.io.OutputStreamWriter;
 import java.io.Writer;
 import java.util.HashMap;
 import java.util.Map;






 public class PageEditModeWrapper
   extends PageWrapper
 {
   public PageEditModeWrapper(IPageParser paser)
   {
     super(paser);
   }

   public String parse(String url) {
     String content = super.parse(url);
     String script = getToolBarScript();
     String html = getToolBarHtml();

     content = wrapPageMain(content);
     content = HtmlUtil.appendTo(content, "head", script);
     content = HtmlUtil.insertTo(content, "body", html);
     return content;
   }

   private String getToolBarScript() {
     String eopFld = EopSetting.EOP_PATH + "/eop/";
     try {
       Map<String, String> data = new HashMap();
       data.put("staticserver", EopSetting.IMG_SERVER_DOMAIN);
       data.put("ctx", EopSetting.CONTEXT_PATH);
       EopSite site = EopContext.getContext().getCurrentSite();
       data.put("userid", "" + site.getUserid());
       data.put("siteid", "" + site.getId());

       Configuration cfg = FreeMarkerUtil.getFolderCfg(eopFld);
       Template temp = cfg.getTemplate("widget_tool_script.html");
       ByteOutputStream stream = new ByteOutputStream();
       Writer out = new OutputStreamWriter(stream);
       temp.process(data, out);
       out.flush();
       return stream.toString();
     } catch (Exception e) {
       e.printStackTrace(); }
     return "";
   }


   private String getToolBarHtml()
   {
     String eopFld = EopSetting.EOP_PATH + "/eop/";
     try {
       Map<String, String> data = new HashMap();
       data.put("staticserver", EopSetting.IMG_SERVER_DOMAIN);
       data.put("ctx", EopSetting.CONTEXT_PATH);
       EopSite site = EopContext.getContext().getCurrentSite();
       data.put("userid", "" + site.getUserid());
       data.put("siteid", "" + site.getId());
       Configuration cfg = FreeMarkerUtil.getFolderCfg(eopFld);
       Template temp = cfg.getTemplate("widget_toolbar.html");
       ByteOutputStream stream = new ByteOutputStream();
       Writer out = new OutputStreamWriter(stream);
       temp.process(data, out);
       out.flush();
       return stream.toString();
     } catch (Exception e) {
       e.printStackTrace(); }
     return "";
   }

   private String wrapPageMain(String content)
   {
     content = HtmlUtil.insertTo(content, "body", "<div id=\"pagemain\">");
     content = HtmlUtil.appendTo(content, "body", "</div>");
     return content;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\support\PageEditModeWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */