 package com.enation.framework.cache;

 import org.apache.log4j.Logger;








 public abstract class AbstractCacheProxy<T>
 {
   protected final Logger logger = Logger.getLogger(getClass());
   protected ICache<T> cache;

   public AbstractCacheProxy() {}

   public AbstractCacheProxy(String cacheName)
   {
     this.cache = CacheFactory.getCache(cacheName);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\cache\AbstractCacheProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */