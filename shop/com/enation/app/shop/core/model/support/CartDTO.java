 package com.enation.app.shop.core.model.support;

 import java.util.List;

 public class CartDTO {
   private List itemList;
   private List ruleList;
   private Double orderPrice;

   public List getItemList() {
     return this.itemList;
   }

   public void setItemList(List itemList) { this.itemList = itemList; }

   public Double getOrderPrice() {
     return this.orderPrice;
   }

   public void setOrderPrice(Double orderPrice) { this.orderPrice = orderPrice; }

   public List getRuleList() {
     return this.ruleList;
   }

   public void setRuleList(List ruleList) { this.ruleList = ruleList; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\support\CartDTO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */