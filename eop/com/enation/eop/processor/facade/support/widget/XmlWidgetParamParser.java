 package com.enation.eop.processor.facade.support.widget;

 import com.enation.eop.processor.widget.IWidgetParamParser;
 import com.enation.eop.processor.widget.WidgetXmlUtil;
 import com.enation.eop.resource.IThemeManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.resource.model.Theme;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import java.util.Map;






 public class XmlWidgetParamParser
   implements IWidgetParamParser
 {
   private IThemeManager themeManager;

   public Map<String, Map<String, Map<String, String>>> parse()
   {
     EopSite site = EopContext.getContext().getCurrentSite();
     Theme theme = this.themeManager.getTheme(site.getThemeid());
     String contextPath = EopContext.getContext().getContextPath();
     String path = EopSetting.EOP_PATH + contextPath + EopSetting.THEMES_STORAGE_PATH + "/" + theme.getPath() + "/widgets.xml";





     return WidgetXmlUtil.parse(path);
   }

   public void setThemeManager(IThemeManager themeManager) { this.themeManager = themeManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\support\widget\XmlWidgetParamParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */