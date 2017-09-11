 package com.enation.app.shop.component.bonus.service;

 import com.enation.app.shop.component.bonus.model.MemberBonus;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.util.CurrencyUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.List;









 public final class BonusSession
 {
   private static final String list_sessionkey = "bonus_list_session_key";
   private static final String one_sessionkey = "bonus_one_session_key";

   public static void use(MemberBonus bonus)
   {
     if (isExists(bonus)) {
       return;
     }


     List<MemberBonus> bounsList = (List)ThreadContextHolder.getSessionContext().getAttribute("bonus_list_session_key");
     if (bounsList == null) {
       bounsList = new ArrayList();
     }

     bounsList.add(bonus);

     ThreadContextHolder.getSessionContext().setAttribute("bonus_list_session_key", bounsList);
   }







   public static void cancel(String sn)
   {
     if (StringUtil.isEmpty(sn))
     {
       return;
     }

     List<MemberBonus> bounsList = (List)ThreadContextHolder.getSessionContext().getAttribute("bonus_list_session_key");
     if (bounsList == null) {
       return;
     }

     List<MemberBonus> newBounsList = new ArrayList();
     for (MemberBonus memberBonus : bounsList) {
       if (!sn.equals(memberBonus.getBonus_sn()))
       {

         newBounsList.add(memberBonus);
       }
     }
     ThreadContextHolder.getSessionContext().setAttribute("bonus_list_session_key", newBounsList);
   }








   public static void useOne(MemberBonus bonus)
   {
     ThreadContextHolder.getSessionContext().setAttribute("bonus_one_session_key", bonus);
   }









   public static List<MemberBonus> get()
   {
     return (List)ThreadContextHolder.getSessionContext().getAttribute("bonus_list_session_key");
   }





   public static MemberBonus getOne()
   {
     return (MemberBonus)ThreadContextHolder.getSessionContext().getAttribute("bonus_one_session_key");
   }






   public static double getUseMoney()
   {
     List<MemberBonus> bonusList = get();
     double moneyCount = 0.0D;

     if (bonusList != null)
     {
       for (MemberBonus memberBonus : bonusList) {
         double bonusMoney = memberBonus.getBonus_money();
         moneyCount = CurrencyUtil.add(moneyCount, bonusMoney);
       }
     }



     MemberBonus memberBonus = getOne();
     if (memberBonus != null) {
       double bonusMoney = memberBonus.getBonus_money();
       moneyCount = CurrencyUtil.add(moneyCount, bonusMoney);
     }
     return moneyCount;
   }


   public static void clean() { ThreadContextHolder.getSessionContext().removeAttribute("bonus_one_session_key"); }

   public static void cleanAll() {
     ThreadContextHolder.getSessionContext().removeAttribute("bonus_one_session_key");
     ThreadContextHolder.getSessionContext().removeAttribute("bonus_list_session_key");
   }

   public static boolean isExists(MemberBonus bonus) { List<MemberBonus> bounsList = (List)ThreadContextHolder.getSessionContext().getAttribute("bonus_list_session_key");
     if (bounsList == null) return false;
     for (MemberBonus memberBonus : bounsList) {
       if (memberBonus.getBonus_id() == bonus.getBonus_id()) {
         return true;
       }
     }
     return false;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\service\BonusSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */