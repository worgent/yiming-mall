 package com.enation.app.cms.component.plugin;

 import com.enation.app.base.core.model.SiteMapUrl;
 import com.enation.app.base.core.plugin.IRecreateMapEvent;
 import com.enation.app.base.core.service.ISitemapManager;
 import com.enation.app.cms.core.model.DataCat;
 import com.enation.app.cms.core.service.IDataCatManager;
 import com.enation.app.cms.core.service.IDataManager;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;








 @Component
 public class CmsRecreateMapPlugin
   extends AutoRegisterPlugin
   implements IRecreateMapEvent
 {
   private ISitemapManager sitemapManager;
   private IDataCatManager dataCatManager;
   private IDataManager dataManager;

   public void register() {}

   public void onRecreateMap()
   {
     List<DataCat> listCat = this.dataCatManager.listAllChildren(Integer.valueOf(0));
    DataCat cat;
     for (Iterator i$ = listCat.iterator(); i$.hasNext();) { cat = (DataCat)i$.next();
       if (cat.getTositemap().intValue() == 1) {
         List<Map> list = this.dataManager.list(cat.getCat_id());
         for (Map map : list) {
           SiteMapUrl url = new SiteMapUrl();
           url.setLoc("/" + cat.getDetail_url() + "-" + cat.getCat_id() + "-" + map.get("id") + ".html");
           url.setLastmod(Long.valueOf(System.currentTimeMillis()));
           this.sitemapManager.addUrl(url);
         }
       }
     }

   }

   public ISitemapManager getSitemapManager() { return this.sitemapManager; }

   public void setSitemapManager(ISitemapManager sitemapManager)
   {
     this.sitemapManager = sitemapManager;
   }

   public IDataCatManager getDataCatManager() {
     return this.dataCatManager;
   }

   public void setDataCatManager(IDataCatManager dataCatManager) {
     this.dataCatManager = dataCatManager;
   }

   public IDataManager getDataManager() {
     return this.dataManager;
   }

   public void setDataManager(IDataManager dataManager) {
     this.dataManager = dataManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\plugin\CmsRecreateMapPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */