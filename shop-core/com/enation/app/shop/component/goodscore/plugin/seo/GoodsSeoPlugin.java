 package com.enation.app.shop.component.goodscore.plugin.seo;

 import com.enation.app.shop.core.model.Goods;
 import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
 import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;


















 @Component
 public class GoodsSeoPlugin
   extends AbstractGoodsPlugin
   implements IGoodsTabShowEvent
 {
   public void addTabs() {}

   public void registerPages() {}

   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {}

   public String getAddHtml(HttpServletRequest request)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setPageName("seo");
     return freeMarkerPaser.proessPageContent();
   }

   public String getEditHtml(Map goods, HttpServletRequest request) {
     FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
     freeMarkerPaser.setPageName("seo");
     freeMarkerPaser.putData("goods", goods);
     return freeMarkerPaser.proessPageContent();
   }




   public void onAfterGoodsAdd(Goods goods, HttpServletRequest request)
     throws RuntimeException
   {}




   public void onAfterGoodsAdd(Map goods, HttpServletRequest request) {}




   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {}



   public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {}



   public String getAuthor()
   {
     return "kingapex";
   }

   public String getId()
   {
     return "goodsseo";
   }

   public String getName()
   {
     return "商品SEO优化插件";
   }

   public String getType()
   {
     return "";
   }

   public String getVersion()
   {
     return "1.0";
   }



   public void perform(Object... params) {}



   public String getTabName()
   {
     return "SEO";
   }


   public int getOrder()
   {
     return 11;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\goodscore\plugin\seo\GoodsSeoPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */