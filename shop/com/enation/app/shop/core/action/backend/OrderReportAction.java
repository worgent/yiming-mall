 package com.enation.app.shop.core.action.backend;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.shop.core.model.Delivery;
 import com.enation.app.shop.core.model.DeliveryItem;
 import com.enation.app.shop.core.model.DlyType;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.model.PaymentLog;
 import com.enation.app.shop.core.model.RefundLog;
 import com.enation.app.shop.core.model.SellBackList;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.app.shop.core.service.ILogiManager;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IOrderMetaManager;
 import com.enation.app.shop.core.service.IOrderReportManager;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.app.shop.core.service.ISellBackManager;
 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import net.sf.json.JSONArray;

















 public class OrderReportAction
   extends WWAction
 {
   private IOrderReportManager orderReportManager;
   private IOrderMetaManager orderMetaManager;
   private IMemberManager memberManager;
   private IAdminUserManager adminUserManager;
   private ISellBackManager sellBackManager;
   private IOrderManager orderManager;
   private ILogiManager logiManager;
   private String order;
   private Order ret_order;
   private int id;
   private PaymentLog payment;
   private RefundLog refund;
   private Delivery delivery;
   private List<DeliveryItem> listDeliveryItem;
   private IPaymentManager paymentManager;
   private List paymentList;
   private IOrderFlowManager orderFlowManager;
   private int orderId;
   private List paymentLogsList;
   private List refundLogsList;
   private SellBackList sellBackList;
   private List goodsList;
   private List metaList;
   private List logList;
   private Integer is_all;
   private Integer sid;
   private Map statusMap;
   private Map payStatusMap;
   private Map shipMap;
   private String status_Json;
   private String payStatus_Json;
   private String ship_Json;
   private IDlyTypeManager dlyTypeManager;
   private List<DlyType> shipTypeList;
   private List<PayCfg> payTypeList;
   private Map orderMap;
   private Integer stype;
   private String keyword;
   private String start_time;
   private String end_time;
   private String sn;
   private Integer paystatus = null;
   private Integer payment_id = null;

   public String paymentList() { if (this.statusMap == null) {
       this.statusMap = new HashMap();
       this.statusMap = getStatusJson();
       String p = JSONArray.fromObject(this.statusMap).toString();
       this.status_Json = p.replace("[", "").replace("]", "");
     }

     if (this.payStatusMap == null) {
       this.payStatusMap = new HashMap();
       this.payStatusMap = getpPayStatusJson();
       String p = JSONArray.fromObject(this.payStatusMap).toString();
       this.payStatus_Json = p.replace("[", "").replace("]", "");
     }


     if (this.shipMap == null) {
       this.shipMap = new HashMap();
       this.shipMap = getShipJson();
       String p = JSONArray.fromObject(this.shipMap).toString();
       this.ship_Json = p.replace("[", "").replace("]", "");
     }

     this.shipTypeList = this.dlyTypeManager.list();
     this.payTypeList = this.paymentManager.list();
     return "paymentList";
   }


   public String paymentListJson()
   {
     this.orderMap = new HashMap();
     this.orderMap.put("stype", this.stype);
     this.orderMap.put("keyword", this.keyword);
     this.orderMap.put("start_time", this.start_time);
     this.orderMap.put("end_time", this.end_time);
     this.orderMap.put("sn", this.sn);
     this.orderMap.put("paystatus", this.paystatus);
     this.orderMap.put("payment_id", this.payment_id);


     this.webpage = this.orderReportManager.listPayment(this.orderMap, getPage(), getPageSize(), this.order);

     showGridJson(this.webpage);
     return "json_message";
   }

   public String paymentLogs() {
     this.payment = this.orderReportManager.getPayment(Integer.valueOf(this.id));
     this.paymentLogsList = this.orderReportManager.listPayLogs(Integer.valueOf(this.payment.getOrder_id()));
     return "paymentLogs";
   }

   public String paymentDetail() {
     this.payment = this.orderReportManager.getPayment(Integer.valueOf(this.id));
     this.paymentList = this.paymentManager.list();
     return "paymentDetail";
   }


   public String refundList() { return "refundList"; }

   public String refundListJson() {
     this.webpage = this.orderReportManager.listRefund(getPage(), getPageSize(), this.order);
     showGridJson(this.webpage);
     return "json_message";
   }

   public String refundLogs() {
     this.payment = this.orderReportManager.getPayment(Integer.valueOf(this.id));
     this.refundLogsList = this.orderReportManager.listRefundLogs(Integer.valueOf(this.payment.getOrder_id()));
     return "refundLogs";
   }

   public String refundDetail() {
     this.refund = this.orderReportManager.getRefund(Integer.valueOf(this.id));
     return "refundDetail";
   }





   public String allocationList()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String depotid = request.getParameter("depotid");
     if (depotid == null) {
       depotid = "0";
     }
     this.webpage = this.orderReportManager.listAllocation(Integer.parseInt(depotid), 0, getPage(), getPageSize());
     return "allocationList";
   }



   public String allocationedList()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String depotid = request.getParameter("depotid");
     if (depotid == null) {
       depotid = "0";
     }
     this.webpage = this.orderReportManager.listAllocation(Integer.parseInt(depotid), 1, getPage(), getPageSize());
     return "allocationList";
   }




   public String shippingList() { return "shippingList"; }

   public String shippingListJson() {
     this.webpage = this.orderReportManager.listShipping(getPage(), getPageSize(), this.order);
     showGridJson(this.webpage);
     return "json_message";
   }

   public String shippingDetail() {
     this.delivery = this.orderReportManager.getDelivery(Integer.valueOf(this.id));
     this.listDeliveryItem = this.orderReportManager.listDeliveryItem(Integer.valueOf(this.id));
     return "shippingDetail";
   }



   public String returnedList() { return "returnedList"; }

   public String returnedListJson() {
     this.webpage = this.orderReportManager.listReturned(getPage(), getPageSize(), null);
     showGridJson(this.webpage);
     return "json_message";
   }




   public String returned()
   {
     this.sellBackList = this.sellBackManager.get(this.sid);
     this.ret_order = this.orderManager.get(this.sellBackList.getOrdersn());
     this.goodsList = this.sellBackManager.getGoodsList(this.sid, this.sellBackList.getOrdersn());
     this.logList = this.sellBackManager.sellBackLogList(this.sid);
     this.metaList = this.orderMetaManager.list(this.ret_order.getOrder_id().intValue());
     this.is_all = this.sellBackManager.isAll(this.sid.intValue());
     return "returned";
   }

   public String returnedDetail() {
     this.delivery = this.orderReportManager.getDelivery(Integer.valueOf(this.id));
     this.listDeliveryItem = this.orderReportManager.listDeliveryItem(Integer.valueOf(this.id));
     return "returnedDetail";
   }





   private Map getStatusJson()
   {
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


   private Map getpPayStatusJson()
   {
     Map pmap = new HashMap();
     pmap.put("0", OrderStatus.getPayStatusText(0));

     pmap.put("2", OrderStatus.getPayStatusText(2));
     pmap.put("3", OrderStatus.getPayStatusText(3));
     pmap.put("5", OrderStatus.getPayStatusText(5));

     return pmap;
   }

   private Map getShipJson() {
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

   public IOrderReportManager getOrderReportManager()
   {
     return this.orderReportManager;
   }

   public void setOrderReportManager(IOrderReportManager orderReportManager) {
     this.orderReportManager = orderReportManager;
   }

   public String getOrder() {
     return this.order;
   }

   public void setOrder(String order) {
     this.order = order;
   }

   public int getId() {
     return this.id;
   }

   public void setId(int id) {
     this.id = id;
   }

   public PaymentLog getPayment() {
     return this.payment;
   }

   public void setPayment(PaymentLog payment) {
     this.payment = payment;
   }

   public Delivery getDelivery() {
     return this.delivery;
   }

   public void setDelivery(Delivery delivery) {
     this.delivery = delivery;
   }

   public List<DeliveryItem> getListDeliveryItem() {
     return this.listDeliveryItem;
   }

   public void setListDeliveryItem(List<DeliveryItem> listDeliveryItem) {
     this.listDeliveryItem = listDeliveryItem;
   }

   public IPaymentManager getPaymentManager() {
     return this.paymentManager;
   }

   public void setPaymentManager(IPaymentManager paymentManager) {
     this.paymentManager = paymentManager;
   }

   public List getPaymentList() {
     return this.paymentList;
   }

   public void setPaymentList(List paymentList) {
     this.paymentList = paymentList;
   }

   public IOrderFlowManager getOrderFlowManager() {
     return this.orderFlowManager;
   }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
     this.orderFlowManager = orderFlowManager;
   }

   public int getOrderId() {
     return this.orderId;
   }

   public void setOrderId(int orderId) {
     this.orderId = orderId;
   }

   public List getPaymentLogsList() {
     return this.paymentLogsList;
   }

   public void setPaymentLogsList(List paymentLogsList) {
     this.paymentLogsList = paymentLogsList;
   }

   public List getRefundLogsList() {
     return this.refundLogsList;
   }

   public void setRefundLogsList(List refundLogsList) {
     this.refundLogsList = refundLogsList;
   }

   public RefundLog getRefund() {
     return this.refund;
   }

   public void setRefund(RefundLog refund) {
     this.refund = refund;
   }

   public IOrderMetaManager getOrderMetaManager() {
     return this.orderMetaManager;
   }

   public void setOrderMetaManager(IOrderMetaManager orderMetaManager) { this.orderMetaManager = orderMetaManager; }

   public IMemberManager getMemberManager() {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) { this.memberManager = memberManager; }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) { this.adminUserManager = adminUserManager; }

   public ISellBackManager getSellBackManager() {
     return this.sellBackManager;
   }

   public void setSellBackManager(ISellBackManager sellBackManager) { this.sellBackManager = sellBackManager; }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) { this.orderManager = orderManager; }

   public ILogiManager getLogiManager() {
     return this.logiManager;
   }

   public void setLogiManager(ILogiManager logiManager) { this.logiManager = logiManager; }

   public SellBackList getSellBackList() {
     return this.sellBackList;
   }

   public void setSellBackList(SellBackList sellBackList) { this.sellBackList = sellBackList; }

   public List getGoodsList() {
     return this.goodsList;
   }

   public void setGoodsList(List goodsList) { this.goodsList = goodsList; }

   public List getMetaList() {
     return this.metaList;
   }

   public void setMetaList(List metaList) { this.metaList = metaList; }

   public List getLogList() {
     return this.logList;
   }

   public void setLogList(List logList) { this.logList = logList; }

   public Integer getIs_all() {
     return this.is_all;
   }

   public void setIs_all(Integer is_all) { this.is_all = is_all; }

   public Order getRet_order() {
     return this.ret_order;
   }

   public void setRet_order(Order ret_order) { this.ret_order = ret_order; }

   public Integer getSid() {
     return this.sid;
   }

   public void setSid(Integer sid) { this.sid = sid; }

   public Map getStatusMap() {
     return this.statusMap;
   }

   public void setStatusMap(Map statusMap) { this.statusMap = statusMap; }

   public Map getPayStatusMap() {
     return this.payStatusMap;
   }

   public void setPayStatusMap(Map payStatusMap) { this.payStatusMap = payStatusMap; }

   public Map getShipMap() {
     return this.shipMap;
   }

   public void setShipMap(Map shipMap) { this.shipMap = shipMap; }

   public String getStatus_Json() {
     return this.status_Json;
   }

   public void setStatus_Json(String status_Json) { this.status_Json = status_Json; }

   public String getPayStatus_Json() {
     return this.payStatus_Json;
   }

   public void setPayStatus_Json(String payStatus_Json) { this.payStatus_Json = payStatus_Json; }

   public String getShip_Json() {
     return this.ship_Json;
   }

   public void setShip_Json(String ship_Json) { this.ship_Json = ship_Json; }

   public IDlyTypeManager getDlyTypeManager() {
     return this.dlyTypeManager;
   }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) { this.dlyTypeManager = dlyTypeManager; }

   public List<DlyType> getShipTypeList() {
     return this.shipTypeList;
   }

   public void setShipTypeList(List<DlyType> shipTypeList) { this.shipTypeList = shipTypeList; }

   public List<PayCfg> getPayTypeList() {
     return this.payTypeList;
   }

   public void setPayTypeList(List<PayCfg> payTypeList) { this.payTypeList = payTypeList; }

   public Map getOrderMap() {
     return this.orderMap;
   }

   public void setOrderMap(Map orderMap) { this.orderMap = orderMap; }

   public Integer getStype() {
     return this.stype;
   }

   public void setStype(Integer stype) { this.stype = stype; }

   public String getKeyword() {
     return this.keyword;
   }

   public void setKeyword(String keyword) { this.keyword = keyword; }

   public String getStart_time() {
     return this.start_time;
   }

   public void setStart_time(String start_time) { this.start_time = start_time; }

   public String getEnd_time() {
     return this.end_time;
   }

   public void setEnd_time(String end_time) { this.end_time = end_time; }

   public String getSn() {
     return this.sn;
   }

   public void setSn(String sn) { this.sn = sn; }

   public Integer getPaystatus() {
     return this.paystatus;
   }

   public void setPaystatus(Integer paystatus) { this.paystatus = paystatus; }

   public Integer getPayment_id() {
     return this.payment_id;
   }

   public void setPayment_id(Integer payment_id) { this.payment_id = payment_id; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\OrderReportAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */