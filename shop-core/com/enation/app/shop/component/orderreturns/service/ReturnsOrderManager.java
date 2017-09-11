 package com.enation.app.shop.component.orderreturns.service;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.OrderLog;
 import com.enation.app.shop.core.model.ReturnsOrder;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.database.DoubleMapper;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.database.StringMapper;
 import com.enation.framework.util.DateUtil;
 import java.util.List;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;



 @Component
 public class ReturnsOrderManager
   extends BaseSupport
   implements IReturnsOrderManager
 {
   private IOrderFlowManager orderFlowManager;
   private IOrderManager orderManager;
   private IReturnsOrderManager returnsOrderManager;

   public IReturnsOrderManager getReturnsOrderManager()
   {
     return this.returnsOrderManager;
   }

   public void setReturnsOrderManager(IReturnsOrderManager returnsOrderManager) {
     this.returnsOrderManager = returnsOrderManager;
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void add(ReturnsOrder returnsOrder, int orderid, int state, int[] goodids) {
     returnsOrder.setState(Integer.valueOf(0));
     returnsOrder.setAdd_time(Integer.valueOf(DateUtil.getDateline()));
     for (int i = 0; i < goodids.length; i++) {
       this.baseDaoSupport.execute("update order_items set state = ? where order_id = ? and goods_id= ?", new Object[] { Integer.valueOf(state), Integer.valueOf(orderid), Integer.valueOf(goodids[i]) });
     }
     this.baseDaoSupport.insert("returns_order", returnsOrder);
   }

   public ReturnsOrder get(Integer id)
   {
     ReturnsOrder order = (ReturnsOrder)this.baseDaoSupport.queryForObject("select * from returns_order where id=?", ReturnsOrder.class, new Object[] { id });



     return order;
   }

   public ReturnsOrder getByOrderSn(String ordersn) {
     ReturnsOrder order = (ReturnsOrder)this.baseDaoSupport.queryForObject("select * from returns_order where ordersn=?", ReturnsOrder.class, new Object[] { ordersn });
     return order;
   }

   public Page listAll(int pageNo, int pageSize)
   {
     String sql = "select r.*,m.uname as membername from " + getTableName("returns_order") + " r," + getTableName("member") + " m where r.memberid=m.member_id   order by r.add_time desc";

     return this.daoSupport.queryForPage(sql, pageNo, pageSize, ReturnsOrder.class, new Object[0]);
   }

   public List<ReturnsOrder> listMemberOrder()
   {
     Member member = UserServiceFactory.getUserService().getCurrentMember();
     return this.baseDaoSupport.queryForList("select * from returns_order where memberid =? ", ReturnsOrder.class, new Object[] { member.getMember_id() });
   }

   public void updateState(Integer returnOrderId, int state) {
     this.baseDaoSupport.execute("update returns_order set state=? where id=?", new Object[] { Integer.valueOf(state), returnOrderId });
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void refuse(Integer return_id, String refuse_reason, int return_state) { this.baseDaoSupport.execute("update returns_order set state=?,refuse_reason=? where id=?", new Object[] { Integer.valueOf(return_state), refuse_reason, return_id }); }

   public IOrderFlowManager getOrderFlowManager()
   {
     return this.orderFlowManager;
   }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
     this.orderFlowManager = orderFlowManager;
   }

   public IOrderManager getOrderManager()
   {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager)
   {
     this.orderManager = orderManager;
   }




   public Page pageReturnOrder(int pageNo, int pageSize)
   {
     Member member = UserServiceFactory.getUserService().getCurrentMember();
     String sql = "select * from returns_order where memberid = ? order by add_time desc";
     Page rpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id() });
     return rpage;
   }




   public String getSnByOrderSn(String orderSn)
   {
     return (String)this.baseDaoSupport.queryForObject("select goodsns from returns_order where ordersn = ?", new StringMapper(), new Object[] { orderSn });
   }




   public void updateOrderItemsState(Integer itemsId, int state)
   {
     this.baseDaoSupport.execute("update order_items set state = ? where item_id = ?", new Object[] { Integer.valueOf(state), itemsId });
   }




   public Double queryItemPrice(Integer orderid, Integer state)
   {
     Double temp = (Double)this.baseDaoSupport.queryForObject("SELECT sum(price) as price FROM order_items  where order_id = ? and state= ?", new DoubleMapper(), new Object[] { orderid, state });
     return temp;
   }

   public void updateItemChange(String change_goods_name, int change_goods_id, int itemId)
   {
     this.baseDaoSupport.execute("update order_items set change_goods_name =?,change_goods_id=? where item_id = ?", new Object[] { change_goods_name, Integer.valueOf(change_goods_id), Integer.valueOf(itemId) });
   }


   public void updateItemStatusByOrderidAndStatus(int newStatus, int prevStatus, int orderid)
   {
     this.baseDaoSupport.execute("update order_items set state = ? where order_id = ? and state=?", new Object[] { Integer.valueOf(newStatus), Integer.valueOf(orderid), Integer.valueOf(prevStatus) });
   }

   public int queryOrderidByReturnorderid(int returnorderid)
   {
     return this.orderManager.get(this.returnsOrderManager.get(Integer.valueOf(returnorderid)).getOrdersn()).getOrder_id().intValue();
   }

   public void updatePriceByItemid(int itemid, double price)
   {
     this.baseDaoSupport.execute("update order_items set price=? where item_id=?", new Object[] { Double.valueOf(price), Integer.valueOf(itemid) });
   }

   public Page listAll(int pageNo, int pageSize, int state)
   {
     return this.daoSupport.queryForPage("select r.*,m.uname as membername from " + getTableName("returns_order") + " r," + getTableName("member") + " m where r.memberid=m.member_id and r.state = ?   order by r.add_time desc", pageNo, pageSize, ReturnsOrder.class, new Object[] { Integer.valueOf(state) });
   }





   public void addLog(OrderLog log)
   {
     log.setOp_time(Long.valueOf(System.currentTimeMillis()));
     this.baseDaoSupport.insert("order_log", log);
   }


   public Integer getOrderidByReturnid(Integer returnorderid)
   {
     Integer orderid = this.orderManager.get(this.returnsOrderManager.get(returnorderid).getOrdersn()).getOrder_id();
     return orderid;
   }

   public void updateItemsState(Integer orderid, int nowstate, int prestate)
   {
     this.baseDaoSupport.execute("update order_items set state = ?  where order_id =? and state = ? ", new Object[] { Integer.valueOf(nowstate), orderid, Integer.valueOf(prestate) });
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\orderreturns\service\ReturnsOrderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */