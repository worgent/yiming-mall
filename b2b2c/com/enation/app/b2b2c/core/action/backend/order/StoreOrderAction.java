 package com.enation.app.b2b2c.core.action.backend.order;

 import com.enation.app.b2b2c.core.model.order.StoreOrder;
 import com.enation.app.b2b2c.core.service.order.IStoreOrderManager;
 import com.enation.app.shop.core.model.DlyType;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import net.sf.json.JSONArray;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.stereotype.Component;













 @Component
 @ParentPackage("eop_default")
 @Namespace("/b2b2c/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/b2b2c/admin/order/store_order_list.html"), @org.apache.struts2.convention.annotation.Result(name="detail", type="freemarker", location="/b2b2c/admin/order/store_order_detail.html")})
 @Action("storeOrder")
 public class StoreOrderAction
   extends WWAction
 {
   private Order ord;
   private IOrderManager orderManager;
   private IStoreOrderManager storeOrderManager;
   private Integer orderId;
   private List<Map> orderChildList;
   private Map orderMap;
   private Integer stype;
   private String keyword;
   private String start_time;
   private String end_time;
   private Integer status = null;
   private String sn;
   private String ship_name;
   private Integer paystatus = null;
   private Integer shipstatus = null;
   private Integer shipping_type = null;
   private Integer payment_id = null;
   private String complete;
   private Map statusMap;
   private Map payStatusMap;
   private Map shipMap;
   private List<DlyType> shipTypeList;
   private List<PayCfg> payTypeList;
   private String status_Json;
   private String payStatus_Json;
   private String ship_Json;
   private IDlyTypeManager dlyTypeManager;
   private IPaymentManager paymentManager;

   public String orderDetail() {
     this.ord = this.orderManager.get(this.orderId);

     List<StoreOrder> orderList = this.storeOrderManager.storeOrderList(this.orderId);
     this.orderChildList = new ArrayList();
     for (StoreOrder storeOrder : orderList)
     {
       List itemList = this.orderManager.listGoodsItems(storeOrder.getOrder_id());
       Map map = new HashMap();
       map.put("storeOrder", storeOrder);
       map.put("goodsList", itemList);
       this.orderChildList.add(map);
     }
     return "detail";
   }

   public String list() { if (this.statusMap == null) {
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
     return "list";
   }

   public String listJson() { HttpServletRequest requst = ThreadContextHolder.getHttpRequest();
     this.orderMap = new HashMap();
     this.orderMap.put("stype", this.stype);
     this.orderMap.put("keyword", this.keyword);
     this.orderMap.put("start_time", this.start_time);
     this.orderMap.put("end_time", this.end_time);
     this.orderMap.put("status", this.status);
     this.orderMap.put("sn", this.sn);
     this.orderMap.put("ship_name", this.ship_name);
     this.orderMap.put("paystatus", this.paystatus);
     this.orderMap.put("shipstatus", this.shipstatus);
     this.orderMap.put("shipping_type", this.shipping_type);
     this.orderMap.put("payment_id", this.payment_id);
     this.orderMap.put("order_state", requst.getParameter("order_state"));
     this.orderMap.put("complete", this.complete);

     this.webpage = this.orderManager.listOrder(this.orderMap, getPage(), getPageSize(), getSort(), getOrder());
     showGridJson(this.webpage);
     return "json_message";
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
     orderStatus.put("-7", OrderStatus.getOrderStatusText(-7));
     orderStatus.put("-4", OrderStatus.getOrderStatusText(-4));
     orderStatus.put("-3", OrderStatus.getOrderStatusText(-3));
     orderStatus.put("1", OrderStatus.getOrderStatusText(1));
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

   public Order getOrd() { return this.ord; }

   public void setOrd(Order ord) {
     this.ord = ord;
   }

   public IOrderManager getOrderManager() { return this.orderManager; }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IStoreOrderManager getStoreOrderManager() { return this.storeOrderManager; }

   public void setStoreOrderManager(IStoreOrderManager storeOrderManager) {
     this.storeOrderManager = storeOrderManager;
   }

   public Integer getOrderId() { return this.orderId; }

   public void setOrderId(Integer orderId) {
     this.orderId = orderId;
   }

   public List<Map> getOrderChildList() { return this.orderChildList; }

   public void setOrderChildList(List<Map> orderChildList) {
     this.orderChildList = orderChildList;
   }

   public Map getOrderMap() { return this.orderMap; }

   public void setOrderMap(Map orderMap) {
     this.orderMap = orderMap;
   }

   public Integer getStype() { return this.stype; }

   public void setStype(Integer stype) {
     this.stype = stype;
   }

   public String getKeyword() { return this.keyword; }

   public void setKeyword(String keyword) {
     this.keyword = keyword;
   }

   public String getStart_time() { return this.start_time; }

   public void setStart_time(String start_time) {
     this.start_time = start_time;
   }

   public String getEnd_time() { return this.end_time; }

   public void setEnd_time(String end_time) {
     this.end_time = end_time;
   }

   public Integer getStatus() { return this.status; }

   public void setStatus(Integer status) {
     this.status = status;
   }

   public String getSn() { return this.sn; }

   public void setSn(String sn) {
     this.sn = sn;
   }

   public String getShip_name() { return this.ship_name; }

   public void setShip_name(String ship_name) {
     this.ship_name = ship_name;
   }

   public Integer getPaystatus() { return this.paystatus; }

   public void setPaystatus(Integer paystatus) {
     this.paystatus = paystatus;
   }

   public Integer getShipstatus() { return this.shipstatus; }

   public void setShipstatus(Integer shipstatus) {
     this.shipstatus = shipstatus;
   }

   public Integer getShipping_type() { return this.shipping_type; }

   public void setShipping_type(Integer shipping_type) {
     this.shipping_type = shipping_type;
   }

   public Integer getPayment_id() { return this.payment_id; }

   public void setPayment_id(Integer payment_id) {
     this.payment_id = payment_id;
   }

   public String getComplete() { return this.complete; }

   public void setComplete(String complete) {
     this.complete = complete;
   }

   public Map getStatusMap() { return this.statusMap; }

   public void setStatusMap(Map statusMap) {
     this.statusMap = statusMap;
   }

   public Map getPayStatusMap() { return this.payStatusMap; }

   public void setPayStatusMap(Map payStatusMap) {
     this.payStatusMap = payStatusMap;
   }

   public Map getShipMap() { return this.shipMap; }

   public void setShipMap(Map shipMap) {
     this.shipMap = shipMap;
   }

   public List<DlyType> getShipTypeList() { return this.shipTypeList; }

   public void setShipTypeList(List<DlyType> shipTypeList) {
     this.shipTypeList = shipTypeList;
   }

   public List<PayCfg> getPayTypeList() { return this.payTypeList; }

   public void setPayTypeList(List<PayCfg> payTypeList) {
     this.payTypeList = payTypeList;
   }

   public String getStatus_Json() { return this.status_Json; }

   public void setStatus_Json(String status_Json) {
     this.status_Json = status_Json;
   }

   public String getPayStatus_Json() { return this.payStatus_Json; }

   public void setPayStatus_Json(String payStatus_Json) {
     this.payStatus_Json = payStatus_Json;
   }

   public String getShip_Json() { return this.ship_Json; }

   public void setShip_Json(String ship_Json) {
     this.ship_Json = ship_Json;
   }

   public IDlyTypeManager getDlyTypeManager() { return this.dlyTypeManager; }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
     this.dlyTypeManager = dlyTypeManager;
   }

   public IPaymentManager getPaymentManager() { return this.paymentManager; }

   public void setPaymentManager(IPaymentManager paymentManager) {
     this.paymentManager = paymentManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\backend\order\StoreOrderAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */