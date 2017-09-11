 package com.enation.app.shop.component.goodscore.plugin.goodsbase;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
 import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;









 @Component
 public class GoodsBasePlugin
   extends AbstractGoodsPlugin
   implements IGoodsTabShowEvent
 {
   private IGoodsCatManager goodsCatManager;

   public String getAddHtml(HttpServletRequest request)
   {
     int catid = StringUtil.toInt(request.getParameter("catid"), true);


     List<Cat> parentList = this.goodsCatManager.getParents(catid);


     Cat currentCat = (Cat)parentList.get(parentList.size() - 1);


     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setPageName("goods_add");
     freeMarkerPaser.putData("typeid", currentCat.getType_id());
     freeMarkerPaser.putData("catid", Integer.valueOf(catid));

     freeMarkerPaser.putData("optype", request.getParameter("optype"));
     freeMarkerPaser.putData("parentList", parentList);


     return freeMarkerPaser.proessPageContent();
   }





   public String getEditHtml(Map goods, HttpServletRequest request)
   {
     int catid = StringUtil.toInt(goods.get("cat_id").toString(), true);


     List<Cat> parentList = this.goodsCatManager.getParents(catid);


     Cat currentCat = (Cat)parentList.get(parentList.size() - 1);

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.putData("goodsView", goods);
     freeMarkerPaser.putData("parentList", parentList);
     freeMarkerPaser.putData("typeid", currentCat.getType_id());
     freeMarkerPaser.putData("catid", Integer.valueOf(catid));
     freeMarkerPaser.putData("optype", request.getParameter("optype"));
     freeMarkerPaser.setPageName("goods_edit");

     return freeMarkerPaser.proessPageContent();
   }






   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {}





   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
     throws RuntimeException
   {}





   public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {}





   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {}





   public String getTabName()
   {
     return "基本信息";
   }


   public int getOrder()
   {
     return 1;
   }

   public IGoodsCatManager getGoodsCatManager()
   {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager)
   {
     this.goodsCatManager = goodsCatManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\goodscore\plugin\goodsbase\GoodsBasePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */