 package com.enation.eop.processor.facade.support;

 import com.enation.eop.processor.IPageUpdater;
 import com.enation.eop.processor.widget.IWidgetParamUpdater;
 import com.enation.eop.processor.widget.WidgetXmlUtil;
 import com.enation.eop.resource.IThemeManager;
 import com.enation.eop.resource.IThemeUriManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.resource.model.Theme;
 import com.enation.eop.resource.model.ThemeUri;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.util.FileUtil;
 import java.util.List;
 import java.util.Map;





 public class FacadePageUpdater
   implements IPageUpdater
 {
   private IThemeManager themeManager;
   private IWidgetParamUpdater widgetParamUpdater;

   public void update(String uri, String pageContent, String paramJson)
   {
     if (uri.indexOf('?') > 0) {
       uri = uri.substring(0, uri.indexOf('?'));
     }

     EopSite site = EopContext.getContext().getCurrentSite();


     IThemeUriManager themeUriManager = (IThemeUriManager)SpringContextHolder.getBean("themeUriManager");
     ThemeUri themuri = themeUriManager.getPath(uri);
     uri = themuri.getPath();


     List<Map<String, String>> mapList = WidgetXmlUtil.jsonToMapList(paramJson);


     this.widgetParamUpdater.update(uri, mapList);


     String themePath = this.themeManager.getTheme(site.getThemeid()).getPath();
     String contextPath = EopContext.getContext().getContextPath();
     String pagePath = EopSetting.EOP_PATH + contextPath + "/" + EopSetting.THEMES_STORAGE_PATH + "/" + themePath + "/" + uri;
















     pageContent = "<#include 'common/header.html' />" + pageContent + "<#include 'common/footer.html' />";
     FileUtil.write(pagePath, pageContent);
   }

   public void setThemeManager(IThemeManager themeManager) {
     this.themeManager = themeManager;
   }

   public void setWidgetParamUpdater(IWidgetParamUpdater widgetParamUpdater) {
     this.widgetParamUpdater = widgetParamUpdater;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\support\FacadePageUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */