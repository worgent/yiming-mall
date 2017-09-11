 package com.enation.app.b2b2c.core.service.order.impl;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.model.order.StoreOrder;
 import com.enation.app.b2b2c.core.service.cart.IStoreCartManager;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.b2b2c.core.service.order.IStoreOrderManager;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.app.shop.core.service.IPromotionManager;
 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;



 @Component
 public class StoreOrderManager
   extends BaseSupport
   implements IStoreOrderManager
 {
   private IStoreCartManager storeCartManager;
   private IDlyTypeManager dlyTypeManager;
   private IPaymentManager paymentManager;
   private OrderPluginBundle orderPluginBundle;
   private IPromotionManager promotionManager;
   private IStoreGoodsManager storeGoodsManager;
   private IStoreMemberManager storeMemberManager;

   public Page storeOrderList(Integer pageNo, Integer pageSize, Integer storeid, Map map)
   {
     String order_state = String.valueOf(map.get("order_state"));
     String keyword = String.valueOf(map.get("keyword"));
     String buyerName = String.valueOf(map.get("buyerName"));
     String startTime = String.valueOf(map.get("startTime"));
     String endTime = String.valueOf(map.get("endTime"));

     StringBuffer sql = new StringBuffer("select * from es_order o where store_id =" + storeid + " and disabled=0");
     if ((!StringUtil.isEmpty(order_state)) && (!order_state.equals("null"))) {
       if (order_state.equals("wait_ship")) {
         sql.append(" and ( ( payment_type!='cod' and payment_id!=8  and  status=2) ");
         sql.append(" or ( payment_type='cod' and  status=0)) ");
       } else if (order_state.equals("wait_pay")) {
         sql.append(" and ( ( payment_type!='cod' and  status=0) ");
         sql.append(" or ( payment_id=8 and status!=0  and  pay_status!=2)");
         sql.append(" or ( payment_type='cod' and  (status=5 or status=6 )  ) )");
       }
       else if (order_state.equals("wait_rog")) {
         sql.append(" and status=5");
       } else {
         sql.append(" and status=" + order_state);
       }
     }
     if ((!StringUtil.isEmpty(keyword)) && (!keyword.equals("null"))) {
       sql.append(" AND o.sn like '%" + keyword + "%'");
     }
     if ((!StringUtil.isEmpty(buyerName)) && (!buyerName.equals("null"))) {
       sql.append(" AND o.ship_name like '%" + buyerName + "%'");
     }
     if ((!StringUtil.isEmpty(startTime)) && (!startTime.equals("null"))) {
       sql.append(" AND o.create_time >" + DateUtil.getDateline(startTime));
     }
     if ((!StringUtil.isEmpty(endTime)) && (!endTime.equals("null"))) {
       sql.append(" AND o.create_time <" + DateUtil.getDateline(endTime));
     }
     sql.append(" order by o.create_time desc");
     System.out.println(sql.toString());
     Page rpage = this.daoSupport.queryForPage(sql.toString(), pageNo.intValue(), pageSize.intValue(), Order.class, new Object[0]);

     return rpage;
   }




   public List storeOrderList(Integer parent_id)
   {
     StringBuffer sql = new StringBuffer("SELECT * from es_order WHERE  disabled=0 AND parent_id=" + parent_id);
     return this.daoSupport.queryForList(sql.toString(), StoreOrder.class, new Object[0]);
   }




   public StoreOrder get(Integer orderId)
   {
     String sql = "select * from order where order_id=?";
     StoreOrder order = (StoreOrder)this.baseDaoSupport.queryForObject(sql, StoreOrder.class, new Object[] { orderId });

     return order;
   }






   @Transactional(propagation=Propagation.REQUIRED)
   public boolean saveShipInfo(String remark, String ship_day, String ship_name, String ship_tel, String ship_mobile, String ship_zip, int orderid)
   {
     Order order = get(Integer.valueOf(orderid));
     try {
       if ((ship_day != null) && (!StringUtil.isEmpty(ship_day)) && (!ship_day.equals(order.getShip_day()))) {
         this.baseDaoSupport.execute("update order set ship_day=?  where order_id=?", new Object[] { ship_day, Integer.valueOf(orderid) });
       }
       if ((remark != null) && (!StringUtil.isEmpty(remark)) && (!remark.equals("undefined")) && (!remark.equals(order.getRemark()))) {
         this.baseDaoSupport.execute("update order set remark= ?  where order_id=?", new Object[] { remark, Integer.valueOf(orderid) });
       }
       if ((ship_name != null) && (!StringUtil.isEmpty(ship_name)) && (!ship_name.equals(order.getShip_name()))) {
         this.baseDaoSupport.execute("update order set ship_name=?  where order_id=?", new Object[] { ship_name, Integer.valueOf(orderid) });
       }
       if ((ship_tel != null) && (!StringUtil.isEmpty(ship_tel)) && (!ship_tel.equals(order.getShip_tel()))) {
         this.baseDaoSupport.execute("update order set ship_tel=?  where order_id=?", new Object[] { ship_tel, Integer.valueOf(orderid) });
       }
       if ((ship_mobile != null) && (!StringUtil.isEmpty(ship_mobile)) && (!ship_mobile.equals(order.getShip_mobile()))) {
         this.baseDaoSupport.execute("update order set ship_mobile=?  where order_id=?", new Object[] { ship_mobile, Integer.valueOf(orderid) });
       }
       if ((ship_zip != null) && (!StringUtil.isEmpty(ship_zip)) && (!ship_zip.equals(order.getShip_zip()))) {
         this.baseDaoSupport.execute("update order set ship_zip=?  where order_id=?", new Object[] { ship_zip, Integer.valueOf(orderid) });
       }
       return true;
     } catch (Exception e) {
       e.printStackTrace(); }
     return false;
   }






   public Page pageOrders(int pageNo, int pageSize, String status, String keyword)
   {
     StoreMember member = this.storeMemberManager.getStoreMember();

     StringBuffer sql = new StringBuffer("select * from " + getTableName("order") + " where member_id = '" + member.getMember_id() + "' and disabled=0");
     if (!StringUtil.isEmpty(status)) {
       int statusNumber = 64537;
       statusNumber = StringUtil.toInt(status);

       if (statusNumber == 0) {
         sql.append(" AND status!=8 AND pay_status=0");
       } else {
         sql.append(" AND status='" + statusNumber + "'");
       }
     }
     if (!StringUtil.isEmpty(keyword)) {
       sql.append(" AND order_id in (SELECT i.order_id FROM " + getTableName("order_items") + " i LEFT JOIN " + getTableName("order") + " o ON i.order_id=o.order_id WHERE o.member_id='" + member.getMember_id() + "' AND i.name like '%" + keyword + "%')");
     }
     sql.append(" AND NOT ISNULL(parent_id) order by create_time desc");
     Page rpage = this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, Order.class, new Object[0]);
     return rpage;
   }





   public int getStoreOrderNum(int struts)
   {
     StoreMember member = this.storeMemberManager.getStoreMember();
     String sql = "select count(order_id) from es_order o where o.store_id =" + member.getStore_id() + " and o.disabled=0";
     if (struts != 64537) {
       sql = sql + " AND o.status=" + struts;
     }
     return this.daoSupport.queryForInt(sql, new Object[0]);
   }




   public StoreOrder get(String ordersn)
   {
     String sql = "select * from es_order where sn='" + ordersn + "'";
     StoreOrder order = (StoreOrder)this.baseDaoSupport.queryForObject(sql, StoreOrder.class, new Object[0]);
     return order;
   }





   public Page listOrder(Map map, int page, int pageSize, String other, String order)
   {
     String sql = createTempSql(map, other, order);
     Page webPage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
     return webPage;
   }

   private String createTempSql(Map map, String other, String order) {
     Integer stype = (Integer)map.get("stype");
     String keyword = (String)map.get("keyword");
     String orderstate = (String)map.get("order_state");
     String start_time = (String)map.get("start_time");
     String end_time = (String)map.get("end_time");
     Integer status = (Integer)map.get("status");
     String sn = (String)map.get("sn");
     String ship_name = (String)map.get("ship_name");
     Integer paystatus = (Integer)map.get("paystatus");
     Integer shipstatus = (Integer)map.get("shipstatus");
     Integer shipping_type = (Integer)map.get("shipping_type");
     Integer payment_id = (Integer)map.get("payment_id");
     Integer depotid = (Integer)map.get("depotid");
     String complete = (String)map.get("complete");
     String store_name = (String)map.get("store_name");
     StringBuffer sql = new StringBuffer();
     sql.append("select o.*,s.store_name as store_name from order o inner join store s on o.store_id=s.store_id  where o.disabled=0 and NOT ISNULL(parent_id)");

     if ((stype != null) && (keyword != null) &&
       (stype.intValue() == 0)) {
       sql.append(" and (sn like '%" + keyword + "%'");
       sql.append(" or ship_name like '%" + keyword + "%')");
     }


     if (status != null) {
       sql.append("and status=" + status);
     }

     if ((sn != null) && (!StringUtil.isEmpty(sn))) {
       sql.append(" and sn like '%" + sn + "%'");
     }

     if ((ship_name != null) && (!StringUtil.isEmpty(ship_name))) {
       sql.append(" and ship_name like '" + ship_name + "'");
     }

     if (paystatus != null) {
       sql.append(" and pay_status=" + paystatus);
     }

     if (shipstatus != null) {
       sql.append(" and ship_status=" + shipstatus);
     }

     if (shipping_type != null) {
       sql.append(" and shipping_id=" + shipping_type);
     }

     if (payment_id != null) {
       sql.append(" and payment_id=" + payment_id);
     }

     if ((depotid != null) && (depotid.intValue() > 0)) {
       sql.append(" and depotid=" + depotid);
     }

     if ((start_time != null) && (!StringUtil.isEmpty(start_time))) {
       long stime = DateUtil.getDateline(start_time + " 00:00:00");
       sql.append(" and create_time>" + stime);
     }
     if ((end_time != null) && (!StringUtil.isEmpty(end_time))) {
       long etime = DateUtil.getDateline(end_time + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
       sql.append(" and create_time<" + etime * 1000L);
     }
     if (!StringUtil.isEmpty(orderstate)) {
       if (orderstate.equals("wait_ship")) {
         sql.append(" and ( ( payment_type!='cod' and payment_id!=8  and  status=2) ");
         sql.append(" or ( payment_type='cod' and  status=0)) ");
       } else if (orderstate.equals("wait_pay")) {
         sql.append(" and ( ( payment_type!='cod' and  status=0) ");
         sql.append(" or ( payment_id=8 and status!=0  and  pay_status!=2)");
         sql.append(" or ( payment_type='cod' and  (status=5 or status=6 )  ) )");
       }
       else if (orderstate.equals("wait_rog")) {
         sql.append(" and status=5");
       } else {
         sql.append(" and status=" + orderstate);
       }
     }


     if (!StringUtil.isEmpty(complete)) {
       sql.append(" and status=7");
     }
     if (!StringUtil.isEmpty(store_name)) {
       sql.append(" and store_id in(select store_id from es_store where store_name like %'" + store_name + "'%)");
     }
     sql.append(" ORDER BY " + other + " " + order);


     return sql.toString();
   }

   public Map getStatusJson() {
     Map orderStatus = new HashMap();

     orderStatus.put("0", OrderStatus.getOrderStatusText(0));

     orderStatus.put("9", OrderStatus.getOrderStatusText(9));
     orderStatus.put("2", OrderStatus.getOrderStatusText(2));
     orderStatus.put("4", OrderStatus.getOrderStatusText(4));
     orderStatus.put("5", OrderStatus.getOrderStatusText(5));
     orderStatus.put("6", OrderStatus.getOrderStatusText(6));
     orderStatus.put("-2", OrderStatus.getOrderStatusText(-2));
     orderStatus.put("7", OrderStatus.getOrderStatusText(7));
     orderStatus.put("-1", OrderStatus.getOrderStatusText(-1));
     orderStatus.put("8", OrderStatus.getOrderStatusText(8));

     return orderStatus;
   }

   public Map getpPayStatusJson() {
     Map pmap = new HashMap();
     pmap.put("0", OrderStatus.getPayStatusText(0));

     pmap.put("2", OrderStatus.getPayStatusText(2));
     pmap.put("3", OrderStatus.getPayStatusText(3));
     pmap.put("5", OrderStatus.getPayStatusText(5));

     return pmap;
   }

   public Map getShipJson() {
     Map map = new HashMap();
     map.put("0", OrderStatus.getShipStatusText(0));
     map.put("1", OrderStatus.getShipStatusText(1));
     map.put("2", OrderStatus.getShipStatusText(2));
     map.put("3", OrderStatus.getShipStatusText(3));
     map.put("4", OrderStatus.getShipStatusText(4));
     map.put("5", OrderStatus.getShipStatusText(5));
     map.put("3", OrderStatus.getShipStatusText(3));
     map.put("4", OrderStatus.getShipStatusText(4));
     map.put("8", OrderStatus.getShipStatusText(8));
     map.put("9", OrderStatus.getShipStatusText(9));
     return map;
   }


   public IDlyTypeManager getDlyTypeManager()
   {
     return this.dlyTypeManager;
   }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
     this.dlyTypeManager = dlyTypeManager;
   }

   public IPaymentManager getPaymentManager() {
     return this.paymentManager;
   }

   public void setPaymentManager(IPaymentManager paymentManager) {
     this.paymentManager = paymentManager;
   }

   public OrderPluginBundle getOrderPluginBundle() {
     return this.orderPluginBundle;
   }

   public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle) {
     this.orderPluginBundle = orderPluginBundle;
   }

   public IPromotionManager getPromotionManager() {
     return this.promotionManager;
   }

   public void setPromotionManager(IPromotionManager promotionManager) {
     this.promotionManager = promotionManager;
   }

   public IStoreGoodsManager getStoreGoodsManager() {
     return this.storeGoodsManager;
   }

   public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager) {
     this.storeGoodsManager = storeGoodsManager;
   }

   public IStoreCartManager getStoreCartManager() {
     return this.storeCartManager;
   }

   public void setStoreCartManager(IStoreCartManager storeCartManager) {
     this.storeCartManager = storeCartManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\order\impl\StoreOrderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */