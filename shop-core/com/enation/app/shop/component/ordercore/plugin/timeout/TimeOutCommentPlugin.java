 package com.enation.app.shop.component.ordercore.plugin.timeout;

 import com.enation.app.base.core.plugin.job.IEveryDayExecuteEvent;
 import com.enation.app.shop.core.model.MemberComment;
 import com.enation.app.shop.core.model.MemberOrderItem;
 import com.enation.app.shop.core.service.IMemberCommentManager;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IMemberOrderItemManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.DateUtil;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;




 @Component
 public class TimeOutCommentPlugin
   extends AutoRegisterPlugin
   implements IEveryDayExecuteEvent
 {
   private IDaoSupport daoSupport;
   private IOrderManager orderManager;
   private IMemberCommentManager memberCommentManager;
   private IMemberOrderItemManager memberOrderItemManager;
   private IMemberManager memberManager;

   public void everyDay()
   {
     String sql = "SELECT tr.* from es_member_order_item mo INNER JOIN es_transaction_record tr ON mo.order_id=tr.order_id WHERE mo.commented=0  and tr.rog_time+?<? GROUP BY tr.record_id ";
     List<Map> list = this.daoSupport.queryForList(sql, new Object[] { Integer.valueOf(604800), Long.valueOf(DateUtil.getDatelineLong()) });
     commentOrder(list);
   }




   private void commentOrder(List<Map> list)
   {
     MemberComment memberComment = new MemberComment();

     for (Map map : list) {
       Integer goods_id = Integer.valueOf(Integer.parseInt(map.get("goods_id").toString()));
       Integer member_id = Integer.valueOf(Integer.parseInt(map.get("member_id").toString()));
       memberComment.setGoods_id(goods_id.intValue());
       memberComment.setGrade(5);
       memberComment.setImg(null);
       memberComment.setMember_id(member_id);
       memberComment.setDateline(DateUtil.getDatelineLong());
       memberComment.setType(1);
       memberComment.setContent("真好吃");
       memberComment.setStatus(1);
       this.memberCommentManager.add(memberComment);

       MemberOrderItem memberOrderItem = this.memberOrderItemManager.get(member_id.intValue(), goods_id.intValue(), 1);
       if (memberOrderItem != null) {
         memberOrderItem.setCommented(Integer.valueOf(1));
         memberOrderItem.setComment_time(Long.valueOf(DateUtil.getDatelineLong()));
         this.memberOrderItemManager.update(memberOrderItem);
       }
     }
   }

   public IDaoSupport getDaoSupport() { return this.daoSupport; }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }

   public IOrderManager getOrderManager() { return this.orderManager; }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IMemberCommentManager getMemberCommentManager() { return this.memberCommentManager; }

   public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
     this.memberCommentManager = memberCommentManager;
   }

   public IMemberOrderItemManager getMemberOrderItemManager() { return this.memberOrderItemManager; }

   public void setMemberOrderItemManager(IMemberOrderItemManager memberOrderItemManager)
   {
     this.memberOrderItemManager = memberOrderItemManager;
   }

   public IMemberManager getMemberManager() { return this.memberManager; }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\ordercore\plugin\timeout\TimeOutCommentPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */