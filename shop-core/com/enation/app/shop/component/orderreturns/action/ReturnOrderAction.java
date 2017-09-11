 package com.enation.app.shop.component.orderreturns.action;

 import com.enation.app.base.core.service.IRegionsManager;
 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.shop.component.orderreturns.service.IReturnsOrderManager;
 import com.enation.app.shop.core.model.Goods;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.OrderLog;
 import com.enation.app.shop.core.model.ReturnsOrder;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.util.ArrayList;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;















 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="return_list", type="freemarker", location="/com/enation/app/shop/component/orderreturns/action/return_list.html"), @org.apache.struts2.convention.annotation.Result(name="return_detail", type="freemarker", location="/com/enation/app/shop/component/orderreturns/action/return_detail.html"), @org.apache.struts2.convention.annotation.Result(name="changeitem", type="freemarker", location="/com/enation/app/shop/component/orderreturns/action/item_change.html")})
 @Action("returnOrder")
 public class ReturnOrderAction
   extends WWAction
 {
   private IAdminUserManager adminUserManager;
   private IReturnsOrderManager returnsOrderManager;
   private IGoodsManager goodsManager;
   private Integer returnOrderId;
   private ReturnsOrder rorder;
   private List itemList;
   private IOrderManager orderManager;
   private List goodIdS;
   private String refuse_reason;
   private IRegionsManager regionsManager;
   private Order ord;
   private List provinceList;
   private Integer[] itemid_array;
   private String[] targetsn_array;
   private Integer state;
   private String ctx;

   public IAdminUserManager getAdminUserManager()
   {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public Integer getState() {
     return this.state;
   }

   public void setState(Integer state) {
     this.state = state;
   }

   public String[] getTargetsn_array() {
     return this.targetsn_array;
   }

   public void setTargetsn_array(String[] targetsn_array) {
     this.targetsn_array = targetsn_array;
   }

   public String getRefuse_reason() {
     return this.refuse_reason;
   }

   public Integer[] getItemid_array() {
     return this.itemid_array;
   }

   public void setItemid_array(Integer[] itemid_array) {
     this.itemid_array = itemid_array;
   }

   public void setRefuse_reason(String refuse_reason) {
     this.refuse_reason = refuse_reason;
   }

   public IRegionsManager getRegionsManager() {
     return this.regionsManager;
   }

   public void setRegionsManager(IRegionsManager regionsManager) {
     this.regionsManager = regionsManager;
   }

   public Order getOrd() {
     return this.ord;
   }

   public void setOrd(Order ord) {
     this.ord = ord;
   }

   public List getProvinceList() {
     return this.provinceList;
   }

   public void setProvinceList(List provinceList) {
     this.provinceList = provinceList;
   }

   public ReturnsOrder getRorder() {
     return this.rorder;
   }

   public void setRorder(ReturnsOrder rorder) {
     this.rorder = rorder;
   }

   public Integer getReturnOrderId() {
     return this.returnOrderId;
   }

   public void setReturnOrderId(Integer returnOrderId) {
     this.returnOrderId = returnOrderId;
   }

   public IReturnsOrderManager getReturnsOrderManager() {
     return this.returnsOrderManager;
   }

   public void setReturnsOrderManager(IReturnsOrderManager returnsOrderManager) {
     this.returnsOrderManager = returnsOrderManager;
   }

   public IGoodsManager getGoodsManager() {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }

   public List getItemList() {
     return this.itemList;
   }

   public void setItemList(List itemList) {
     this.itemList = itemList;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public List getGoodIdS() {
     return this.goodIdS;
   }

   public void setGoodIdS(List goodIdS) {
     this.goodIdS = goodIdS;
   }




   public String refuseReturn()
   {
     try
     {
       this.returnsOrderManager.refuse(this.returnOrderId, this.refuse_reason, 1);

       int orderid = this.returnsOrderManager.queryOrderidByReturnorderid(this.returnOrderId.intValue());

       this.returnsOrderManager.updateItemStatusByOrderidAndStatus(3, 1, orderid);


       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       OrderLog log = new OrderLog();
       log.setMessage("管理员" + adminUser.getUsername() + "拒绝退货");
       log.setOp_id(adminUser.getUserid());
       log.setOp_name(adminUser.getUsername());
       log.setOrder_id(this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId));

       this.returnsOrderManager.addLog(log);
       showSuccessJson("");
     } catch (RuntimeException e) {
       e.printStackTrace();
       this.logger.error(e.fillInStackTrace());
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }





   public String agreeReturn()
   {
     Integer orderid = this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId);
     try {
       this.returnsOrderManager.updateState(this.returnOrderId, 2);

       if ((this.itemid_array != null) && (this.itemid_array.length > 0)) {
         for (int i = 0; i < this.itemid_array.length; i++) {
           this.returnsOrderManager.updateOrderItemsState(this.itemid_array[i], 5);
         }

         this.returnsOrderManager.updateItemsState(orderid, 3, 1);
       }
       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       OrderLog log = new OrderLog();
       log.setMessage("管理员" + adminUser.getUsername() + "同意退货");
       log.setOp_id(adminUser.getUserid());
       log.setOp_name(adminUser.getUsername());
       log.setOrder_id(orderid);
       this.returnsOrderManager.addLog(log);
       showSuccessJson("");
     } catch (RuntimeException e) {
       e.printStackTrace();
       this.logger.error(e.fillInStackTrace());
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }




   public String confirmReturnReceive()
   {
     try
     {
       this.returnsOrderManager.updateState(this.returnOrderId, 3);

       int orderid = this.returnsOrderManager.queryOrderidByReturnorderid(this.returnOrderId.intValue());

       this.returnsOrderManager.updateItemStatusByOrderidAndStatus(7, 5, orderid);


       OrderLog log = new OrderLog();
       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       log.setMessage("管理员" + adminUser.getUsername() + "已收到退货,正在执行退款");
       log.setOp_id(adminUser.getUserid());
       log.setOp_name(adminUser.getUsername());
       log.setOrder_id(this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId));

       this.returnsOrderManager.addLog(log);
       showSuccessJson("");
     } catch (Exception e) {
       e.printStackTrace();
       this.logger.error(e.fillInStackTrace());
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }


   public String returned()
   {
     try
     {
       this.returnsOrderManager.updateState(this.returnOrderId, 4);

       int orderid = this.returnsOrderManager.queryOrderidByReturnorderid(this.returnOrderId.intValue());

       double prices = this.returnsOrderManager.queryItemPrice(Integer.valueOf(orderid), Integer.valueOf(7)).doubleValue();

       this.returnsOrderManager.updateItemStatusByOrderidAndStatus(9, 7, orderid);


       this.orderManager.updateOrderPrice(prices, orderid);
       OrderLog log = new OrderLog();
       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       log.setMessage("管理员" + adminUser.getUsername() + "完成退货");
       log.setOp_id(adminUser.getUserid());
       log.setOp_name(adminUser.getUsername());
       log.setOrder_id(this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId));

       this.returnsOrderManager.addLog(log);
       showSuccessJson("");
     } catch (Exception e) {
       e.printStackTrace();
       this.logger.error(e.fillInStackTrace());
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }


   public String refuseChange()
   {
     try
     {
       this.returnsOrderManager.refuse(this.returnOrderId, this.refuse_reason, 1);

       int orderid = this.returnsOrderManager.queryOrderidByReturnorderid(this.returnOrderId.intValue());

       this.returnsOrderManager.updateItemStatusByOrderidAndStatus(4, 2, orderid);


       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       OrderLog log = new OrderLog();
       log.setMessage("管理员" + adminUser.getUsername() + "拒绝换货");
       log.setOp_id(adminUser.getUserid());
       log.setOp_name(adminUser.getUsername());
       log.setOrder_id(this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId));

       this.returnsOrderManager.addLog(log);
       showSuccessJson("");
     } catch (RuntimeException e) {
       e.printStackTrace();
       this.logger.error(e.fillInStackTrace());
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }



   public String agreeChange()
   {
     Integer orderid = this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId);
     try {
       this.returnsOrderManager.updateState(this.returnOrderId, 2);

       if ((this.itemid_array != null) && (this.itemid_array.length > 0)) {
         for (int i = 0; i < this.itemid_array.length; i++) {
           this.returnsOrderManager.updateOrderItemsState(this.itemid_array[i], 6);
         }

         this.returnsOrderManager.updateItemsState(orderid, 4, 2);
       }
       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       OrderLog log = new OrderLog();
       log.setMessage("管理员" + adminUser.getUsername() + "同意换货");
       log.setOp_id(adminUser.getUserid());
       log.setOp_name(adminUser.getUsername());
       log.setOrder_id(orderid);
       this.returnsOrderManager.addLog(log);
       showSuccessJson("");
     } catch (RuntimeException e) {
       e.printStackTrace();
       this.logger.error(e.fillInStackTrace());
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }



   public String confirmChangeReceive()
   {
     int orderid = this.returnsOrderManager.queryOrderidByReturnorderid(this.returnOrderId.intValue());

     double prices = this.returnsOrderManager.queryItemPrice(Integer.valueOf(orderid), Integer.valueOf(6)).doubleValue();
     try
     {
       if ((this.targetsn_array != null) && (this.targetsn_array.length > 0)) {
         this.targetsn_array = dealwithhyphen(this.targetsn_array);
         for (int i = 0; i < this.targetsn_array.length; i++) {
           Goods g = this.goodsManager.getGoodBySn(this.targetsn_array[i]);
           if (g == null) {
             showErrorJson("目标货号必须都存在！");
             return "json_message";
           }
         }
         for (int i = 0; i < this.targetsn_array.length; i++) {
           this.targetsn_array = dealwithhyphen(this.targetsn_array);
           double temp_price = this.goodsManager.getGoodBySn(this.targetsn_array[i]).getPrice().doubleValue();


           this.returnsOrderManager.updatePriceByItemid(this.itemid_array[i].intValue(), temp_price);
         }
       }

       if ((this.targetsn_array != null) && (this.targetsn_array.length > 0)) {
         for (int i = 0; i < this.targetsn_array.length; i++) {
           this.targetsn_array = dealwithhyphen(this.targetsn_array);
           Goods g = this.goodsManager.getGoodBySn(this.targetsn_array[i]);
           prices -= g.getPrice().doubleValue();
           this.returnsOrderManager.updateItemChange(g.getName(), g.getGoods_id().intValue(), this.itemid_array[i].intValue());
         }
       }

       this.orderManager.updateOrderPrice(prices, orderid);
       this.returnsOrderManager.updateState(this.returnOrderId, 3);

       this.returnsOrderManager.updateItemStatusByOrderidAndStatus(8, 6, orderid);


       OrderLog log = new OrderLog();
       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       log.setMessage("管理员" + adminUser.getUsername() + "已收到换货,换货已发出");
       log.setOp_id(adminUser.getUserid());
       log.setOp_name(adminUser.getUsername());
       log.setOrder_id(this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId));

       this.returnsOrderManager.addLog(log);
       showSuccessJson("");
     } catch (Exception e) {
       e.printStackTrace();
       this.logger.error(e.fillInStackTrace());
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }


   public String changed()
   {
     try
     {
       this.returnsOrderManager.updateState(this.returnOrderId, 4);

       int orderid = this.returnsOrderManager.queryOrderidByReturnorderid(this.returnOrderId.intValue());

       this.returnsOrderManager.updateItemStatusByOrderidAndStatus(10, 8, orderid);


       OrderLog log = new OrderLog();
       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       log.setMessage("管理员" + adminUser.getUsername() + "完成换货");
       log.setOp_id(adminUser.getUserid());
       log.setOp_name(adminUser.getUsername());
       log.setOrder_id(this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId));

       this.returnsOrderManager.addLog(log);
       showSuccessJson("");
     } catch (RuntimeException e) {
       e.printStackTrace();
       this.logger.error(e.fillInStackTrace());
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }





   public String returnsList()
   {
     return "return_list";
   }

   public String returnsListJson() {
     this.webpage = this.returnsOrderManager.listAll(getPage(), getPageSize());

     showGridJson(this.webpage);
     return "json_message";
   }





   public String returnsApplyList()
   {
     this.webpage = this.returnsOrderManager.listAll(getPage(), getPageSize(), this.state.intValue());

     return "return_list";
   }





   public String returnDetail()
   {
     this.rorder = this.returnsOrderManager.get(this.returnOrderId);
     this.itemList = this.orderManager.listGoodsItems(this.orderManager.get(this.rorder.getOrdersn()).getOrder_id());

     String goodsSn = this.returnsOrderManager.getSnByOrderSn(this.rorder.getOrdersn());

     String[] goodSn = null;
     this.goodIdS = new ArrayList();
     if ((goodsSn != null) && (!goodsSn.equals(""))) {
       goodSn = StringUtils.split(goodsSn, ",");
       goodSn = dealwithhyphen(goodSn);
       for (int i = 0; i < goodSn.length; i++) {
         this.goodIdS.add(this.goodsManager.getGoodBySn(goodSn[i]).getGoods_id());
       }
     }
     this.ctx = ThreadContextHolder.getHttpRequest().getContextPath();
     if ((this.ctx != null) && (!this.ctx.endsWith("/"))) {
       this.ctx += "/";
     }
     return "return_detail";
   }

   public String returnD() {
     this.rorder = this.returnsOrderManager.get(this.returnOrderId);
     this.itemList = this.orderManager.listGoodsItems(this.orderManager.get(this.rorder.getOrdersn()).getOrder_id());

     return "changeitem";
   }

   public String[] dealwithhyphen(String[] targetsn_array) {
     for (int j = 0; j < targetsn_array.length; j++) {
       if (targetsn_array[j].indexOf("-") != -1) {
         targetsn_array[j] = targetsn_array[j].substring(0, targetsn_array[j].indexOf("-"));
       }
     }
     return targetsn_array;
   }

   public String getCtx() {
     return this.ctx;
   }

   public void setCtx(String ctx) {
     this.ctx = ctx;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\orderreturns\action\ReturnOrderAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */