 package com.enation.app.shop.core.model;

 import java.util.List;




 public class Allocation
 {
   private int orderid;
   private int shipDepotId;
   private List<AllocationItem> itemList;

   public int getOrderid()
   {
     return this.orderid;
   }

   public void setOrderid(int orderid) { this.orderid = orderid; }

   public int getShipDepotId() {
     return this.shipDepotId;
   }

   public void setShipDepotId(int shipDepotId) { this.shipDepotId = shipDepotId; }

   public List<AllocationItem> getItemList() {
     return this.itemList;
   }

   public void setItemList(List<AllocationItem> itemList) { this.itemList = itemList; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\Allocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */