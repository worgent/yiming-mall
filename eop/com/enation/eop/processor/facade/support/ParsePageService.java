 package com.enation.eop.processor.facade.support;

 import com.enation.eop.processor.facade.support.widget.WidgetHtmlGetter;
 import com.enation.eop.processor.widget.IWidgetHtmlGetter;
 import com.enation.eop.processor.widget.IWidgetParamParser;
 import com.enation.eop.resource.IThemeManager;
 import com.enation.eop.resource.IThemeUriManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.resource.model.Theme;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.util.Map;
 import java.util.Set;
 import java.util.regex.Pattern;
 import javax.servlet.http.HttpServletRequest;

 public class ParsePageService
 {
   private IWidgetParamParser widgetParamParser;
   private IThemeManager themeManager;
   private IThemeUriManager themeUriManager;

   public void parse()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String uri = com.enation.framework.util.RequestUtil.getRequestUrl(request);
     parse(uri);
   }





   private void putData(String key, String value)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     request.setAttribute(key, value);
   }

   public void parse(String uri) {
     String mode = "no";

     if (uri.indexOf("?mode") > 0) {
       mode = "edit";
     }


     if (uri.indexOf('?') > 0) {
       uri = uri.substring(0, uri.indexOf('?'));
     }

     EopSite site = EopContext.getContext().getCurrentSite();
     Theme theme = this.themeManager.getTheme(site.getThemeid());
     String themePath = theme.getPath();

     com.enation.eop.resource.model.ThemeUri themeUri = this.themeUriManager.getPath(uri);
     String tplFileName = themeUri.getPath();


     Map<String, Map<String, Map<String, String>>> pages = this.widgetParamParser.parse();


     Map<String, Map<String, String>> widgets = (Map)pages.get(tplFileName);
     IWidgetHtmlGetter htmlGetter;
     if (widgets != null) {
       htmlGetter = new WidgetHtmlGetter();


       Map<String, String> mainparams = (Map)widgets.get("main");
       if (mainparams != null) {
         String content = htmlGetter.process(mainparams, tplFileName);
         putData("widget_" + (String)mainparams.get("id"), content);
         widgets.remove("main");
       }

       Set<String> idSet = widgets.keySet();

       for (String id : idSet) {
         Map<String, String> params = (Map)widgets.get(id);
         params.put("mode", mode);

         boolean isCurrUrl = matchUrl(uri, id);


         if ((!tplFileName.equals("/default.html")) || (!id.startsWith("/")) || (isCurrUrl))
         {


           String content = htmlGetter.process(params, tplFileName);

           if ((tplFileName.equals("/default.html")) && (id.startsWith("/")) && (isCurrUrl)) {
             putData("widget_main", content);
           } else {
             putData("widget_" + id, content);
           }
         }
       }
     }

     Map<String, Map<String, String>> commonWidgets = (Map)pages.get("common");
//     IWidgetHtmlGetter htmlGetter;
    if (commonWidgets != null) {
       htmlGetter = new WidgetHtmlGetter();
       Set<String> idSet = commonWidgets.keySet();
       for (String id : idSet) {
         Map<String, String> params = (Map)commonWidgets.get(id);
         String content = htmlGetter.process(params, tplFileName);
         putData("widget_" + id, content);
       }
     }
     try
     {
       StringBuffer context = new StringBuffer();


       if (EopSetting.RESOURCEMODE.equals("1")) {
         context.append(EopSetting.IMG_SERVER_DOMAIN);
       }
       if (EopSetting.RESOURCEMODE.equals("2")) {
         context.append(EopSetting.CONTEXT_PATH);
       }
       String contextPath = EopContext.getContext().getContextPath();
       context.append(contextPath);
       context.append(EopSetting.THEMES_STORAGE_PATH);
       context.append("/");
       context.append(themePath + "/");
       putData("context", context.toString());
       putData("staticserver", EopSetting.IMG_SERVER_DOMAIN);
     } catch (Exception e) {
       e.printStackTrace();
     }
   }

   private boolean matchUrl(String uri, String targetUri) {
     Pattern p = Pattern.compile(targetUri, 34);
     java.util.regex.Matcher m = p.matcher(uri);
     return m.find();
   }

   public static void main(String[] args) {
     String url = "/goods-1.html";
     if (url.indexOf('?') > 0)
       url = url.substring(0, url.indexOf('?'));
   }

   public void setWidgetParamParser(IWidgetParamParser widgetParamParser) {
     this.widgetParamParser = widgetParamParser;
   }

   public void setThemeManager(IThemeManager themeManager) {
     this.themeManager = themeManager;
   }

   public IThemeUriManager getThemeUriManager() {
     return this.themeUriManager;
   }

   public void setThemeUriManager(IThemeUriManager themeUriManager) {
     this.themeUriManager = themeUriManager;
   }

   public IWidgetParamParser getWidgetParamParser() {
     return this.widgetParamParser;
   }

   public IThemeManager getThemeManager() {
     return this.themeManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\support\ParsePageService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */