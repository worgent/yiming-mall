 package com.enation.app.b2b2c.core.action.backend.order;

 import com.enation.app.b2b2c.core.service.order.IB2B2cOrderReportManager;
 import com.enation.app.b2b2c.core.service.order.IStoreOrderManager;
 import com.enation.app.shop.core.model.DlyType;
 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.framework.action.WWAction;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
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
 @Action("storeOrderReport")
 public class StoreOrderReportAction
   extends WWAction
 {
   private IStoreOrderManager storeOrderManager;
   private IB2B2cOrderReportManager b2B2cOrderReportManager;
   private IDlyTypeManager dlyTypeManager;
   private IPaymentManager paymentManager;
   private List<DlyType> shipTypeList;
   private List<PayCfg> payTypeList;
   private Map statusMap;
   private Map payStatusMap;
   private Map shipMap;
   private Map orderMap;
   private String payStatus_Json;
   private String status_Json;
   private String ship_Json;
   private Integer stype;
   private String keyword;
   private String start_time;
   private String end_time;
   private String sn;
   private String order;
   private Integer paystatus = null;
   private Integer payment_id = null;
   private String store_name;

   public String paymentList() {
     if (this.statusMap == null) {
       this.statusMap = new HashMap();
       this.statusMap = this.storeOrderManager.getStatusJson();
       String p = JSONArray.fromObject(this.statusMap).toString();
       this.status_Json = p.replace("[", "").replace("]", "");
     }

     if (this.payStatusMap == null) {
       this.payStatusMap = new HashMap();
       this.payStatusMap = this.storeOrderManager.getpPayStatusJson();
       String p = JSONArray.fromObject(this.payStatusMap).toString();
       this.payStatus_Json = p.replace("[", "").replace("]", "");
     }


     if (this.shipMap == null) {
       this.shipMap = new HashMap();
       this.shipMap = this.storeOrderManager.getShipJson();
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
     this.orderMap.put("store_name", this.store_name);

     this.webpage = this.b2B2cOrderReportManager.listPayment(this.orderMap, getPage(), getPageSize(), this.order);

     showGridJson(this.webpage);
     return "json_message";
   }

   public IStoreOrderManager getStoreOrderManager() {
     return this.storeOrderManager;
   }

   public void setStoreOrderManager(IStoreOrderManager storeOrderManager) {
     this.storeOrderManager = storeOrderManager;
   }

   public IDlyTypeManager getDlyTypeManager() { return this.dlyTypeManager; }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager)
   {
     this.dlyTypeManager = dlyTypeManager;
   }

   public IPaymentManager getPaymentManager() {
     return this.paymentManager;
   }

   public void setPaymentManager(IPaymentManager paymentManager) {
     this.paymentManager = paymentManager;
   }

   public List<DlyType> getShipTypeList() {
     return this.shipTypeList;
   }

   public void setShipTypeList(List<DlyType> shipTypeList) {
     this.shipTypeList = shipTypeList;
   }

   public List<PayCfg> getPayTypeList() {
     return this.payTypeList;
   }

   public void setPayTypeList(List<PayCfg> payTypeList) {
     this.payTypeList = payTypeList;
   }

   public Map getStatusMap() {
     return this.statusMap;
   }

   public void setStatusMap(Map statusMap) {
     this.statusMap = statusMap;
   }

   public Map getPayStatusMap() {
     return this.payStatusMap;
   }

   public void setPayStatusMap(Map payStatusMap) {
     this.payStatusMap = payStatusMap;
   }

   public Map getShipMap() {
     return this.shipMap;
   }

   public void setShipMap(Map shipMap) {
     this.shipMap = shipMap;
   }

   public String getPayStatus_Json() {
     return this.payStatus_Json;
   }

   public void setPayStatus_Json(String payStatus_Json) {
     this.payStatus_Json = payStatus_Json;
   }

   public String getStatus_Json() {
     return this.status_Json;
   }

   public void setStatus_Json(String status_Json) {
     this.status_Json = status_Json;
   }

   public String getShip_Json() {
     return this.ship_Json;
   }

   public void setShip_Json(String ship_Json) {
     this.ship_Json = ship_Json;
   }

   public IB2B2cOrderReportManager getB2B2cOrderReportManager() { return this.b2B2cOrderReportManager; }

   public void setB2B2cOrderReportManager(IB2B2cOrderReportManager b2b2cOrderReportManager)
   {
     this.b2B2cOrderReportManager = b2b2cOrderReportManager;
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

   public String getSn() { return this.sn; }

   public void setSn(String sn) {
     this.sn = sn;
   }

   public String getOrder() { return this.order; }

   public void setOrder(String order) {
     this.order = order;
   }

   public Integer getPaystatus() { return this.paystatus; }

   public void setPaystatus(Integer paystatus) {
     this.paystatus = paystatus;
   }

   public Integer getPayment_id() { return this.payment_id; }

   public void setPayment_id(Integer payment_id) {
     this.payment_id = payment_id;
   }

   public String getStore_name() { return this.store_name; }

   public void setStore_name(String store_name) {
     this.store_name = store_name;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\backend\order\StoreOrderReportAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */