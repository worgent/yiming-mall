 package com.enation.app.shop.core.tag;

 import com.enation.app.shop.core.service.IGoodsTagManager;
 import com.enation.app.shop.core.service.ISellBackManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import java.util.Random;
 import org.springframework.stereotype.Component;


 @Component
 public class GoodsRandomTag
   extends BaseFreeMarkerTag
 {
   private ISellBackManager sellBackManager;
   private IGoodsTagManager goodsTagManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer tagid = (Integer)params.get("tagid");
     List list = this.goodsTagManager.getGoodsList(tagid.intValue());
     int totleNum = list.size();

     Random random = new Random();
     int rdNum = random.nextInt(totleNum);
     Map map = (Map)list.get(rdNum);

     return map;
   }

   public ISellBackManager getSellBackManager()
   {
     return this.sellBackManager;
   }

   public void setSellBackManager(ISellBackManager sellBackManager) { this.sellBackManager = sellBackManager; }


   public IGoodsTagManager getGoodsTagManager()
   {
     return this.goodsTagManager;
   }

   public void setGoodsTagManager(IGoodsTagManager goodsTagManager) {
     this.goodsTagManager = goodsTagManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\GoodsRandomTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */