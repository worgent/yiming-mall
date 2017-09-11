 package com.enation.app.shop.component.bonus.action;

 import com.enation.app.shop.component.bonus.model.BonusType;
 import com.enation.app.shop.component.bonus.service.IBonusTypeManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;










 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/com/enation/app/shop/component/bonus/action/html/bonus_type_list.html"), @org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/com/enation/app/shop/component/bonus/action/html/bonus_type_add.html"), @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/com/enation/app/shop/component/bonus/action/html/bonus_type_edit.html")})
 public class BonusTypeAction
   extends WWAction
 {
   private IBonusTypeManager bonusTypeManager;
   private BonusType bonusType;
   private String useTimeStart;
   private String useTimeEnd;
   private String sendTimeStart;
   private String sendTimeEnd;
   private int typeid;
   private Integer[] type_id;

   public String list()
   {
     return "list";
   }

   public String listJson() {
     this.webpage = this.bonusTypeManager.list(getPage(), getPageSize());
     showGridJson(this.webpage);
     return "json_message";
   }


   public String add()
   {
     return "add";
   }


   public String edit()
   {
     this.bonusType = this.bonusTypeManager.get(this.typeid);
     return "edit";
   }

   public String saveAdd()
   {
     if (StringUtil.isEmpty(this.bonusType.getRecognition())) {
       showErrorJson("请输入优惠卷识别码");
       return "json_message";
     }

     if (StringUtil.isEmpty(this.bonusType.getType_name())) {
       showErrorJson("请输入类型名称");
       return "json_message";
     }


     if (this.bonusType.getType_money() == null) {
       showErrorJson("请输入金额");
       return "json_message";
     }

     if (StringUtil.isEmpty(this.useTimeStart)) {
       showErrorJson("请输入使用起始日期");
       return "json_message";
     }
     this.bonusType.setUse_start_date(DateUtil.getDateline(this.useTimeStart));

     if (StringUtil.isEmpty(this.useTimeEnd)) {
       showErrorJson("请输入使用结束日期");
       return "json_message";
     }
     this.bonusType.setUse_end_date(DateUtil.getDateline(this.useTimeEnd));

     if (!StringUtil.isEmpty(this.sendTimeStart)) {
       this.bonusType.setSend_start_date(DateUtil.getDateline(this.sendTimeStart));
     }

     if (!StringUtil.isEmpty(this.sendTimeEnd)) {
       this.bonusType.setSend_end_date(DateUtil.getDateline(this.sendTimeEnd));
     }

     try
     {
       this.bonusTypeManager.add(this.bonusType);
       showSuccessJson("保存优惠卷类型成功");
     } catch (Throwable e) {
       this.logger.error("保存优惠卷类型出错", e);
       showErrorJson("保存优惠卷类型出错" + e.getMessage());
     }


     return "json_message";
   }




   public String saveEdit()
   {
     if (StringUtil.isEmpty(this.bonusType.getRecognition())) {
       showErrorJson("请输入优惠卷识别码");
       return "json_message";
     }

     if (StringUtil.isEmpty(this.bonusType.getType_name())) {
       showErrorJson("请输入类型名称");
       return "json_message";
     }


     if (this.bonusType.getType_money() == null) {
       showErrorJson("请输入金额");
       return "json_message";
     }

     if (StringUtil.isEmpty(this.useTimeStart)) {
       showErrorJson("请输入使用起始日期");
       return "json_message";
     }
     this.bonusType.setUse_start_date(DateUtil.getDateline(this.useTimeStart));

     if (StringUtil.isEmpty(this.useTimeEnd)) {
       showErrorJson("请输入使用结束日期");
       return "json_message";
     }
     this.bonusType.setUse_end_date(DateUtil.getDateline(this.useTimeEnd));

     if (!StringUtil.isEmpty(this.sendTimeStart)) {
       this.bonusType.setSend_start_date(DateUtil.getDateline(this.sendTimeStart));
     }

     if (!StringUtil.isEmpty(this.sendTimeEnd)) {
       this.bonusType.setSend_end_date(DateUtil.getDateline(this.sendTimeEnd));
     }

     try
     {
       this.bonusTypeManager.update(this.bonusType);
       showSuccessJson("保存优惠卷类型成功");
     } catch (Throwable e) {
       this.logger.error("保存优惠卷类型出错", e);
       showErrorJson("保存优惠卷类型出错" + e.getMessage());
     }


     return "json_message";
   }

   public String delete()
   {
     try
     {
       this.bonusTypeManager.delete(this.type_id);
       showSuccessJson("删除优惠卷类型成功");
     } catch (Throwable e) {
       this.logger.error("删除优惠卷类型出错", e);
       showErrorJson("删除优惠卷类型出错" + e.getMessage());
     }

     return "json_message";
   }

   public IBonusTypeManager getBonusTypeManager()
   {
     return this.bonusTypeManager;
   }

   public void setBonusTypeManager(IBonusTypeManager bonusTypeManager)
   {
     this.bonusTypeManager = bonusTypeManager;
   }

   public String getUseTimeStart()
   {
     return this.useTimeStart;
   }

   public void setUseTimeStart(String useTimeStart)
   {
     this.useTimeStart = useTimeStart;
   }

   public String getUseTimeEnd()
   {
     return this.useTimeEnd;
   }

   public void setUseTimeEnd(String useTimeEnd)
   {
     this.useTimeEnd = useTimeEnd;
   }

   public String getSendTimeStart()
   {
     return this.sendTimeStart;
   }

   public void setSendTimeStart(String sendTimeStart)
   {
     this.sendTimeStart = sendTimeStart;
   }

   public String getSendTimeEnd()
   {
     return this.sendTimeEnd;
   }

   public void setSendTimeEnd(String sendTimeEnd)
   {
     this.sendTimeEnd = sendTimeEnd;
   }

   public int getTypeid()
   {
     return this.typeid;
   }

   public void setTypeid(int typeid)
   {
     this.typeid = typeid;
   }

   public BonusType getBonusType()
   {
     return this.bonusType;
   }

   public void setBonusType(BonusType bonusType)
   {
     this.bonusType = bonusType;
   }

   public Integer[] getType_id() {
     return this.type_id;
   }

   public void setType_id(Integer[] type_id) {
     this.type_id = type_id;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\action\BonusTypeAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */