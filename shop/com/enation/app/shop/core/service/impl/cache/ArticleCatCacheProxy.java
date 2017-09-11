 package com.enation.app.shop.core.service.impl.cache;

 import com.enation.app.shop.core.model.ArticleCat;
 import com.enation.app.shop.core.service.IArticleCatManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import java.util.List;
 import org.apache.log4j.Logger;

 public class ArticleCatCacheProxy extends com.enation.framework.cache.AbstractCacheProxy<List<ArticleCat>> implements IArticleCatManager
 {
   public static final String cacheName = "article_cat";
   private IArticleCatManager articleCatManager;

   public ArticleCatCacheProxy(IArticleCatManager articleCatManager)
   {
     super("article_cat");
     this.articleCatManager = articleCatManager;
   }

   private String getKey() {
     EopSite site = EopContext.getContext().getCurrentSite();
     return "article_cat_" + site.getUserid() + "_" + site.getId();
   }

   private void cleanCache() { EopSite site = EopContext.getContext().getCurrentSite();
     this.cache.remove(getKey());
   }

   public int delete(int catId)
   {
     int r = this.articleCatManager.delete(catId);
     if (r == 0) {
       cleanCache();
     }
     return r;
   }

   public ArticleCat getById(int catId)
   {
     return this.articleCatManager.getById(catId);
   }

   public List listChildById(Integer catId)
   {
     List<ArticleCat> catList = (List)this.cache.get(getKey());
     if (catList == null) {
       catList = this.articleCatManager.listChildById(catId);
       this.cache.put(getKey(), catList);
       if (this.logger.isDebugEnabled()) {
         this.logger.debug("load article cat form database");
       }
     }
     else if (this.logger.isDebugEnabled()) {
       this.logger.debug("load article cat form cache");
     }


     return catList;
   }

   public List listHelp(int catId)
   {
     return this.articleCatManager.listHelp(catId);
   }

   public void saveAdd(ArticleCat cat)
   {
     this.articleCatManager.saveAdd(cat);
     cleanCache();
   }

   public void saveSort(int[] catIds, int[] catSorts)
   {
     this.articleCatManager.saveSort(catIds, catSorts);
     cleanCache();
   }

   public void update(ArticleCat cat)
   {
     this.articleCatManager.update(cat);
     cleanCache();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\cache\ArticleCatCacheProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */