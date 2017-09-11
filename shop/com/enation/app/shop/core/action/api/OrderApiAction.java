 package com.enation.app.shop.core.action.api;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.model.MemberAddress;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.service.IMemberAddressManager;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.eop.processor.core.RemoteRequest;
 import com.enation.eop.processor.core.Request;
 import com.enation.eop.processor.core.Response;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.JsonMessageUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import net.sf.json.JSONObject;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;




















 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("order")
 @Results({@org.apache.struts2.convention.annotation.Result(name="kuaidi", type="freemarker", location="/themes/default/member/order_kuaidi.html")})
 public class OrderApiAction
   extends WWAction
 {
   private IOrderFlowManager orderFlowManager;
   private IOrderManager orderManager;
   private IMemberAddressManager memberAddressManager;
   private Map kuaidiResult;

   public String create()
   {
     try
     {
       Order order = createOrder();

       this.json = JsonMessageUtil.getObjectJson(order, "order");
     }
     catch (RuntimeException e) {
       e.printStackTrace();
       this.logger.error("创建订单出错", e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }









   public String cancel()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     try {
       String sn = request.getParameter("sn");
       String reason = request.getParameter("reason");
       Member member = UserServiceFactory.getUserService().getCurrentMember();
       if (member == null) {
         showErrorJson("取消订单失败：登录超时");
       } else {
         this.orderFlowManager.cancel(sn, reason);
         showSuccessJson("取消订单成功");
       }
     } catch (RuntimeException re) {
       showErrorJson(re.getMessage());
     }
     return "json_message";
   }








   public String rogConfirm()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     try {
       String orderId = request.getParameter("orderId");
       Member member = UserServiceFactory.getUserService().getCurrentMember();
       if (member == null) {
         showErrorJson("取消订单失败：登录超时");
       } else {
         this.orderFlowManager.rogConfirm(Integer.parseInt(orderId), member.getMember_id(), member.getUname(), member.getUname(), Long.valueOf(DateUtil.getDatelineLong()));
         showSuccessJson("确认收货成功");
       }
     } catch (Exception e) {
       showErrorJson("数据库错误");
     }
     return "json_message";
   }


   private Order createOrder()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();


     Integer shippingId = StringUtil.toInt(request.getParameter("typeId"), null);
     if (shippingId == null) { throw new RuntimeException("配送方式不能为空");
     }
     Integer paymentId = StringUtil.toInt(request.getParameter("paymentId"), Integer.valueOf(0));
     if (paymentId.intValue() == 0) { throw new RuntimeException("支付方式不能为空");
     }
     Order order = new Order();
     order.setShipping_id(shippingId);
     order.setPayment_id(paymentId);
     Integer addressId = Integer.valueOf(StringUtil.toInt(request.getParameter("addressId"), false));
     MemberAddress address = new MemberAddress();

     address = createAddress();

     order.setShip_provinceid(address.getProvince_id());
     order.setShip_cityid(address.getCity_id());
     order.setShip_regionid(address.getRegion_id());

     order.setShip_addr(address.getAddr());
     order.setShip_mobile(address.getMobile());
     order.setShip_tel(address.getTel());
     order.setShip_zip(address.getZip());
     order.setShipping_area(address.getProvince() + "-" + address.getCity() + "-" + address.getRegion());
     order.setShip_name(address.getName());
     order.setRegionid(address.getRegion_id());



     if (("yes".equals(request.getParameter("saveAddress"))) &&
       (UserServiceFactory.getUserService().getCurrentMember() != null)) {
       address.setAddr_id(null);
       addressId = Integer.valueOf(this.memberAddressManager.addAddress(address));
     }



     address.setAddr_id(addressId);
     order.setMemberAddress(address);
     order.setShip_day(request.getParameter("shipDay"));
     order.setShip_time(request.getParameter("shipTime"));
     order.setRemark(request.getParameter("remark"));
     order.setAddress_id(address.getAddr_id());
     return this.orderManager.add(order, request.getSession().getId());
   }

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

   public String orderKuaidi() {
     try {
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       String logino = request.getParameter("logino");
       String code = request.getParameter("code");
       if ((logino == null) || (logino.length() < 5)) {
         Map result = new HashMap();
         result.put("status", "-1");
         showErrorJson("请输入正确的运单号");
         return "";
       }
       if ((code == null) || (code.equals(""))) {
         code = "yuantong";
       }
       Request remoteRequest = new RemoteRequest();
       String kuaidiurl = "http://api.kuaidi100.com/api?id=92a25441fc46fded&com=" + code + "&nu=" + logino + "&show=0&muti=1&order=asc";
       System.out.println(kuaidiurl);
       Response remoteResponse = remoteRequest.execute(kuaidiurl);
       String content = remoteResponse.getContent();
       this.kuaidiResult = ((Map)JSONObject.toBean(JSONObject.fromObject(content), Map.class));
     }
     catch (Exception e) {
       this.logger.error("查询货运状态", e);
     }
     return "kuaidi";
   }

   public IOrderManager getOrderManager()
   {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager)
   {
     this.orderManager = orderManager;
   }

   public IMemberAddressManager getMemberAddressManager()
   {
     return this.memberAddressManager;
   }

   public void setMemberAddressManager(IMemberAddressManager memberAddressManager)
   {
     this.memberAddressManager = memberAddressManager;
   }

   public IOrderFlowManager getOrderFlowManager() {
     return this.orderFlowManager;
   }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
     this.orderFlowManager = orderFlowManager;
   }

   public Map getKuaidiResult() {
     return this.kuaidiResult;
   }

   public void setKuaidiResult(Map kuaidiResult) {
     this.kuaidiResult = kuaidiResult;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\OrderApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */