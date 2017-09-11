 package com.enation.app.shop.component.bonus.api;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.component.bonus.model.MemberBonus;
 import com.enation.app.shop.component.bonus.service.BonusSession;
 import com.enation.app.shop.component.bonus.service.IBonusManager;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.JsonMessageUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.Date;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;







 @ParentPackage("shop_default")
 @Namespace("/api/shop")
 @Scope("prototype")
 @Action("bonus")
 public class BonusApiAction
   extends WWAction
 {
   private IBonusManager bonusManager;
   private ICartManager cartManager;
   private int bonusid;
   private String sn;

   public String getMemberBonus()
   {
     try
     {
       Member member = UserServiceFactory.getUserService().getCurrentMember();
       if (member == null) {
         showErrorJson("未登陆，不能使用此api");
         return "json_message";
       }
       Double goodsprice = this.cartManager.countGoodsTotal(getRequest().getSession().getId());
       List bonusList = this.bonusManager.getMemberBonusList(member.getMember_id().intValue(), goodsprice);
       this.json = JsonMessageUtil.getListJson(bonusList);
     } catch (Exception e) {
       this.logger.error("调用获取会员红包api出错", e);
       showErrorJson(e.getMessage());
     }


     return "json_message";
   }








   public String useOne()
   {
     try
     {
       if (this.bonusid == 0) {
         BonusSession.clean();
         showSuccessJson("清除红包成功");
         return "json_message";
       }

       MemberBonus bonus = this.bonusManager.getBonus(this.bonusid);



       Double goodsprice = this.cartManager.countGoodsTotal(getRequest().getSession().getId());
       if (goodsprice.doubleValue() <= bonus.getMin_goods_amount())
       {
         showErrorJson("订单的商品金额不足[" + bonus.getMin_goods_amount() + "],不能使用此红包");
         return "json_message";
       }


       BonusSession.useOne(bonus);
       showSuccessJson("红包使用成功");
     } catch (Exception e) {
       showErrorJson("使用红包发生错误[" + e.getMessage() + "]");
       this.logger.error("使用红包发生错误", e);
     }
     return "json_message";
   }


   public String useSn()
   {
     try
     {
       if (StringUtil.isEmpty(this.sn)) {
         showErrorJson("红包编号不能为空");
         return "json_message";
       }

       MemberBonus bonus = this.bonusManager.getBonus(this.sn);
       if (bonus == null) {
         showErrorJson("您输入的红包编号不正确");
         return "json_message";
       }


       if (bonus.getUsed_time() != null)
       {
         showErrorJson("此红包已被使用过");
         return "json_message";
       }


       Double goodsprice = this.cartManager.countGoodsTotal(getRequest().getSession().getId());
       if (goodsprice.doubleValue() <= bonus.getMin_goods_amount())
       {
         showErrorJson("订单的商品金额不足[" + bonus.getMin_goods_amount() + "],不能使用此红包");
         return "json_message";
       }


       int now = DateUtil.getDateline();
       if (bonus.getUse_start_date() > now) {
         long l = Long.valueOf(bonus.getUse_start_date()).longValue() * 1000L;
         showErrorJson("此红包还未到使用期，开始使用时间为[" + DateUtil.toString(new Date(l), "yyyy年MM月dd日") + "]");
         return "json_message";
       }

       if (bonus.getUse_end_date() < now) {
         long l = Long.valueOf(bonus.getUse_end_date()).longValue() * 1000L;
         showErrorJson("此红包已过期，使用截至时间为[" + DateUtil.toString(new Date(l), "yyyy年MM月dd日") + "]");
         return "json_message";
       }

       BonusSession.use(bonus);
       showSuccessJson("红包使用成功");
     } catch (Exception e) {
       showErrorJson("使用红包发生错误[" + e.getMessage() + "]");
       this.logger.error("使用红包发生错误", e);
     }
     return "json_message";
   }





   public String cancelSn()
   {
     try
     {
       if (StringUtil.isEmpty(this.sn)) {
         showErrorJson("编号不能为空");
         return "json_message";
       }
       BonusSession.cancel(this.sn);
       showSuccessJson("取消成功");
     } catch (Exception e) {
       showErrorJson("取消红包发生错误[" + e.getMessage() + "]");
       this.logger.error("取消红包发生错误", e);
     }
     return "json_message";
   }









   public String getUseBonusMoney()
   {
     try
     {
       double moneyCount = BonusSession.getUseMoney();
       this.json = JsonMessageUtil.getNumberJson("money", Double.valueOf(moneyCount));
     }
     catch (Exception e) {
       this.logger.error("获取红包金额出错", e);
       showErrorJson("获取红包金额出错[" + e.getMessage() + "]");
     }


     return "json_message";
   }

   public IBonusManager getBonusManager()
   {
     return this.bonusManager;
   }

   public void setBonusManager(IBonusManager bonusManager) {
     this.bonusManager = bonusManager;
   }

   public ICartManager getCartManager() {
     return this.cartManager;
   }

   public void setCartManager(ICartManager cartManager) {
     this.cartManager = cartManager;
   }

   public int getBonusid() {
     return this.bonusid;
   }

   public void setBonusid(int bonusid) {
     this.bonusid = bonusid;
   }

   public String getSn() {
     return this.sn;
   }

   public void setSn(String sn) {
     this.sn = sn;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\api\BonusApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */