 package com.enation.app.cms.core.service.impl.cache;

 import com.enation.app.cms.core.model.DataCat;
 import com.enation.app.cms.core.service.IDataCatManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.framework.cache.AbstractCacheProxy;
 import com.enation.framework.cache.ICache;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.log4j.Logger;




 public class DataCatCacheProxy
   extends AbstractCacheProxy<Map>
   implements IDataCatManager
 {
   public static final String cacheName = "cms_data_cat";
   private IDataCatManager articleCatManager;

   public DataCatCacheProxy(IDataCatManager articleCatManager)
   {
     super("cms_data_cat");
     this.articleCatManager = articleCatManager;
   }

   private String getKey(int catid) { return "cms_data_cat_cat_" + catid; }







   private void put(String key, List<DataCat> list)
   {
     EopSite site = EopContext.getContext().getCurrentSite();
     String mainkey = "cms_data_cat_" + site.getUserid() + "_" + site.getId();
     Map catCache = (Map)this.cache.get(mainkey);


     if (catCache == null) {
       catCache = new HashMap();
       this.cache.put(mainkey, catCache);
     }
     catCache.put(key, list);
   }






   private List<DataCat> get(String key)
   {
     EopSite site = EopContext.getContext().getCurrentSite();
     String mainkey = "cms_data_cat_" + site.getUserid() + "_" + site.getId();
     Map<String, List<DataCat>> catCache = (Map)this.cache.get(mainkey);
     if (catCache == null) {
       return null;
     }

     return (List)catCache.get(key);
   }



   private void cleanCache()
   {
     EopSite site = EopContext.getContext().getCurrentSite();
     String mainkey = "cms_data_cat_" + site.getUserid() + "_" + site.getId();
     this.cache.remove(mainkey);
   }

   public void add(DataCat cat) {
     this.articleCatManager.add(cat);
     cleanCache();
   }

   public int delete(Integer catid) {
     int r = this.articleCatManager.delete(catid);
     if (r == 0) {
       cleanCache();
     }
     return r;
   }

   public void edit(DataCat cat) {
     this.articleCatManager.edit(cat);
     cleanCache();
   }

   public DataCat get(Integer catid)
   {
     return this.articleCatManager.get(catid);
   }

   public List<DataCat> listAllChildren(Integer parentid) {
     List<DataCat> catList = get(getKey(parentid.intValue()));
     if (catList == null) {
       catList = this.articleCatManager.listAllChildren(parentid);
       put(getKey(parentid.intValue()), catList);
       if (this.logger.isDebugEnabled()) {
         this.logger.debug("load article cat form database");
       }
     }
     else if (this.logger.isDebugEnabled()) {
       this.logger.debug("load article cat form cache");
     }

     return catList;
   }

   public List<DataCat> listLevelChildren(Integer catid, Integer level) {
     String key = "cms_data_cat_levelcat_" + catid;
     List<DataCat> catList = get(key);
     if (catList == null) {
       catList = this.articleCatManager.listLevelChildren(catid, level);
       put(key, catList);
     }
     return catList;
   }

   public void saveSort(int[] catIds, int[] catSorts) {
     this.articleCatManager.saveSort(catIds, catSorts);
     cleanCache();
   }

   public List<DataCat> getParents(Integer catid)
   {
     return this.articleCatManager.getParents(catid);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\service\impl\cache\DataCatCacheProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */