 package com.enation.app.shop.core.model.support;


 public class DiscountPrice
 {
   private Double orderPrice;
   private Double shipFee;
   private Integer point;

   public Double getOrderPrice()
   {
     return this.orderPrice;
   }

   public void setOrderPrice(Double orderPrice) { this.orderPrice = orderPrice; }

   public Double getShipFee() {
     return this.shipFee;
   }

   public void setShipFee(Double shipFee) { this.shipFee = shipFee; }

   public Integer getPoint() {
     return this.point;
   }

   public void setPoint(Integer point) { this.point = point; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\support\DiscountPrice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */