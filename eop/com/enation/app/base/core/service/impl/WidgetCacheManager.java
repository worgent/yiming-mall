 package com.enation.app.base.core.service.impl;

 import com.enation.app.base.core.service.ISettingService;
 import com.enation.app.base.core.service.IWidgetCacheManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.cache.CacheFactory;
 import com.enation.framework.cache.ICache;
 import java.util.HashMap;
 import java.util.Map;










 public class WidgetCacheManager
   implements IWidgetCacheManager
 {
   private ISettingService settingService;

   public void close()
   {
     if ("2".equals(EopSetting.RUNMODE)) {
       cleanSaasCache();
     } else {
       cleanSlCache();
     }

     Map<String, Map<String, String>> settings = new HashMap();
     Map<String, String> value = new HashMap();
     value.put("state", "close");
     settings.put("widgetCache", value);
     this.settingService.save(settings);
   }





   private void cleanSaasCache()
   {
     ICache widgetCache = CacheFactory.getCache("widgetCache");


     EopSite site = EopContext.getContext().getCurrentSite();
     String site_key = "widget_" + site.getUserid() + "_" + site.getId();


     widgetCache.remove(site_key);
   }





   private void cleanSlCache()
   {
     ICache widgetCache = CacheFactory.getCache("widgetCache");
     widgetCache.clear();
   }





   public void open()
   {
     Map<String, Map<String, String>> settings = new HashMap();
     Map<String, String> value = new HashMap();
     value.put("state", "open");
     settings.put("widgetCache", value);
     this.settingService.save(settings);
   }




   public boolean isOpen()
   {
     Map<String, Map<String, String>> settings = new HashMap();
     String state = this.settingService.getSetting("widgetCache", "state");
     return "open".equals(state);
   }

   public ISettingService getSettingService()
   {
     return this.settingService;
   }

   public void setSettingService(ISettingService settingService)
   {
     this.settingService = settingService;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\impl\WidgetCacheManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */