 package com.enation.app.shop.core.action.api;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.component.orderreturns.service.IReturnsOrderManager;
 import com.enation.app.shop.core.model.Goods;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.OrderLog;
 import com.enation.app.shop.core.model.ReturnsOrder;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IMemberPointManger;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.StringUtil;
 import java.io.File;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;


















 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("returnorder")
 public class MemberReturnAction
   extends WWAction
 {
   private IReturnsOrderManager returnsOrderManager;
   private IMemberPointManger memberPointManger;
   private IOrderManager orderManager;
   private IGoodsManager goodsManager;
   private String type;
   private String ordersn;
   private String applyreason;
   private String goodsns;
   private Integer member_id;
   private File pic;
   private String picFileName;
   private File file;
   private String fileFileName;
   private int rerurntype;
   private int orderid;

   public String returnAdd()
   {
     ReturnsOrder returnOrder = new ReturnsOrder();

     String subFolder = "order";
     if (this.file != null)
     {
       String allowTYpe = "gif,jpg,bmp,png";
       if ((!this.fileFileName.trim().equals("")) && (this.fileFileName.length() > 0)) {
         String ex = this.fileFileName.substring(this.fileFileName.lastIndexOf(".") + 1, this.fileFileName.length());
         if (allowTYpe.toString().indexOf(ex.toLowerCase()) < 0) {
           showErrorJson("对不起,只能上传gif,jpg,bmp,png格式的图片！");
           return "json_message";
         }
       }

       if (this.file.length() > 204800L) {
         showErrorJson("'对不起,图片不能大于200K！");
         return "json_message";
       }
       String imgPath = UploadUtil.upload(this.file, this.fileFileName, subFolder);
       returnOrder.setPhoto(imgPath);
     }
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     int type = this.rerurntype;

     Order order = this.orderManager.get(this.ordersn);
     Member member = UserServiceFactory.getUserService().getCurrentMember();

     if (this.orderManager.get(this.ordersn) == null) {
       showErrorJson("此订单不存在！");
       return "json_message";
     }
     if ((order.getStatus().intValue() != 5) && (order.getStatus().intValue() != 6)) {
       showErrorJson("您的订单还没有发货！");
       return "json_message";
     }

     Integer member_id = order.getMember_id();
     if (!member_id.equals(member.getMember_id())) {
       showErrorJson("此订单号不是您的订单号！");
       return "json_message";
     }
     Integer memberid = member_id;


     ReturnsOrder tempReturnsOrder = this.returnsOrderManager.getByOrderSn(this.ordersn);
     if (tempReturnsOrder != null) {
       showErrorJson("此订单已经申请过退货或换货，不能再申请！");
       return "json_message";
     }

     String goods = this.goodsns;

     String[] goodsns;
     if ((goods != null) && (!goods.equals(""))) {
       goodsns = StringUtils.split(goods, ",");
     } else {
       showErrorJson("您填写的货号为空！");
       return "json_message"; }
//     String[] goodsns;
     List<Map> items = this.orderManager.getItemsByOrderid(order.getOrder_id());
     if (items == null) {
       showErrorJson("您的订单下没有货物！");
       return "json_message";
     }
     List<String> goodSnUnderOrder = new ArrayList();
     for (int i = 0; i < items.size(); i++) {
       goodSnUnderOrder.add(this.goodsManager.getGoods((Integer)((Map)items.get(i)).get("goods_id")).getSn());
     }
     for (int j = 0; j < goodsns.length; j++) {
       if (goodsns[j].indexOf("-") != -1) {
         goodsns[j] = goodsns[j].substring(0, goodsns[j].indexOf("-"));
       }
     }
     for (int j = 0; j < goodsns.length; j++) {
       if (!goodSnUnderOrder.contains(goodsns[j])) {
         showErrorJson("您所填写的所有货物号必须属于一个订单中！");
         return "json_message";
       }
     }


     int[] goodids = new int[goodsns.length];
     if (goodsns != null) {
       for (int i = 0; i < goodsns.length; i++) {
         goodids[i] = this.goodsManager.getGoodBySn(goodsns[i]).getGoods_id().intValue();
       }
     }

     returnOrder.setGoodsns(goods);
     returnOrder.setMemberid(member_id);
     returnOrder.setOrdersn(this.ordersn);
     returnOrder.setApply_reason(this.applyreason);
     returnOrder.setType(Integer.valueOf(type));
     int orderid = this.orderManager.get(this.ordersn).getOrder_id().intValue();

     OrderLog log = new OrderLog();
     if (type == 1) {
       log.setMessage("用户" + member.getUname() + "申请退货");
       log.setOp_name(member.getUname());
       log.setOp_id(member.getMember_id());
       log.setOrder_id(order.getOrder_id());
       this.returnsOrderManager.add(returnOrder, orderid, 1, goodids);
       this.returnsOrderManager.addLog(log);
       showSuccessJson("退货，我们会在2个工作日内处理您的请求！");
     }
     if (type == 2) {
       log.setMessage("用户" + member.getUname() + "申请退货");
       log.setOp_name(member.getUname());
       log.setOp_id(member.getMember_id());
       log.setOrder_id(order.getOrder_id());
       this.returnsOrderManager.add(returnOrder, orderid, 2, goodids);
       this.returnsOrderManager.addLog(log);
       showSuccessJson("换货申请已提交，我们会在2个工作日内处理您的请求！");
     }

     return "json_message";
   }

   @Deprecated
   public String thaw()
   {
     try
     {
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       int orderid = StringUtil.toInt(request.getParameter("orderid"), true);
       this.memberPointManger.thaw(Integer.valueOf(orderid));
       showSuccessJson("成功解冻");
     } catch (RuntimeException e) {
       this.logger.error("手动解冻积分" + e.getMessage(), e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }



   public Integer getMember_id()
   {
     return this.member_id;
   }

   public void setMember_id(Integer member_id) {
     this.member_id = member_id;
   }

   public String getOrdersn() {
     return this.ordersn;
   }

   public void setOrdersn(String ordersn) {
     this.ordersn = ordersn;
   }

   public String getApplyreason() {
     return this.applyreason;
   }

   public void setApplyreason(String applyreason) {
     this.applyreason = applyreason;
   }

   public String getGoodsns() {
     return this.goodsns;
   }

   public void setGoodsns(String goodsns) {
     this.goodsns = goodsns;
   }

   public IReturnsOrderManager getReturnsOrderManager() {
     return this.returnsOrderManager;
   }

   public void setReturnsOrderManager(IReturnsOrderManager returnsOrderManager) {
     this.returnsOrderManager = returnsOrderManager;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IGoodsManager getGoodsManager() {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }

   public File getPic()
   {
     return this.pic;
   }

   public void setPic(File pic) {
     this.pic = pic;
   }

   public String getPicFileName() {
     return this.picFileName;
   }

   public void setPicFileName(String picFileName) {
     this.picFileName = picFileName;
   }

   public File getFile() {
     return this.file;
   }

   public void setFile(File file) {
     this.file = file;
   }

   public String getFileFileName() {
     return this.fileFileName;
   }

   public void setFileFileName(String fileFileName) {
     this.fileFileName = fileFileName;
   }

   public String getType() {
     return this.type;
   }

   public void setType(String type) {
     this.type = type;
   }

   public int getRerurntype() {
     return this.rerurntype;
   }

   public void setRerurntype(int rerurntype) {
     this.rerurntype = rerurntype;
   }


   public IMemberPointManger getMemberPointManger()
   {
     return this.memberPointManger;
   }


   public void setMemberPointManger(IMemberPointManger memberPointManger)
   {
     this.memberPointManger = memberPointManger;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\MemberReturnAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */