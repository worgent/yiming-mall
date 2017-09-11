 package com.enation.app.shop.core.service.impl;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.base.core.service.auth.IPermissionManager;
 import com.enation.app.base.core.service.auth.impl.PermissionConfig;
 import com.enation.app.shop.core.model.Delivery;
 import com.enation.app.shop.core.model.DeliveryItem;
 import com.enation.app.shop.core.model.DepotUser;
 import com.enation.app.shop.core.model.PaymentDetail;
 import com.enation.app.shop.core.model.PaymentLog;
 import com.enation.app.shop.core.model.PaymentLogType;
 import com.enation.app.shop.core.model.RefundLog;
 import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
 import com.enation.app.shop.core.service.IOrderReportManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.DoubleMapper;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.database.impl.IRowMapperColumnFilter;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.List;
 import java.util.Map;








 public class OrderReportManager
   extends BaseSupport
   implements IOrderReportManager
 {
   private IAdminUserManager adminUserManager;
   private OrderPluginBundle orderPluginBundle;
   private IPermissionManager permissionManager;

   public Delivery getDelivery(Integer deliveryId)
   {
     String sql = "select l.*, m.uname as member_name, o.sn from " + getTableName("delivery") + " l left join " + getTableName("member") + " m on m.member_id = l.member_id left join " + getTableName("order") + " o on o.order_id = l.order_id where l.delivery_id = ?";
     Delivery delivery = (Delivery)this.daoSupport.queryForObject(sql, Delivery.class, new Object[] { deliveryId });
     return delivery;
   }

   public List<Delivery> getDeliveryList(int orderId) {
     String sql = "select * from " + getTableName("delivery") + " where order_id=" + orderId;
     return this.daoSupport.queryForList(sql, Delivery.class, new Object[0]);
   }

   public PaymentLog getPayment(Integer paymentId)
   {
     String sql = "select l.*, m.uname as member_name, o.sn from " + getTableName("payment_logs") + " l left join " + getTableName("member") + " m on m.member_id = l.member_id left join " + getTableName("order") + " o on o.order_id = l.order_id where l.payment_id = ?";
     PaymentLog paymentLog = (PaymentLog)this.daoSupport.queryForObject(sql, PaymentLog.class, new Object[] { paymentId });
     return paymentLog;
   }

   public RefundLog getRefund(Integer refundid) {
     String sql = "select * from refund_logs where refund_id=?";
     RefundLog refundLog = (RefundLog)this.baseDaoSupport.queryForObject(sql, RefundLog.class, new Object[] { refundid });
     return refundLog;
   }

   public List<DeliveryItem> listDeliveryItem(Integer deliveryId) { String sql = "select * from delivery_item where delivery_id = ?";
     return this.baseDaoSupport.queryForList(sql, DeliveryItem.class, new Object[] { deliveryId });
   }




   public Page listPayment(Map map, int pageNo, int pageSize, String order)
   {
     String sql = createTempSql(map);
     return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
   }

   private String createTempSql(Map map)
   {
     Integer stype = (Integer)map.get("stype");
     String keyword = (String)map.get("keyword");
     String start_time = (String)map.get("start_time");
     String end_time = (String)map.get("end_time");
     Integer status = (Integer)map.get("status");
     String sn = (String)map.get("sn");
     Integer paystatus = (Integer)map.get("paystatus");
     Integer payment_id = (Integer)map.get("payment_id");

     String sql = "select * from payment_logs where payment_id>0 and type=" + PaymentLogType.receivable.getValue();

     if ((stype != null) && (keyword != null) &&
       (stype.intValue() == 0)) {
       sql = sql + " and sn like '%" + keyword + "%'";
       sql = sql + " or ship_name like '%" + keyword + "%'";
     }


     if ((sn != null) && (!StringUtil.isEmpty(sn))) {
       sql = sql + " and order_sn like '%" + sn + "%'";
     }

     if (paystatus != null) {
       sql = sql + " and status=" + paystatus;
     }

     if (payment_id != null) {
       sql = sql + "and order_id in(select order_id from es_order where payment_id=" + payment_id + ")";
     }

     if ((start_time != null) && (!StringUtil.isEmpty(start_time))) {
       long stime = DateUtil.getDateline(start_time + " 00:00:00");
       sql = sql + " and create_time>" + stime;
     }
     if ((end_time != null) && (!StringUtil.isEmpty(end_time))) {
       long etime = DateUtil.getDateline(end_time + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
       sql = sql + " and create_time<" + etime * 1000L;
     }

     sql = sql + " order by payment_id desc";

     return sql;
   }



   public List<PaymentLog> listPayLogs(Integer orderId)
   {
     return this.baseDaoSupport.queryForList("select * from payment_logs where order_id = ? ", PaymentLog.class, new Object[] { orderId });
   }





   public Page listRefund(int pageNo, int pageSize, String order)
   {
     order = order == null ? "l.payment_id desc" : order;
     String sql = "select * from refund_logs ";
     return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, RefundLog.class, new Object[0]);
   }





   public List<RefundLog> listRefundLogs(Integer order_id)
   {
     return this.baseDaoSupport.queryForList("select * from refund_logs where order_id = ? ", RefundLog.class, new Object[] { order_id });
   }

   private Page listDelivery(int pageNo, int pageSize, String order, int type)
   {
     order = order == null ? "delivery_id desc" : order;
     String sql = "select l.*, m.uname as member_name, o.sn from " + getTableName("delivery") + " l left join " + getTableName("member") + " m on m.member_id = l.member_id left join " + getTableName("order") + " o on o.order_id = l.order_id where l.type = " + type;
     sql = sql + " order by " + order;

     return this.daoSupport.queryForPage(sql, pageNo, pageSize, Delivery.class, new Object[0]);
   }

   public Page listReturned(int pageNo, int pageSize, String order)
   {
     String sql = "select * from sellback_list order by id desc ";
     Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
     return webpage;
   }

   public Page listShipping(int pageNo, int pageSize, String order)
   {
     return listDelivery(pageNo, pageSize, order, 1);
   }

   public List listDelivery(Integer orderId, Integer type)
   {
     return this.baseDaoSupport.queryForList("select * from delivery where order_id = ? and type = ?", new Object[] { orderId, type });
   }





   public Page listAllocation(int pageNo, int pageSize)
   {
     DepotUser depotUser = (DepotUser)this.adminUserManager.getCurrentUser();
     int depotid = depotUser.getDepotid().intValue();
     String sql = "select o.sn,o.status,a.*,i.name as name,i.cat_id,i.addon  from  " + getTableName("allocation_item") + " a left join " + getTableName("order_items") + " i on a.itemid= i.item_id left join  " + getTableName("order") + " o on a.orderid= o.order_id where a.depotid=?  order by o.create_time desc";



     IRowMapperColumnFilter columnFilter = new IRowMapperColumnFilter()
     {
       public void filter(Map colValues, ResultSet rs) throws SQLException
       {
         int catid = rs.getInt("cat_id");
         OrderReportManager.this.orderPluginBundle.filterAlloItem(catid, colValues, rs);

       }


     };
     return this.daoSupport.queryForPage(sql, pageNo, pageSize, columnFilter, new Object[] { Integer.valueOf(depotid) });
   }





   public Page listAllocation(int depotid, int status, int pageNo, int pageSize)
   {
     String sql = "select o.sn,o.status,a.*,i.name as name,i.cat_id,i.addon  from  " + getTableName("allocation_item") + " a left join " + getTableName("order_items") + " i on a.itemid= i.item_id left join  " + getTableName("order") + " o on a.orderid= o.order_id  where  a.iscmpl=? ";


     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
     if (!isSuperAdmin) {
       DepotUser depotUser = (DepotUser)this.adminUserManager.getCurrentUser();
       depotid = depotUser.getDepotid().intValue();
       sql = sql + " and a.depotid=" + depotid;
     }
     sql = sql + " order by o.create_time desc";


     IRowMapperColumnFilter columnFilter = new IRowMapperColumnFilter()
     {
       public void filter(Map colValues, ResultSet rs) throws SQLException
       {
         int catid = rs.getInt("cat_id");
         OrderReportManager.this.orderPluginBundle.filterAlloItem(catid, colValues, rs);

       }


     };
     return this.daoSupport.queryForPage(sql, pageNo, pageSize, columnFilter, new Object[] { Integer.valueOf(status) });
   }

   public double getPayMoney(Integer paymentId)
   {
     return ((Double)this.baseDaoSupport.queryForObject("select sum(pay_money) from payment_detail where payment_id=?", new DoubleMapper(), new Object[] { paymentId })).doubleValue();
   }




   public void addPayMentDetail(PaymentDetail paymentdetail)
   {
     this.baseDaoSupport.insert("payment_detail", paymentdetail);
   }




   public Integer getPaymentLogId(Integer orderId)
   {
     return Integer.valueOf(this.baseDaoSupport.queryForInt("select payment_id from payment_logs where order_id=? and type=" + PaymentLogType.receivable.getValue(), new Object[] { orderId }));
   }





   public List<PaymentDetail> listPayMentDetail(Integer paymentId)
   {
     return this.baseDaoSupport.queryForList("select * from payment_detail where payment_id=?", PaymentDetail.class, new Object[] { paymentId });
   }

   public IAdminUserManager getAdminUserManager()
   {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager)
   {
     this.adminUserManager = adminUserManager;
   }

   public OrderPluginBundle getOrderPluginBundle()
   {
     return this.orderPluginBundle;
   }

   public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle)
   {
     this.orderPluginBundle = orderPluginBundle;
   }

   public IPermissionManager getPermissionManager() {
     return this.permissionManager;
   }

   public void setPermissionManager(IPermissionManager permissionManager) {
     this.permissionManager = permissionManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\OrderReportManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */