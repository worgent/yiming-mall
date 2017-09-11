 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberCommentManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.b2b2c.core.service.order.IStoreOrderManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import org.springframework.stereotype.Component;




 @Component
 public class MyStoreDetailOtherTag
   extends BaseFreeMarkerTag
 {
   private IStoreOrderManager storeOrderManager;
   private IStoreGoodsManager storeGoodsManager;
   private IStoreMemberCommentManager storeMemberCommentManager;
   private IStoreMemberManager storeMemberManager;
   private Integer store_id;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Map result = new HashMap();

     int storeAllOrder = this.storeOrderManager.getStoreOrderNum(64537);
     int orderNotPay = this.storeOrderManager.getStoreOrderNum(0);
     int orderPay = this.storeOrderManager.getStoreOrderNum(1);
     int orderNotShip = this.storeOrderManager.getStoreOrderNum(2);
     int orderNotRog = this.storeOrderManager.getStoreOrderNum(5);
     int orderComplete = this.storeOrderManager.getStoreOrderNum(7);


     int notMarket = this.storeGoodsManager.getStoreGoodsNum(0);

     StoreMember member = this.storeMemberManager.getStoreMember();
     int notReply = this.storeMemberCommentManager.getCommentCount(Integer.valueOf(2), member.getStore_id()).intValue();

     result.put("storeAllOrder", Integer.valueOf(storeAllOrder));
     result.put("orderNotPay", Integer.valueOf(orderNotPay));
     result.put("orderPay", Integer.valueOf(orderPay));
     result.put("orderNotShip", Integer.valueOf(orderNotShip));
     result.put("orderNotRog", Integer.valueOf(orderNotRog));
     result.put("orderComplete", Integer.valueOf(orderComplete));

     result.put("notMarket", Integer.valueOf(notMarket));
     result.put("notReply", Integer.valueOf(notReply));
     return result;
   }

   public Integer getStore_id() { return this.store_id; }

   public void setStore_id(Integer store_id) {
     this.store_id = store_id;
   }

   public IStoreMemberCommentManager getStoreMemberCommentManager() { return this.storeMemberCommentManager; }


   public void setStoreMemberCommentManager(IStoreMemberCommentManager storeMemberCommentManager)
   {
     this.storeMemberCommentManager = storeMemberCommentManager;
   }

   public IStoreOrderManager getStoreOrderManager() {
     return this.storeOrderManager;
   }

   public void setStoreOrderManager(IStoreOrderManager storeOrderManager) { this.storeOrderManager = storeOrderManager; }

   public IStoreGoodsManager getStoreGoodsManager() {
     return this.storeGoodsManager;
   }

   public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager) { this.storeGoodsManager = storeGoodsManager; }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) { this.storeMemberManager = storeMemberManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\MyStoreDetailOtherTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */