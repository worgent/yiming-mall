 package com.enation.app.shop.core.action.backend;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.shop.core.model.Logi;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.OrderItem;
 import com.enation.app.shop.core.model.OrderMeta;
 import com.enation.app.shop.core.model.SellBackGoodsList;
 import com.enation.app.shop.core.model.SellBackList;
 import com.enation.app.shop.core.model.SellBackLog;
 import com.enation.app.shop.core.model.SellBackStatus;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.app.shop.core.service.ILogiManager;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IOrderMetaManager;
 import com.enation.app.shop.core.service.ISellBackManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.DateUtil;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
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
 @Action("sellBack")
 @Results({@org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/shop/admin/orderReport/add_sellback.html"), @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/shop/admin/orderReport/edit_sellback.html"), @org.apache.struts2.convention.annotation.Result(name="payment", type="freemarker", location="/shop/admin/orderReport/payment_sellback.html"), @org.apache.struts2.convention.annotation.Result(name="returned", type="freemarker", location="/shop/admin/orderReport/returned_sellback.html")})
 public class SellBackAction
   extends WWAction
 {
   private IOrderMetaManager orderMetaManager;
   private IMemberManager memberManager;
   private IAdminUserManager adminUserManager;
   private ISellBackManager sellBackManager;
   private IOrderManager orderManager;
   private ILogiManager logiManager;
   private Integer orderId;
   private Order orderinfo;
   private List<OrderItem> orderItem;
   private List<OrderMeta> metaList;
   private String tradesn;
   private List logiList;
   private SellBackList sellBackList;
   private SellBackGoodsList sellBackGoodsList;
   private Integer[] goodsId;
   private String[] goodsName;
   private Integer[] goodsNum;
   private String[] goodsRemark;
   private SellBackLog sellBackLog;
   private Double[] goodsPrice;
   private Integer[] payNum;
   private Integer[] returnNum;
   private Integer[] oldStorageNum;
   private Integer[] storageNum;
   private Integer status;
   private Integer id;
   private List goodsList;
   private List logList;
   private String cancelRemark;
   private String keyword;
   private Integer[] gid;
   private Integer is_all;
   private List depotlist;
   private IDepotManager depotManager;
   private Integer depotid;
   private Integer ctype;
   private Integer[] productId;

   public String list()
   {
     this.webpage = this.sellBackManager.list(getPage(), getPageSize());
     return "list";
   }

   public String searchSn()
   {
     this.orderinfo = this.orderManager.get(this.orderId);
     int num = this.sellBackManager.searchSn(this.orderinfo.getSn());

     if (num > 0) {
       showErrorJson("订单已提交过退货申请");
     }
     return "json_message";
   }



   public String search()
   {
     this.webpage = this.sellBackManager.search(this.keyword, getPage(), getPageSize());
     return "list";
   }


   public String add()
   {
     this.orderinfo = this.orderManager.get(this.orderId);
     this.orderItem = this.orderManager.listGoodsItems(this.orderId);
     this.tradesn = createSn();
     this.logiList = this.logiManager.list();

     this.depotlist = this.depotManager.list();
     return "add";
   }




   public String edit()
   {
     this.sellBackList = this.sellBackManager.get(this.id);
     this.orderinfo = this.orderManager.get(this.sellBackList.getOrdersn());
     this.logiList = this.logiManager.list();
     this.goodsList = this.sellBackManager.getGoodsList(this.id, this.sellBackList.getOrdersn());
     this.metaList = this.orderMetaManager.list(this.orderinfo.getOrder_id().intValue());
     return "edit";
   }




   public String returned()
   {
     this.sellBackList = this.sellBackManager.get(this.id);
     this.orderinfo = this.orderManager.get(this.sellBackList.getOrdersn());
     this.goodsList = this.sellBackManager.getGoodsList(this.id, this.sellBackList.getOrdersn());
     this.logList = this.sellBackManager.sellBackLogList(this.id);

     this.is_all = this.sellBackManager.isAll(this.id.intValue());
     return "returned";
   }




   public String payment()
   {
     this.sellBackList = this.sellBackManager.get(this.id);
     this.orderinfo = this.orderManager.get(this.sellBackList.getOrdersn());
     this.goodsList = this.sellBackManager.getGoodsList(this.id);
     this.logList = this.sellBackManager.sellBackLogList(this.id);
     this.metaList = this.orderMetaManager.list(this.orderinfo.getOrder_id().intValue());
     return "payment";
   }




   public String save()
   {
     String goodslist = "";

     if (this.goodsId != null) {
       for (int i = 0; i < this.goodsId.length; i++) {
         for (int j = 0; j < this.gid.length; j++) {
           if (this.goodsId[i].intValue() == this.gid[j].intValue())
             goodslist = goodslist + this.goodsName[j] + "(" + this.goodsNum[j] + ") ";
         }
       }
     }
     try {
       Logi logi = this.logiManager.getLogiById(this.sellBackList.getLogi_id());
       this.sellBackList.setGoodslist(goodslist);
       SellBackList sellback = this.sellBackManager.get(this.sellBackList.getTradeno());
       if (sellback == null) {
         Order order = this.orderManager.get(this.orderId);
         if ((order.getShipping_area() != null) || (order.getShipping_area() != "") || (order.getShipping_area().trim() != "暂空")) {
           this.sellBackList.setAdr(order.getShip_addr());
         } else {
           String[] adr = order.getShipping_area().split("-");
           this.sellBackList.setAdr(adr[0] + adr[1] + adr[2] + order.getShip_addr());
         }
         this.sellBackList.setRegtime(Long.valueOf(DateUtil.getDatelineLong()));
         this.sellBackList.setSeller(this.adminUserManager.getCurrentUser().getUsername());
         this.sellBackList.setRegoperator(this.adminUserManager.getCurrentUser().getUsername());
         this.sellBackList.setTel(order.getShip_tel());
         this.sellBackList.setZip(order.getShip_zip());
       } else {
         this.sellBackList.setId(sellback.getId());
         this.sellBackList.setRegtime(sellback.getRegtime());
       }
       this.sellBackList.setTradestatus(this.status);
       this.sellBackList.setLogi_name(logi.getName());
       this.sellBackList.setDepotid(this.depotid);
       Integer sid = this.sellBackManager.save(this.sellBackList);

       if (sellback == null) {
         SellBackList sellbacklist = this.sellBackManager.get(this.sellBackList.getTradeno());
         this.sellBackManager.saveLog(sellbacklist.getId(), SellBackStatus.valueOf(sellbacklist.getTradestatus().intValue()), "");
       }
       if (this.goodsId != null) {
         Integer recid = this.sellBackManager.getRecid(this.sellBackList.getTradeno());
         for (int i = 0; i < this.goodsId.length; i++) {
           for (int j = 0; j < this.gid.length; j++) {
             if (this.goodsId[i].intValue() == this.gid[j].intValue()) {
               SellBackGoodsList sellBackGoods = this.sellBackManager.getSellBackGoods(recid, this.goodsId[i]);
               if (sellBackGoods != null) {
                 editGoodsList(this.goodsNum[j], recid, this.goodsId[i], this.goodsRemark[j], null);
               } else {
                 saveGoodsList(this.goodsId[i], this.goodsNum[j], this.goodsPrice[j], this.payNum[j], recid, this.goodsRemark[j], null, this.productId[i]);
               }
             }
           }
         }
       }
       showSuccessJson("操作成功！", sid);
     } catch (Exception e) {
       showErrorJson("操作失败!");
     }
     return "json_message";
   }




   public String update()
   {
     SellBackList sellback = this.sellBackManager.get(this.id);
     String goodslist = "";
     this.status = Integer.valueOf(2);
     if (this.goodsId != null) {
       for (int i = 0; i < this.goodsId.length; i++) {
         for (int j = 0; j < this.gid.length; j++)
         {
           int rnum = this.returnNum[i].intValue();
           int osnum = this.oldStorageNum[i].intValue();

           if (this.goodsId[i].intValue() == this.gid[j].intValue())
           {
             int snum = this.storageNum[j].intValue();

             if (snum + osnum > rnum) {
               showErrorJson("入库数量不能大于退货数量");
               return "json_message";
             }

             if (snum + osnum < rnum) {
               this.status = Integer.valueOf(5);
             }

             goodslist = goodslist + this.goodsName[j] + "(" + this.storageNum[j] + ") ";
             SellBackGoodsList sellBackGoods = this.sellBackManager.getSellBackGoods(getId(), this.goodsId[i]);
             if (sellBackGoods != null) {
               editGoodsList(null, this.id, this.goodsId[i], sellBackGoods.getGoods_remark(), Integer.valueOf(snum + osnum));
             }
           }
         }
       }
     }

     try
     {
       sellback.setGoodslist(goodslist);
       sellback.setWarehouse_remark(this.sellBackList.getWarehouse_remark());
       sellback.setIsall(this.sellBackList.getIsall());
       sellback.setTradestatus(this.status);
       this.sellBackManager.save(sellback);
       if ((this.status.intValue() == 2) || (this.status.intValue() == 5)) {
         this.sellBackManager.saveLog(sellback.getId(), SellBackStatus.valueOf(sellback.getTradestatus().intValue()), "");
       }
       showSuccessJson("操作成功！");
     } catch (Exception e) {
       showErrorJson("操作失败 ！");
     }
     return "json_message";
   }



   public String savePayment()
   {
     try
     {
       this.sellBackManager.closePayable(this.id.intValue(), this.sellBackList.getFinance_remark(), "");
       showSuccessJson("操作成功！");
     } catch (Exception e) {
       e.printStackTrace();
       showErrorJson("操作失败！");
     }
     return "json_message";
   }






   public Integer saveGoodsList(Integer goodsid, Integer goodsnum, Double price, Integer paynum, Integer id, String remark, Integer storageNum, Integer productid)
   {
     SellBackGoodsList sellBackGoods = new SellBackGoodsList();
     if (storageNum != null) {
       sellBackGoods.setStorage_num(storageNum);
       sellBackGoods.setReturn_num(storageNum);
     }
     if (goodsnum == null) {
       sellBackGoods.setReturn_num(Integer.valueOf(0));
     } else {
       sellBackGoods.setReturn_num(goodsnum);
     }
     sellBackGoods.setGoods_id(goodsid);
     sellBackGoods.setPrice(price);
     sellBackGoods.setRecid(id);
     sellBackGoods.setShip_num(paynum);
     sellBackGoods.setGoods_remark(remark);
     sellBackGoods.setProduct_id(productid);

     Integer sid = this.sellBackManager.saveGoodsList(sellBackGoods);
     return sid;
   }




   public void editGoodsList(Integer goodsNum, Integer recid, Integer goodsid, String remark, Integer storageNum)
   {
     if (goodsNum != null) {
       if (goodsNum.intValue() > 0) {
         Map map = new HashMap();
         map.put("recid", recid);
         map.put("goods_id", goodsid);
         map.put("return_num", goodsNum);
         map.put("goods_remark", remark);
         this.sellBackManager.editGoodsNum(map);
       } else {
         this.sellBackManager.delGoods(recid, goodsid);
       }
     }
     if (storageNum != null) {
       this.sellBackManager.editStorageNum(recid, goodsid, storageNum);
     }
   }


   public String cancel()
   {
     try
     {
       SellBackList sellbacklist = null;
       this.status = this.ctype;
       if (this.id != null) {
         if ((this.status.intValue() == 0) || (this.status.intValue() == 1)) {
           sellbacklist = this.sellBackManager.get(this.id);
         } else {
           showErrorJson("该退货单的商品已入库，不能取消退货！");
         }
       }
       else if (this.sellBackList.getTradeno() != null) {
         if ((this.status.intValue() == 0) || (this.status.intValue() == 1)) {
           sellbacklist = this.sellBackManager.get(this.sellBackList.getTradeno());
         } else {
           showErrorJson("该退货单的商品已入库，不能取消退货！");
         }
       }

       if (sellbacklist != null) {
         sellbacklist.setTradestatus(Integer.valueOf(4));
         this.sellBackManager.save(sellbacklist);
         this.sellBackManager.saveLog(sellbacklist.getId(), SellBackStatus.valueOf(sellbacklist.getTradestatus().intValue()), "取消退货，原因：" + this.cancelRemark);
         showSuccessJson("取消退货成功！");
       } else {
         showSuccessJson("操作成功！");
       }
     } catch (Exception e) {
       showErrorJson("取消退货失败！");
     }
     return "json_message";
   }


   public String createSn()
   {
     Date now = new Date();
     String sn = DateUtil.toString(now, "yyMMddhhmmss");

     return sn;
   }


   public ISellBackManager getSellBackManager()
   {
     return this.sellBackManager;
   }

   public void setSellBackManager(ISellBackManager sellBackManager) { this.sellBackManager = sellBackManager; }

   public IOrderManager getOrderManager()
   {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public ILogiManager getLogiManager() {
     return this.logiManager;
   }

   public void setLogiManager(ILogiManager logiManager) {
     this.logiManager = logiManager;
   }

   public Integer getOrderId() {
     return this.orderId;
   }

   public void setOrderId(Integer orderId) {
     this.orderId = orderId;
   }


   public Order getOrderinfo()
   {
     return this.orderinfo;
   }

   public void setOrderinfo(Order orderinfo) {
     this.orderinfo = orderinfo;
   }

   public List<OrderItem> getOrderItem() {
     return this.orderItem;
   }

   public void setOrderItem(List<OrderItem> orderItem) {
     this.orderItem = orderItem;
   }

   public String getTradesn() {
     return this.tradesn;
   }

   public void setTradesn(String tradesn) {
     this.tradesn = tradesn;
   }

   public List getLogiList() {
     return this.logiList;
   }

   public void setLogiList(List logiList) {
     this.logiList = logiList;
   }

   public SellBackList getSellBackList() {
     return this.sellBackList;
   }

   public void setSellBackList(SellBackList sellBackList) {
     this.sellBackList = sellBackList;
   }

   public SellBackGoodsList getSellBackGoodsList() {
     return this.sellBackGoodsList;
   }

   public void setSellBackGoodsList(SellBackGoodsList sellBackGoodsList) {
     this.sellBackGoodsList = sellBackGoodsList;
   }

   public Integer[] getGoodsId() {
     return this.goodsId;
   }

   public void setGoodsId(Integer[] goodsId) {
     this.goodsId = goodsId;
   }

   public String[] getGoodsName() {
     return this.goodsName;
   }

   public void setGoodsName(String[] goodsName) {
     this.goodsName = goodsName;
   }

   public Integer[] getGoodsNum() {
     return this.goodsNum;
   }

   public void setGoodsNum(Integer[] goodsNum) {
     this.goodsNum = goodsNum;
   }

   public String[] getGoodsRemark() {
     return this.goodsRemark;
   }

   public void setGoodsRemark(String[] goodsRemark) {
     this.goodsRemark = goodsRemark;
   }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public SellBackLog getSellBackLog() {
     return this.sellBackLog;
   }

   public void setSellBackLog(SellBackLog sellBackLog) {
     this.sellBackLog = sellBackLog;
   }

   public Double[] getGoodsPrice() {
     return this.goodsPrice;
   }

   public void setGoodsPrice(Double[] goodsPrice) {
     this.goodsPrice = goodsPrice;
   }

   public Integer[] getPayNum() {
     return this.payNum;
   }

   public void setPayNum(Integer[] payNum) {
     this.payNum = payNum;
   }

   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public IMemberManager getMemberManager() {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }

   public List getGoodsList() {
     return this.goodsList;
   }

   public void setGoodsList(List goodsList) {
     this.goodsList = goodsList;
   }

   public Integer[] getStorageNum() {
     return this.storageNum;
   }

   public void setStorageNum(Integer[] storageNum) {
     this.storageNum = storageNum;
   }

   public List getLogList() {
     return this.logList;
   }

   public void setLogList(List logList) {
     this.logList = logList;
   }

   public IOrderMetaManager getOrderMetaManager() {
     return this.orderMetaManager;
   }

   public void setOrderMetaManager(IOrderMetaManager orderMetaManager) {
     this.orderMetaManager = orderMetaManager;
   }

   public List<OrderMeta> getMetaList() {
     return this.metaList;
   }

   public void setMetaList(List<OrderMeta> metaList) {
     this.metaList = metaList;
   }

   public String getCancelRemark()
   {
     return this.cancelRemark;
   }

   public void setCancelRemark(String cancelRemark) {
     this.cancelRemark = cancelRemark;
   }

   public String getKeyword() {
     return this.keyword;
   }

   public void setKeyword(String keyword) {
     this.keyword = keyword;
   }

   public Integer[] getGid() {
     return this.gid;
   }

   public void setGid(Integer[] gid) {
     this.gid = gid;
   }

   public Integer getIs_all() {
     return this.is_all;
   }

   public void setIs_all(Integer is_all) {
     this.is_all = is_all;
   }

   public Integer[] getReturnNum() {
     return this.returnNum;
   }

   public void setReturnNum(Integer[] returnNum) {
     this.returnNum = returnNum;
   }

   public Integer[] getOldStorageNum() {
     return this.oldStorageNum;
   }

   public void setOldStorageNum(Integer[] oldStorageNum) {
     this.oldStorageNum = oldStorageNum;
   }

   public List getDepotlist() {
     return this.depotlist;
   }

   public void setDepotlist(List depotlist) {
     this.depotlist = depotlist;
   }

   public IDepotManager getDepotManager() {
     return this.depotManager;
   }

   public void setDepotManager(IDepotManager depotManager) {
     this.depotManager = depotManager;
   }

   public Integer getDepotid() {
     return this.depotid;
   }

   public void setDepotid(Integer depotid) {
     this.depotid = depotid;
   }

   public Integer getStatus() {
     return this.status;
   }

   public void setStatus(Integer status) {
     this.status = status;
   }

   public Integer getCtype() {
     return this.ctype;
   }

   public void setCtype(Integer ctype) {
     this.ctype = ctype;
   }

   public Integer[] getProductId() {
     return this.productId;
   }

   public void setProductId(Integer[] productId) {
     this.productId = productId;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\SellBackAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */