 package com.enation.app.base.component.plugin;

 import com.enation.app.base.core.model.SiteMapUrl;
 import com.enation.app.base.core.plugin.IRecreateMapEvent;
 import com.enation.app.base.core.service.ISitemapManager;
 import com.enation.eop.resource.IThemeUriManager;
 import com.enation.eop.resource.model.ThemeUri;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.DateUtil;
 import java.util.List;
 import org.springframework.stereotype.Component;



 @Component
 public class ThemeUriSitemapPlugin
   extends AutoRegisterPlugin
   implements IRecreateMapEvent
 {
   private ISitemapManager sitemapManager;
   private IThemeUriManager themeUriManager;

   public void register() {}

   public void onRecreateMap()
   {
     List<ThemeUri> list = this.themeUriManager.list(null);
     for (ThemeUri uri : list) {
       if (uri.getSitemaptype() == 1) {
         SiteMapUrl url = new SiteMapUrl();
         url.setLoc(uri.getUri());
         url.setLastmod(Long.valueOf(DateUtil.getDatelineLong()));
         this.sitemapManager.addUrl(url);
       }
     }
   }

   public String getAuthor()
   {
     return "lzf";
   }

   public String getId() {
     return "themeUriSitemap";
   }

   public String getName() {
     return "地址转向定义重建站点地图";
   }

   public String getType() {
     return "themeUriSitemap";
   }

   public String getVersion() {
     return "v2.1.5";
   }


   public void perform(Object... params) {}

   public ISitemapManager getSitemapManager()
   {
     return this.sitemapManager;
   }

   public void setSitemapManager(ISitemapManager sitemapManager) {
     this.sitemapManager = sitemapManager;
   }

   public IThemeUriManager getThemeUriManager() {
     return this.themeUriManager;
   }

   public void setThemeUriManager(IThemeUriManager themeUriManager) {
     this.themeUriManager = themeUriManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\plugin\ThemeUriSitemapPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */