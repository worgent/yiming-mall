 package com.enation.app.b2b2c.core.action.api.order;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.cart.IStoreCartManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.b2b2c.core.service.order.IStoreOrderManager;
 import com.enation.app.base.core.model.MemberAddress;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.support.OrderPrice;
 import com.enation.app.shop.core.service.IMemberAddressManager;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IOrderPrintManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.CurrencyUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;




















 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/store")
 @Action("storeOrder")
 public class StoreOrderApiAction
   extends WWAction
 {
   private IOrderManager orderManager;
   private IStoreOrderManager storeOrderManager;
   private IOrderFlowManager orderFlowManager;
   private IMemberAddressManager memberAddressManager;
   private IOrderPrintManager orderPrintManager;
   private IStoreCartManager storeCartManager;
   private IStoreMemberManager storeMemberManager;
   private Integer orderId;
   private Integer[] order_id;
   private Integer paymentId;
   private Double payMoney;
   private Double shipmoney;
   private String remark;
   private String ship_day;
   private String ship_name;
   private String ship_tel;
   private String ship_mobile;
   private String ship_zip;
   private String storeids;
   private String shippingids;
   private Integer regionid;
   private String addr;
   private String[] shipNos;

   private MemberAddress createAddress()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     MemberAddress address = new MemberAddress();


     String name = request.getParameter("shipName");
     address.setName(name);

     String tel = request.getParameter("shipTel");
     address.setTel(tel);

     String mobile = request.getParameter("shipMobile");
     address.setMobile(mobile);

     String province_id = request.getParameter("province_id");
     if (province_id != null) {
       address.setProvince_id(Integer.valueOf(province_id));
     }

     String city_id = request.getParameter("city_id");
     if (city_id != null) {
       address.setCity_id(Integer.valueOf(city_id));
     }

     String region_id = request.getParameter("region_id");
     if (region_id != null) {
       address.setRegion_id(Integer.valueOf(region_id));
     }

     String province = request.getParameter("province");
     address.setProvince(province);

     String city = request.getParameter("city");
     address.setCity(city);

     String region = request.getParameter("region");
     address.setRegion(region);

     String addr = request.getParameter("shipAddr");
     address.setAddr(addr);

     String zip = request.getParameter("shipZip");
     address.setZip(zip);

     return address;
   }




   public String confirm()
   {
     try
     {
       this.orderFlowManager.confirmOrder(this.orderId);
       Order order = this.orderManager.get(this.orderId);

       showSuccessJson("'订单[" + order.getSn() + "]成功确认'");
     } catch (RuntimeException e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug(e);
       }
       showErrorJson("订单确认失败" + e.getMessage());
     }
     return "json_message";
   }








   public String pay()
   {
     try
     {
       StoreMember member = this.storeMemberManager.getStoreMember();
       Order order = this.orderManager.get(this.orderId);

       if (this.orderFlowManager.pay(this.paymentId, this.orderId, this.payMoney.doubleValue(), member.getUname())) {
         showSuccessJson("订单[" + order.getSn() + "]收款成功");
       } else {
         showErrorJson("订单[" + order.getSn() + "]收款失败,您输入的付款金额合计大于应付金额");
       }
     } catch (RuntimeException e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug(e);
       }
       showErrorJson("确认付款失败:" + e.getMessage());
     }
     return "json_message";
   }




   public String ship()
   {
     try
     {
       String is_ship = this.orderPrintManager.ship(this.order_id);
       if (is_ship.equals("true")) {
         showSuccessJson("发货成功");
       } else {
         showErrorJson(is_ship);
       }
     } catch (Exception e) {
       e.printStackTrace();
       showErrorJson(e.getMessage());
       this.logger.error("发货出错", e);
     }
     return "json_message";
   }







   public String saveShipPrice()
   {
     try
     {
       double currshipamount = this.orderManager.get(this.orderId).getShipping_amount().doubleValue();
       double price = this.orderManager.saveShipmoney(this.shipmoney.doubleValue(), this.orderId.intValue());

       StoreMember member = this.storeMemberManager.getStoreMember();

       showSuccessJson("保存成功");
     } catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e);
       showErrorJson("保存失败");
     }
     return "json_message";
   }







   public String savePrice()
   {
     try
     {
       double amount = this.orderManager.get(this.orderId).getOrder_amount().doubleValue();
       this.orderManager.savePrice(this.payMoney.doubleValue(), this.orderId.intValue());

       StoreMember member = this.storeMemberManager.getStoreMember();

       showSuccessJson("修改订单价格成功");
     } catch (Exception e) {
       showErrorJson("修改订单价格失败");
       this.logger.error(e);
     }
     return "json_message";
   }
















   public String saveConsigee()
   {
     try
     {
       Order order = this.orderManager.get(getOrderId());
       StoreMember member = this.storeMemberManager.getStoreMember();
       String oldShip_day = order.getShip_day();
       String oldship_name = order.getShip_name();
       String oldship_tel = order.getShip_tel();
       String oldship_mobile = order.getShip_mobile();
       String oldship_zip = order.getShip_zip();

       boolean addr = this.storeOrderManager.saveShipInfo(this.remark, this.ship_day, this.ship_name, this.ship_tel, this.ship_mobile, this.ship_zip, getOrderId().intValue());












       saveAddr();
       showSuccessJson("修改成功");
     } catch (Exception e) {
       e.printStackTrace();
       showErrorJson("修改失败");
       this.logger.error(e);
     }
     return "json_message";
   }















   private void saveAddr()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String province = request.getParameter("province");
     String city = request.getParameter("city");
     String region = request.getParameter("region");
     String Attr = province + "-" + city + "-" + region;

     String province_id = request.getParameter("province_id");
     String city_id = request.getParameter("city_id");
     String region_id = request.getParameter("region_id");

     String oldAddr = this.orderManager.get(this.orderId).getShip_addr();
     StoreMember member = this.storeMemberManager.getStoreMember();
     this.orderManager.saveAddr(this.orderId.intValue(), StringUtil.toInt(province_id, true), StringUtil.toInt(city_id, true), StringUtil.toInt(region_id, true), Attr);
     this.orderManager.saveAddrDetail(getAddr(), getOrderId().intValue());
   }







   private MemberAddress memberAddress()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String address_id = request.getParameter("addressId");
     MemberAddress address = this.memberAddressManager.getAddress(Integer.valueOf(address_id).intValue());
     return address;
   }











   public String getOrderPrice()
   {
     String[] storeid = this.storeids.split(",");
     String[] typeid = this.shippingids.split(",");

     List<Map> storeGoodsList = new ArrayList();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String sessionid = request.getSession().getId();
     storeGoodsList = this.storeCartManager.storeListGoods(sessionid);

     String storeprices = "";
     Double totleprice = Double.valueOf(0.0D);
     Double goodsprice = Double.valueOf(0.0D);
     Double shippingprice = Double.valueOf(0.0D);
     for (Map map : storeGoodsList) {
       Integer store_id = (Integer)map.get("store_id");
       List list = (List)map.get("goodslist");
       for (int i = 0; i < storeid.length; i++) {
         if (store_id.equals(Integer.valueOf(storeid[i]))) {
           OrderPrice orderPrice = this.storeCartManager.countPrice(list, this.regionid + "", Integer.valueOf(typeid[i]), Boolean.valueOf(false));
           storeprices = storeprices + "," + orderPrice.getOrderPrice();
           totleprice = Double.valueOf(CurrencyUtil.add(totleprice.doubleValue(), orderPrice.getOrderPrice().doubleValue()));
           shippingprice = Double.valueOf(CurrencyUtil.add(shippingprice.doubleValue(), orderPrice.getShippingPrice().doubleValue()));
           goodsprice = Double.valueOf(CurrencyUtil.add(goodsprice.doubleValue(), orderPrice.getGoodsPrice().doubleValue()));
           break;
         }
       }
     }
     storeprices = storeprices.substring(1, storeprices.length());
     this.json = ("{\"result\":1,\"storeprice\":\"" + storeprices + "\",\"totleprice\":\"" + totleprice + "\",\"goodsprice\":\"" + goodsprice + "\",\"shippingprice\":\"" + shippingprice + "\"}");
     return "json_message";
   }





   public String saveShopNos()
   {
     try
     {
       this.orderPrintManager.saveShopNos(this.order_id, this.shipNos);
       showSuccessJson("保存成功");
     } catch (Exception e) {
       showErrorJson("保存失败");
     }
     return "json_message";
   }

   public IStoreOrderManager getStoreOrderManager() {
     return this.storeOrderManager;
   }

   public void setStoreOrderManager(IStoreOrderManager storeOrderManager) { this.storeOrderManager = storeOrderManager; }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) { this.storeMemberManager = storeMemberManager; }

   public IMemberAddressManager getMemberAddressManager() {
     return this.memberAddressManager;
   }

   public void setMemberAddressManager(IMemberAddressManager memberAddressManager) { this.memberAddressManager = memberAddressManager; }

   public IOrderFlowManager getOrderFlowManager() {
     return this.orderFlowManager;
   }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) { this.orderFlowManager = orderFlowManager; }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) { this.orderManager = orderManager; }

   public IOrderPrintManager getOrderPrintManager() {
     return this.orderPrintManager;
   }

   public void setOrderPrintManager(IOrderPrintManager orderPrintManager) { this.orderPrintManager = orderPrintManager; }

   public IStoreCartManager getStoreCartManager() {
     return this.storeCartManager;
   }

   public void setStoreCartManager(IStoreCartManager storeCartManager) { this.storeCartManager = storeCartManager; }

   public Integer getOrderId() {
     return this.orderId;
   }

   public void setOrderId(Integer orderId) { this.orderId = orderId; }

   public Integer[] getOrder_id() {
     return this.order_id;
   }

   public void setOrder_id(Integer[] order_id) { this.order_id = order_id; }

   public Integer getPaymentId() {
     return this.paymentId;
   }

   public void setPaymentId(Integer paymentId) { this.paymentId = paymentId; }

   public Double getPayMoney() {
     return this.payMoney;
   }

   public void setPayMoney(Double payMoney) { this.payMoney = payMoney; }

   public Double getShipmoney() {
     return this.shipmoney;
   }

   public void setShipmoney(Double shipmoney) { this.shipmoney = shipmoney; }

   public String getRemark() {
     return this.remark;
   }

   public void setRemark(String remark) { this.remark = remark; }

   public String getShip_day() {
     return this.ship_day;
   }

   public void setShip_day(String ship_day) { this.ship_day = ship_day; }

   public String getShip_name() {
     return this.ship_name;
   }

   public void setShip_name(String ship_name) { this.ship_name = ship_name; }

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

   public String getStoreids() {
     return this.storeids;
   }

   public void setStoreids(String storeids) { this.storeids = storeids; }

   public String getShippingids() {
     return this.shippingids;
   }

   public void setShippingids(String shippingids) { this.shippingids = shippingids; }

   public Integer getRegionid() {
     return this.regionid;
   }

   public void setRegionid(Integer regionid) { this.regionid = regionid; }

   public String getAddr() {
     return this.addr;
   }

   public void setAddr(String addr) { this.addr = addr; }

   public String[] getShipNos() {
     return this.shipNos;
   }

   public void setShipNos(String[] shipNos) { this.shipNos = shipNos; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\order\StoreOrderApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */