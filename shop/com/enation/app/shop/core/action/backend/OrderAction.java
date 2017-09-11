 package com.enation.app.shop.core.action.backend;

 import com.enation.app.base.core.service.IRegionsManager;
 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.shop.core.model.Delivery;
 import com.enation.app.shop.core.model.Depot;
 import com.enation.app.shop.core.model.DlyCenter;
 import com.enation.app.shop.core.model.DlyType;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.app.shop.core.service.IDlyCenterManager;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.app.shop.core.service.IPromotionManager;
 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import net.sf.json.JSONArray;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;























 @Component
 @Scope("prototype")
 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Action("order")
 @Results({@org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/shop/admin/order/order_list.html"), @org.apache.struts2.convention.annotation.Result(name="trash_list", type="freemarker", location="/shop/admin/order/trash_list.html"), @org.apache.struts2.convention.annotation.Result(name="detail", type="freemarker", location="/shop/admin/order/order_detail.html"), @org.apache.struts2.convention.annotation.Result(name="not_ship", type="freemarker", location="/shop/admin/order/not_ship.html"), @org.apache.struts2.convention.annotation.Result(name="not_pay", type="freemarker", location="/shop/admin/order/not_pay.html"), @org.apache.struts2.convention.annotation.Result(name="not_rog", type="freemarker", location="/shop/admin/order/not_rog.html"), @org.apache.struts2.convention.annotation.Result(name="list_express", type="freemarker", location="/shop/admin/order/listForExpressNo.html")})
 public class OrderAction
   extends WWAction
 {
   private String sn;
   private String logi_no;
   private String uname;
   private String start_time;
   private String end_time;
   private String ship_name;
   private Integer status = null;
   private Integer paystatus = null;
   private Integer shipstatus = null;
   private Integer shipping_type = null;
   private Integer payment_id = null;

   private int shipping_id;

   private Integer stype;

   private String keyword;

   private Integer orderId;

   private String searchKey;

   private String searchValue;

   private IOrderManager orderManager;

   private IRegionsManager regionsManager;

   private IOrderFlowManager orderFlowManager;

   private IPromotionManager promotionManager;

   private OrderPluginBundle orderPluginBundle;

   protected Map<Integer, String> pluginTabs;

   protected Map<Integer, String> pluginHtmls;

   private Order ord;

   private List provinceList;
   private String alert_null;
   private Integer[] order_id;
   private double price;
   private double shipmoney;
   private String remark;
   private String start;
   private String end;
   private String next;
   private List<Delivery> deliveryList;
   private String addr;
   private String ship_day;
   private String ship_tel;
   private String ship_mobile;
   private String ship_zip;
   private Integer member_id;
   private IDlyTypeManager dlyTypeManager;
   private IPaymentManager paymentManager;
   private Map params;
   private List<DlyType> shipTypeList;
   private List<PayCfg> payTypeList;
   private IAdminUserManager adminUserManager;
   private List orderList;
   private Map orderMap;
   private Map statusMap;
   private Map payStatusMap;
   private Map shipMap;
   private String status_Json;
   private String payStatus_Json;
   private String ship_Json;
   private String orderstate;
   private IDlyCenterManager dlyCenterManager;
   private List<DlyCenter> dlycenterlist;
   private Integer depotid;
   private Integer paytypeid;
   private String cancel_reason;
   private String complete;
   private IDepotManager depotManager;

   public String savePrice()
   {
     try
     {
       double amount = this.orderManager.get(this.orderId).getOrder_amount().doubleValue();
       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       this.orderManager.savePrice(this.price, this.orderId.intValue());

       this.orderManager.log(this.orderId, "订单价格从:" + amount + "修改为" + this.price, null, adminUser.getUsername());
       showSuccessJson("成功" + this.price);
     } catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e);
       showErrorJson("失败");
     }
     return "json_message";
   }








   public String saveShipMoney()
   {
     try
     {
       double currshipamount = this.orderManager.get(this.orderId).getShipping_amount().doubleValue();
       double price = this.orderManager.saveShipmoney(this.shipmoney, this.orderId.intValue());

       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       this.orderManager.log(this.orderId, "运费从" + currshipamount + "修改为" + price, null, adminUser.getUsername());
       this.json = ("{\"result\":1,\"price\":\"" + price + "\"}");
     }
     catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e);
       showErrorJson("保存失败");
     }
     return "json_message";
   }













   public String saveAddr()
   {
     try
     {
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       String province = request.getParameter("province");
       String city = request.getParameter("city");
       String region = request.getParameter("region");
       String Attr = province + "-" + city + "-" + region;

       String province_id = request.getParameter("province_id");
       String city_id = request.getParameter("city_id");
       String region_id = request.getParameter("region_id");

       this.orderManager.saveAddr(this.orderId.intValue(), StringUtil.toInt(province_id, true), StringUtil.toInt(city_id, true), StringUtil.toInt(region_id, true), Attr);
       showSuccessJson("保存成功");
     } catch (Exception e) {
       e.printStackTrace();
       showErrorJson("保存失败");
     }
     return "json_message";
   }








   public String saveAddrDetail()
   {
     try
     {
       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       String oldAddr = this.orderManager.get(this.orderId).getShip_addr();
       boolean addr = this.orderManager.saveAddrDetail(this.addr, this.orderId.intValue());

       this.orderManager.log(this.orderId, "收货人详细地址从['" + oldAddr + "']修改为['" + addr + "']", null, adminUser.getUsername());
       if (addr) {
         showSuccessJson("成功");
       } else {
         showErrorJson("失败");
       }
     } catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e);
       showErrorJson("失败");
     }
     return "json_message";
   }
















   public String saveShipInfo()
   {
     try
     {
       Order order = this.orderManager.get(this.orderId);
       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       String oldShip_day = order.getShip_day();
       String oldship_name = order.getShip_name();
       String oldship_tel = order.getShip_tel();
       String oldship_mobile = order.getShip_mobile();
       String oldship_zip = order.getShip_zip();

       boolean addr = this.orderManager.saveShipInfo(this.remark, this.ship_day, this.ship_name, this.ship_tel, this.ship_mobile, this.ship_zip, this.orderId.intValue());

       if ((this.ship_day != null) && (!StringUtil.isEmpty(this.ship_day)))
         this.orderManager.log(this.orderId, "收货日期从['" + oldShip_day + "']修改为['" + this.ship_day + "']", null, adminUser.getUsername());
       if ((this.ship_name != null) && (!StringUtil.isEmpty(this.ship_name)))
         this.orderManager.log(this.orderId, "收货人姓名从['" + oldship_name + "']修改为['" + this.ship_name + "']", null, adminUser.getUsername());
       if ((this.ship_tel != null) && (!StringUtil.isEmpty(this.ship_tel)))
         this.orderManager.log(this.orderId, "收货人电话从['" + oldship_tel + "']修改为['" + this.ship_tel + "']", null, adminUser.getUsername());
       if ((this.ship_mobile != null) && (!StringUtil.isEmpty(this.ship_mobile)))
         this.orderManager.log(this.orderId, "收货人手机从['" + oldship_mobile + "']修改为['" + this.ship_mobile + "']", null, adminUser.getUsername());
       if ((this.ship_zip != null) && (!StringUtil.isEmpty(this.ship_zip))) {
         this.orderManager.log(this.orderId, "收货人邮编从['" + oldship_zip + "']修改为['" + this.ship_zip + "']", null, adminUser.getUsername());
       }
       if (addr) {
         showSuccessJson("成功!");
       } else {
         showErrorJson("失败!");
       }
     } catch (RuntimeException e) {
       showErrorJson("失败!" + e.getMessage());
     }
     return "json_message";
   }









   public String list()
   {
     if (this.statusMap == null) {
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









   public String notShipOrder()
   {
     if (this.statusMap == null) {
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
     return "not_ship";
   }









   public String notPayOrder()
   {
     if (this.statusMap == null) {
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
     return "not_pay";
   }









   public String notRogOrder()
   {
     if (this.statusMap == null) {
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
     return "not_rog";
   }
















   public String listJson()
   {
     HttpServletRequest requst = ThreadContextHolder.getHttpRequest();
     this.orderMap = new HashMap();
     this.orderMap.put("stype", this.stype);
     this.orderMap.put("keyword", this.keyword);
     this.orderMap.put("start_time", this.start_time);
     this.orderMap.put("end_time", this.end_time);
     this.orderMap.put("sn", this.sn);
     this.orderMap.put("ship_name", this.ship_name);
     this.orderMap.put("status", this.status);
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










   public String trash_list()
   {
     if (this.statusMap == null) {
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

     return "trash_list";
   }















   public String trash_listJson()
   {
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

     this.webpage = this.orderManager.list(getPage(), getPageSize(), 1, getSort());
     showGridJson(this.webpage);
     return "json_message";
   }




   public String delete()
   {
     try
     {
       if (this.orderManager.delete(this.order_id)) {
         showSuccessJson("订单加入回收站成功");
       } else {
         showErrorJson("您所删除的订单包含未作废的订单，无法加入回收站");
       }
     } catch (RuntimeException e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug(e);
       }
       showErrorJson("订单删除失败" + e.getMessage());
     }

     return "json_message";
   }





   public String revert()
   {
     try
     {
       this.orderManager.revert(this.order_id);
       showSuccessJson("订单还原成功");
     } catch (RuntimeException e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug(e);
       }
       showErrorJson("订单还原失败:" + e.getMessage());
     }
     return "json_message";
   }





   public String clean()
   {
     try
     {
       this.orderManager.clean(this.order_id);
       showSuccessJson("订单清除成功");
     } catch (RuntimeException e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug(e);
       }
       showErrorJson("订单清除失败" + e.getMessage());
     }

     return "json_message";
   }









   public String complete()
   {
     try
     {
       this.orderFlowManager.complete(this.orderId);
       Order order = this.orderManager.get(this.orderId);
       showSuccessJson("订单[" + order.getSn() + "]成功标记为完成状态");
     } catch (RuntimeException e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug(e);
       }
       showErrorJson("订单完成失败");
     }
     return "json_message";
   }







   public String cancel()
   {
     try
     {
       this.orderFlowManager.cancel(this.orderId, this.cancel_reason);
       Order order = this.orderManager.get(this.orderId);
       this.json = ("{result:1,message:'订单[" + order.getSn() + "]成功作废',orderStatus:" + order.getStatus() + "}");
       showSuccessJson("订单作废成功");
     } catch (RuntimeException e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug(e);
       }

       showErrorJson("订单作废失败");
     }
     return "json_message";
   }






   public String rogConfirm()
   {
     try
     {
       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       this.orderFlowManager.rogConfirm(this.orderId.intValue(), adminUser.getUserid(), adminUser.getUsername(), adminUser.getUsername(), Long.valueOf(DateUtil.getDatelineLong()));
       showSuccessJson("确认收货成功");
     } catch (Exception e) {
       showErrorJson("数据库错误");
     }
     return "json_message";
   }





   public String confirmOrder()
   {
     try
     {
       this.orderFlowManager.confirmOrder(this.orderId);
       Order order = this.orderManager.get(this.orderId);
       this.orderFlowManager.addCodPaymentLog(order);
       showSuccessJson("'订单[" + order.getSn() + "]成功确认'");
     } catch (RuntimeException e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug(e);
       }
       showErrorJson("订单确认失败" + e.getMessage());
     }
     return "json_message";
   }













   public String detail()
   {
     if (this.ship_name != null) this.ship_name = StringUtil.toUTF8(this.ship_name);
     if (this.uname != null) this.uname = StringUtil.toUTF8(this.uname);
     this.ord = this.orderManager.get(this.orderId);
     this.provinceList = this.regionsManager.listProvince();

     this.pluginTabs = this.orderPluginBundle.getTabList(this.ord);
     this.pluginHtmls = this.orderPluginBundle.getDetailHtml(this.ord);
     this.dlycenterlist = this.dlyCenterManager.list();

     return "detail";
   }













   public String nextDetail()
   {
     if (this.orderManager.getNext(this.next, this.orderId, this.status, 0, this.sn, this.logi_no, this.uname, this.ship_name) == null) {
       this.alert_null = "kong";
       this.ord = this.orderManager.get(this.orderId);
     } else {
       this.ord = this.orderManager.getNext(this.next, this.orderId, this.status, 0, this.sn, this.logi_no, this.uname, this.ship_name);
     }

     this.orderId = (this.ord == null ? this.orderId : this.ord.getOrder_id());
     this.provinceList = this.regionsManager.listProvince();


     this.pluginTabs = this.orderPluginBundle.getTabList(this.ord);
     this.pluginHtmls = this.orderPluginBundle.getDetailHtml(this.ord);


     return "detail";
   }








   public String saveAdminRemark()
   {
     String encoding = EopSetting.ENCODING;
     if ((this.remark != null) && (!StringUtil.isEmpty(encoding))) {
       this.remark = StringUtil.to(this.remark, encoding);
     }
     this.ord = this.orderManager.get(this.orderId);
     this.ord.setAdmin_remark(this.remark);
     try {
       this.orderManager.edit(this.ord);
       showSuccessJson("修改成功");
     } catch (RuntimeException e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug(e);
       }
       showErrorJson("修改失败");
     }
     return "json_message";
   }







   public String listOrderByMemberId()
   {
     List list = this.orderManager.listOrderByMemberId(this.member_id.intValue());
     showGridJson(list);
     return "json_message";
   }




   private Map getStatusJson()
   {
     Map orderStatus = new HashMap();

     orderStatus.put("0", OrderStatus.getOrderStatusText(0));
     orderStatus.put("2", OrderStatus.getOrderStatusText(2));
     orderStatus.put("5", OrderStatus.getOrderStatusText(5));
     orderStatus.put("6", OrderStatus.getOrderStatusText(6));
     orderStatus.put("-2", OrderStatus.getOrderStatusText(-2));
     orderStatus.put("7", OrderStatus.getOrderStatusText(7));
     orderStatus.put("-1", OrderStatus.getOrderStatusText(-1));
     orderStatus.put("8", OrderStatus.getOrderStatusText(8));
     orderStatus.put("-7", OrderStatus.getOrderStatusText(-7));
     orderStatus.put("-4", OrderStatus.getOrderStatusText(-4));
     orderStatus.put("-3", OrderStatus.getOrderStatusText(-3));





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



   private Map getShipJson()
   {
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










   public String saveDepot()
   {
     try
     {
       String oldname = this.depotManager.get(this.orderManager.get(this.orderId).getDepotid().intValue()).getName();
       String depotname = this.depotManager.get(this.depotid.intValue()).getName();
       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       this.orderManager.saveDepot(this.orderId.intValue(), this.depotid.intValue());

       this.orderManager.log(this.orderId, "修改仓库从" + oldname + "修改为" + depotname, adminUser.getUserid(), adminUser.getUsername());
       showSuccessJson("保存库房成功");
     } catch (Exception e) {
       showErrorJson("保存库房出错:" + e.getMessage());
     }

     return "json_message";
   }






   public String savePayType()
   {
     try
     {
       this.orderManager.savePayType(this.orderId.intValue(), this.paytypeid.intValue());
       showSuccessJson("保存配送方式成功");
     } catch (Exception e) {
       this.logger.error("保存配送方式出错", e);
       showErrorJson("保存配送方式出错");
     }


     return "json_message";
   }






   public String saveShipType()
   {
     try
     {
       this.orderManager.saveShipType(this.orderId.intValue(), this.shipping_id);
       showSuccessJson("保存配送方式成功");
     } catch (Exception e) {
       this.logger.error("保存配送方式出错", e);
       showErrorJson("保存配送方式出错");
     }


     return "json_message";
   }

   public String getSn()
   {
     return this.sn;
   }

   public void setSn(String sn) { this.sn = sn; }

   public String getLogi_no() {
     return this.logi_no;
   }

   public void setLogi_no(String logi_no) { this.logi_no = logi_no; }

   public String getUname() {
     return this.uname;
   }

   public void setUname(String uname) { this.uname = uname; }

   public String getStart_time() {
     return this.start_time;
   }

   public void setStart_time(String start_time) { this.start_time = start_time; }

   public String getEnd_time() {
     return this.end_time;
   }

   public void setEnd_time(String end_time) { this.end_time = end_time; }

   public String getShip_name() {
     return this.ship_name;
   }

   public void setShip_name(String ship_name) { this.ship_name = ship_name; }

   public Integer getStatus() {
     return this.status;
   }

   public void setStatus(Integer status) { this.status = status; }

   public Integer getPaystatus() {
     return this.paystatus;
   }

   public void setPaystatus(Integer paystatus) { this.paystatus = paystatus; }

   public Integer getShipstatus() {
     return this.shipstatus;
   }

   public void setShipstatus(Integer shipstatus) { this.shipstatus = shipstatus; }

   public Integer getShipping_type() {
     return this.shipping_type;
   }

   public void setShipping_type(Integer shipping_type) { this.shipping_type = shipping_type; }

   public Integer getPayment_id() {
     return this.payment_id;
   }

   public void setPayment_id(Integer payment_id) { this.payment_id = payment_id; }

   public int getShipping_id() {
     return this.shipping_id;
   }

   public void setShipping_id(int shipping_id) { this.shipping_id = shipping_id; }

   public Integer getStype() {
     return this.stype;
   }

   public void setStype(Integer stype) { this.stype = stype; }

   public String getKeyword() {
     return this.keyword;
   }

   public void setKeyword(String keyword) { this.keyword = keyword; }

   public Integer getOrderId() {
     return this.orderId;
   }

   public void setOrderId(Integer orderId) { this.orderId = orderId; }

   public String getSearchKey() {
     return this.searchKey;
   }

   public void setSearchKey(String searchKey) { this.searchKey = searchKey; }

   public String getSearchValue() {
     return this.searchValue;
   }

   public void setSearchValue(String searchValue) { this.searchValue = searchValue; }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) { this.orderManager = orderManager; }

   public IRegionsManager getRegionsManager() {
     return this.regionsManager;
   }

   public void setRegionsManager(IRegionsManager regionsManager) { this.regionsManager = regionsManager; }

   public IOrderFlowManager getOrderFlowManager() {
     return this.orderFlowManager;
   }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) { this.orderFlowManager = orderFlowManager; }

   public IPromotionManager getPromotionManager() {
     return this.promotionManager;
   }

   public void setPromotionManager(IPromotionManager promotionManager) { this.promotionManager = promotionManager; }

   public OrderPluginBundle getOrderPluginBundle() {
     return this.orderPluginBundle;
   }

   public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle) { this.orderPluginBundle = orderPluginBundle; }

   public Map<Integer, String> getPluginTabs() {
     return this.pluginTabs;
   }

   public void setPluginTabs(Map<Integer, String> pluginTabs) { this.pluginTabs = pluginTabs; }

   public Map<Integer, String> getPluginHtmls() {
     return this.pluginHtmls;
   }

   public void setPluginHtmls(Map<Integer, String> pluginHtmls) { this.pluginHtmls = pluginHtmls; }

   public Order getOrd() {
     return this.ord;
   }

   public void setOrd(Order ord) { this.ord = ord; }

   public List getProvinceList() {
     return this.provinceList;
   }

   public void setProvinceList(List provinceList) { this.provinceList = provinceList; }

   public String getAlert_null() {
     return this.alert_null;
   }

   public void setAlert_null(String alert_null) { this.alert_null = alert_null; }

   public Integer[] getOrder_id() {
     return this.order_id;
   }

   public void setOrder_id(Integer[] order_id) { this.order_id = order_id; }

   public double getPrice() {
     return this.price;
   }

   public void setPrice(double price) { this.price = price; }

   public double getShipmoney() {
     return this.shipmoney;
   }

   public void setShipmoney(double shipmoney) { this.shipmoney = shipmoney; }

   public String getRemark() {
     return this.remark;
   }

   public void setRemark(String remark) { this.remark = remark; }

   public String getStart() {
     return this.start;
   }

   public void setStart(String start) { this.start = start; }

   public String getEnd() {
     return this.end;
   }

   public void setEnd(String end) { this.end = end; }

   public String getNext() {
     return this.next;
   }

   public void setNext(String next) { this.next = next; }

   public List<Delivery> getDeliveryList() {
     return this.deliveryList;
   }

   public void setDeliveryList(List<Delivery> deliveryList) { this.deliveryList = deliveryList; }

   public String getAddr() {
     return this.addr;
   }

   public void setAddr(String addr) { this.addr = addr; }

   public String getShip_day() {
     return this.ship_day;
   }

   public void setShip_day(String ship_day) { this.ship_day = ship_day; }

   public String getShip_tel() {
     return this.ship_tel;
   }

   public void setShip_tel(String ship_tel) { this.ship_tel = ship_tel; }

   public String getShip_mobile() {
     return this.ship_mobile;
   }

   public void setShip_mobile(String ship_mobile) { this.ship_mobile = ship_mobile; }

   public String getShip_zip() {
     return this.ship_zip;
   }

   public void setShip_zip(String ship_zip) { this.ship_zip = ship_zip; }

   public Integer getMember_id() {
     return this.member_id;
   }

   public void setMember_id(Integer member_id) { this.member_id = member_id; }

   public IDlyTypeManager getDlyTypeManager() {
     return this.dlyTypeManager;
   }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) { this.dlyTypeManager = dlyTypeManager; }

   public IPaymentManager getPaymentManager() {
     return this.paymentManager;
   }

   public void setPaymentManager(IPaymentManager paymentManager) { this.paymentManager = paymentManager; }

   public Map getParams() {
     return this.params;
   }

   public void setParams(Map params) { this.params = params; }

   public List<DlyType> getShipTypeList() {
     return this.shipTypeList;
   }

   public void setShipTypeList(List<DlyType> shipTypeList) { this.shipTypeList = shipTypeList; }

   public List<PayCfg> getPayTypeList() {
     return this.payTypeList;
   }

   public void setPayTypeList(List<PayCfg> payTypeList) { this.payTypeList = payTypeList; }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) { this.adminUserManager = adminUserManager; }

   public List getOrderList() {
     return this.orderList;
   }

   public void setOrderList(List orderList) { this.orderList = orderList; }

   public Map getOrderMap() {
     return this.orderMap;
   }

   public void setOrderMap(Map orderMap) { this.orderMap = orderMap; }

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

   public String getOrderstate() {
     return this.orderstate;
   }

   public void setOrderstate(String orderstate) { this.orderstate = orderstate; }

   public IDlyCenterManager getDlyCenterManager() {
     return this.dlyCenterManager;
   }

   public void setDlyCenterManager(IDlyCenterManager dlyCenterManager) { this.dlyCenterManager = dlyCenterManager; }

   public List<DlyCenter> getDlycenterlist() {
     return this.dlycenterlist;
   }

   public void setDlycenterlist(List<DlyCenter> dlycenterlist) { this.dlycenterlist = dlycenterlist; }

   public Integer getDepotid() {
     return this.depotid;
   }

   public void setDepotid(Integer depotid) { this.depotid = depotid; }

   public Integer getPaytypeid() {
     return this.paytypeid;
   }

   public void setPaytypeid(Integer paytypeid) { this.paytypeid = paytypeid; }

   public String getCancel_reason() {
     return this.cancel_reason;
   }

   public void setCancel_reason(String cancel_reason) { this.cancel_reason = cancel_reason; }

   public String getComplete() {
     return this.complete;
   }

   public void setComplete(String complete) { this.complete = complete; }

   public IDepotManager getDepotManager() {
     return this.depotManager;
   }

   public void setDepotManager(IDepotManager depotManager) { this.depotManager = depotManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\OrderAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */