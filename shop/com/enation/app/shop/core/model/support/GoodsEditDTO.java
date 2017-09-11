 package com.enation.app.shop.core.model.support;

 import java.util.Map;

 public class GoodsEditDTO
 {
   private Map goods;
   private Map<Integer, String> htmlMap;

   public Map getGoods()
   {
     return this.goods;
   }

   public void setGoods(Map goods) {
     this.goods = goods;
   }

   public Map<Integer, String> getHtmlMap() {
     return this.htmlMap;
   }

   public void setHtmlMap(Map<Integer, String> htmlMap) {
     this.htmlMap = htmlMap;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\support\GoodsEditDTO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */