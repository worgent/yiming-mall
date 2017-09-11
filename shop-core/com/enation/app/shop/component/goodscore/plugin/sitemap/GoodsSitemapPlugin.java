 package com.enation.app.shop.component.goodscore.plugin.sitemap;

 import com.enation.app.base.core.model.SiteMapUrl;
 import com.enation.app.base.core.plugin.IRecreateMapEvent;
 import com.enation.app.base.core.service.ISitemapManager;
 import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
 import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.framework.util.DateUtil;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;









 @Component
 public class GoodsSitemapPlugin
   extends AbstractGoodsPlugin
   implements IGoodsDeleteEvent, IRecreateMapEvent
 {
   private ISitemapManager sitemapManager;
   private IGoodsManager goodsManager;

   public void addTabs() {}

   public String getAddHtml(HttpServletRequest request)
   {
     return null;
   }


   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {}

   public String getEditHtml(Map goods, HttpServletRequest request)
   {
     return null;
   }

   public void onAfterGoodsAdd(Map goods, HttpServletRequest request) throws RuntimeException
   {
     SiteMapUrl url = new SiteMapUrl();
     url.setLoc("/goods-" + goods.get("goods_id").toString() + ".html");
     url.setLastmod(Long.valueOf(DateUtil.getDatelineLong()));


     this.sitemapManager.addUrl(url);
   }

   public void onRecreateMap()
   {
     List<Map> list = this.goodsManager.list();
     for (Map map : list) {
       SiteMapUrl url = new SiteMapUrl();
       url.setLoc("/goods-" + map.get("goods_id") + ".html");
       url.setLastmod(Long.valueOf(DateUtil.getDatelineLong()));
       this.sitemapManager.addUrl(url);
     }
   }

   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
   {
     this.sitemapManager.editUrl("/goods-" + goods.get("goods_id").toString() + ".html", Long.valueOf(DateUtil.getDatelineLong()));
   }


   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {}


   public void onGoodsDelete(Integer[] goodsid)
   {
     Integer[] arr$ = goodsid;int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { int i = arr$[i$].intValue();
       this.sitemapManager.delete("/goods-" + i + ".html");
     }
   }

   public String getAuthor()
   {
     return "lzf";
   }

   public String getId() {
     return "goods-sitemap";
   }

   public String getName() {
     return "商品数据sitemap记录插件";
   }

   public String getType() {
     return "sitemap";
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

   public IGoodsManager getGoodsManager() {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\goodscore\plugin\sitemap\GoodsSitemapPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */