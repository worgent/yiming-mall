 package com.enation.app.base.core.action;

 import com.enation.framework.action.WWAction;
 import net.sf.ehcache.Cache;
 import net.sf.ehcache.CacheManager;
 import net.sf.ehcache.statistics.LiveCacheStatistics;

 public class CacheAction extends WWAction
 {
   public String execute()
   {
     CacheManager manager = CacheManager.getInstance();
     Cache cache = manager.getCache("widgetCache");

     LiveCacheStatistics statistis = cache.getLiveCacheStatistics();
     boolean memory = statistis.isStatisticsEnabled();

     return "list";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\CacheAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */