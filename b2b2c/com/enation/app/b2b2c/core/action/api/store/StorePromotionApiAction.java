 package com.enation.app.b2b2c.core.action.api.store;

 import com.enation.app.b2b2c.core.model.StoreBonus;
 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStorePromotionManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.DateUtil;
 import java.io.IOException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.stereotype.Component;























 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("promotion")
 @Results({@org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/themes/default/b2b2c/storesite/navication_edit.html")})
 public class StorePromotionApiAction
   extends WWAction
 {
   private IStorePromotionManager storePromotionManager;
   private IStoreMemberManager storeMemberManager;
   private String type_name;
   private Double min_goods_amount;
   private Double type_money;
   private String useTimeStart;
   private String useTimeEnd;
   private String img_bonus;
   private Integer store_id;
   private Integer type_id;

   public String add_fullSubtract()
   {
     StoreMember member = this.storeMemberManager.getStoreMember();
     StoreBonus bonus = new StoreBonus();
     bonus.setType_name(this.type_name);
     bonus.setType_money(this.type_money);
     bonus.setMin_goods_amount(this.min_goods_amount);
     bonus.setUse_start_date(DateUtil.getDateline(this.useTimeStart));
     bonus.setUse_end_date(DateUtil.getDateline(this.useTimeEnd));
     bonus.setStore_id(member.getStore_id());
     try {
       this.storePromotionManager.add_FullSubtract(bonus);
       showSuccessJson("添加成功");
     } catch (Exception e) {
       showSuccessJson("添加失败");
     }
     return "json_message";
   }




   public String receiveBonus()
   {
     StoreMember member = this.storeMemberManager.getStoreMember();

     String ctx = getRequest().getContextPath();
     if ("/".equals(ctx)) {
       ctx = "";
     }
     if (member == null) {
       HttpServletResponse response = ThreadContextHolder.getHttpResponse();
       try {
         response.sendRedirect(ctx + "/store/login.html");
         return null;
       } catch (IOException e) {
         e.printStackTrace();
         return null;
       }
     }
     try {
       this.storePromotionManager.receive_bonus(member.getMember_id(), this.store_id, this.type_id);
       showSuccessJson("领取成功!");
     } catch (Exception e) {
       showSuccessJson("出现错误，请稍后重试！");
     }

     return "json_message";
   }


   public IStorePromotionManager getStorePromotionManager()
   {
     return this.storePromotionManager;
   }

   public void setStorePromotionManager(IStorePromotionManager storePromotionManager)
   {
     this.storePromotionManager = storePromotionManager;
   }

   public String getType_name()
   {
     return this.type_name;
   }

   public void setType_name(String type_name)
   {
     this.type_name = type_name;
   }

   public Double getMin_goods_amount()
   {
     return this.min_goods_amount;
   }

   public void setMin_goods_amount(Double min_goods_amount)
   {
     this.min_goods_amount = min_goods_amount;
   }

   public Double getType_money()
   {
     return this.type_money;
   }

   public void setType_money(Double type_money)
   {
     this.type_money = type_money;
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

   public IStoreMemberManager getStoreMemberManager()
   {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager)
   {
     this.storeMemberManager = storeMemberManager;
   }

   public String getImg_bonus()
   {
     return this.img_bonus;
   }

   public void setImg_bonus(String img_bonus)
   {
     this.img_bonus = img_bonus;
   }

   public Integer getStore_id() {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) {
     this.store_id = store_id;
   }

   public Integer getType_id() {
     return this.type_id;
   }

   public void setType_id(Integer type_id) {
     this.type_id = type_id;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\store\StorePromotionApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */