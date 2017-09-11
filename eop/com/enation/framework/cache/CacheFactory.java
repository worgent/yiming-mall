 package com.enation.framework.cache;



 public final class CacheFactory
 {
   public static final String APP_CACHE_NAME_KEY = "appCache";

   public static final String THEMEURI_CACHE_NAME_KEY = "themeUriCache";

   public static final String SITE_CACHE_NAME_KEY = "siteCache";

   public static final String WIDGET_CACHE_NAME_KEY = "widgetCache";


   public static <T> ICache<T> getCache(String name)
   {
     ICache<T> ehCache = new EhCacheImpl(name);
     return ehCache;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\cache\CacheFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */