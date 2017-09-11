 package com.enation.app.shop.component.bonus.action;

 import com.enation.app.shop.component.bonus.model.BonusType;
 import com.enation.app.shop.component.bonus.service.IBonusManager;
 import com.enation.app.shop.component.bonus.service.IBonusTypeManager;
 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.JsonMessageUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.InputStream;
 import java.io.UnsupportedEncodingException;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;

















 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="send_for_member", type="freemarker", location="/com/enation/app/shop/component/bonus/action/html/send_for_member.html"), @org.apache.struts2.convention.annotation.Result(name="send_for_goods", type="freemarker", location="/com/enation/app/shop/component/bonus/action/html/send_for_goods.html"), @org.apache.struts2.convention.annotation.Result(name="send_for_order", type="freemarker", location="/com/enation/app/shop/component/bonus/action/html/send_for_order.html"), @org.apache.struts2.convention.annotation.Result(name="send_for_offline", type="freemarker", location="/com/enation/app/shop/component/bonus/action/html/send_for_offline.html"), @org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/com/enation/app/shop/component/bonus/action/html/bonus_list.html"), @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/com/enation/app/shop/component/bonus/action/html/bonus_edit.html")})
 public class BonusAction
   extends WWAction
 {
   private IBonusManager bonusManager;
   private IBonusTypeManager bonusTypeManager;
   private IMemberLvManager memberLvManager;
   private int typeid;
   private int bonusid;
   private Integer[] memberids;
   private Integer[] goodsids;
   private int send_type;
   private List lvList;
   private String excelPath;
   private String filename;

   public String send()
   {
     BonusType bonusType = this.bonusTypeManager.get(this.typeid);
     this.send_type = bonusType.getSend_type();
     String result = "";
     switch (this.send_type) {
     case 0:
       this.lvList = this.memberLvManager.list();
       result = "send_for_member";
       break;
     case 1:
       result = "send_for_goods";
       break;
     case 2:
       result = "send_for_order";
       break;
     case 3:
       result = "send_for_offline";
       break;
     default:
       result = "send_for_member";
     }

     return result;
   }




   public String sendForMemberLv()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     int lvid = StringUtil.toInt(request.getParameter("lvid"), Integer.valueOf(0)).intValue();

     if (lvid == 0) {
       showErrorJson("必须选择会员级别");
       return "json_message";
     }


     int onlyEmailChecked = StringUtil.toInt(request.getParameter("onlyEmailChecked"), Integer.valueOf(0)).intValue();
     try
     {
       int count = this.bonusManager.sendForMemberLv(this.typeid, lvid, onlyEmailChecked);
       this.json = JsonMessageUtil.getNumberJson("count", Integer.valueOf(count));
     } catch (Exception e) {
       this.logger.error("发放红包出错", e);
       showErrorJson("发放红包出错[" + e.getMessage() + "]");
     }

     return "json_message";
   }




   public String sendForMember()
   {
     try
     {
       int count = this.bonusManager.sendForMember(this.typeid, this.memberids);
       this.json = JsonMessageUtil.getNumberJson("count", Integer.valueOf(count));
     } catch (Exception e) {
       this.logger.error("发放红包出错", e);
       showErrorJson("发放红包出错[" + e.getMessage() + "]");
     }
     return "json_message";
   }



   public String sendForGoods()
   {
     try
     {
       int count = this.bonusManager.sendForGoods(this.typeid, this.goodsids);
       this.json = JsonMessageUtil.getNumberJson("count", Integer.valueOf(count));
     } catch (Exception e) {
       this.logger.error("发放红包出错", e);
       showErrorJson("发放红包出错[" + e.getMessage() + "]");
     }
     return "json_message";
   }



   public String sendForOffLine()
   {
     try
     {
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       int createnum = StringUtil.toInt(request.getParameter("createnum"), Integer.valueOf(0)).intValue();
       int count = this.bonusManager.sendForOffLine(this.typeid, createnum);
       this.json = JsonMessageUtil.getNumberJson("count", Integer.valueOf(count));
     } catch (Exception e) {
       this.logger.error("发放红包出错", e);
       showErrorJson("发放红包出错[" + e.getMessage() + "]");
     }
     return "json_message";
   }





   public String list()
   {
     return "list";
   }

   public String listJson() {
     this.webpage = this.bonusManager.list(getPage(), getPageSize(), this.typeid);
     showGridJson(this.webpage);
     return "json_message";
   }




   public String delete()
   {
     try
     {
       this.bonusManager.delete(this.bonusid);
       showSuccessJson("删除成功");
     } catch (Exception e) {
       showErrorJson("删除失败【" + e.getMessage() + "】");
     }
     return "json_message";
   }






   public String reSendMail()
   {
     return "json_message";
   }

   public String getGoodsList() {
     try {
       List<Map> goodsList = this.bonusManager.getGoodsList(this.typeid);
       this.json = JsonMessageUtil.getListJson(goodsList);
     }
     catch (Exception e) {
       this.logger.error("获取已绑定商品出错", e);
       showErrorJson("获取已绑定商品出错");
     }


     return "json_message";
   }



   public String exportExcel()
   {
     BonusType bonusType = this.bonusTypeManager.get(this.typeid);
     this.filename = (bonusType.getType_name() + "红包列表.xls");
     this.excelPath = this.bonusManager.exportToExcel(this.typeid);

     return "download";
   }



   public InputStream getInputStream()
   {
     if (StringUtil.isEmpty(this.excelPath)) { return null;
     }
     InputStream in = null;
     try {
       in = new FileInputStream(this.excelPath);
     } catch (FileNotFoundException e) {
       e.printStackTrace();
     }
     return in;
   }

   public String getFileName()
   {
     String downFileName = this.filename;

     try
     {
       downFileName = new String(downFileName.getBytes(), "ISO8859-1");
     }
     catch (UnsupportedEncodingException e)
     {
       e.printStackTrace();
     }


     return downFileName;
   }


   public String execute()
   {
     return "success";
   }

   public int getTypeid()
   {
     return this.typeid;
   }

   public void setTypeid(int typeid) {
     this.typeid = typeid;
   }

   public Integer[] getMemberids() {
     return this.memberids;
   }

   public void setMemberids(Integer[] memberids) {
     this.memberids = memberids;
   }

   public Integer[] getGoodsids()
   {
     return this.goodsids;
   }

   public void setGoodsids(Integer[] goodsids) {
     this.goodsids = goodsids;
   }

   public int getSend_type() {
     return this.send_type;
   }

   public void setSend_type(int send_type) {
     this.send_type = send_type;
   }

   public IBonusManager getBonusManager() {
     return this.bonusManager;
   }

   public void setBonusManager(IBonusManager bonusManager) {
     this.bonusManager = bonusManager;
   }

   public List getLvList() {
     return this.lvList;
   }

   public void setLvList(List lvList) {
     this.lvList = lvList;
   }

   public IMemberLvManager getMemberLvManager() {
     return this.memberLvManager;
   }

   public void setMemberLvManager(IMemberLvManager memberLvManager) {
     this.memberLvManager = memberLvManager;
   }

   public int getBonusid() {
     return this.bonusid;
   }

   public void setBonusid(int bonusid) {
     this.bonusid = bonusid;
   }

   public IBonusTypeManager getBonusTypeManager() {
     return this.bonusTypeManager;
   }

   public void setBonusTypeManager(IBonusTypeManager bonusTypeManager) {
     this.bonusTypeManager = bonusTypeManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\action\BonusAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */