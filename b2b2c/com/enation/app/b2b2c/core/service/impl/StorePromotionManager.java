 package com.enation.app.b2b2c.core.service.impl;

 import com.enation.app.b2b2c.core.model.StoreBonus;
 import com.enation.app.b2b2c.core.service.IStorePromotionManager;
 import com.enation.app.shop.component.bonus.model.BonusType;
 import com.enation.app.shop.component.bonus.service.IBonusTypeManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.DateUtil;
 import java.io.PrintStream;
 import java.util.Date;
 import java.util.Random;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;




 @Component
 public class StorePromotionManager
   extends BaseSupport
   implements IStorePromotionManager
 {
   private IBonusTypeManager bonusTypeManager;

   public void add_FullSubtract(StoreBonus bonus)
   {
     this.baseDaoSupport.insert("es_bonus_type", bonus);
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void receive_bonus(Integer memberid, Integer storeid, Integer type_id)
   {
     BonusType bonusType = this.bonusTypeManager.get(type_id.intValue());

     while (memberid != null)
     {
       String sn = createSn(bonusType.getType_id() + "");
       int c = this.baseDaoSupport.queryForInt("select count(0) from member_bonus where bonus_sn=?", new Object[] { sn });
       if (c == 0) {
         this.baseDaoSupport.execute("insert into es_member_bonus(bonus_type_id,bonus_sn,type_name,bonus_type,create_time,member_id)values(?,?,?,?,?,?)", new Object[] { type_id, sn, bonusType.getType_name(), Integer.valueOf(bonusType.getSend_type()), Long.valueOf(DateUtil.getDatelineLong()), memberid });
         return;
       }
       System.out.println("在生成一个sn码");
     }
   }



   private String createSn(String prefix)
   {
     StringBuffer sb = new StringBuffer();
     sb.append(prefix);
     sb.append(DateUtil.toString(new Date(), "yyMM"));
     sb.append(createRandom());

     return sb.toString();
   }

   private String createRandom() {
     Random random = new Random();
     StringBuffer pwd = new StringBuffer();
     for (int i = 0; i < 6; i++) {
       pwd.append(random.nextInt(9));
     }

     return pwd.toString();
   }


   public IBonusTypeManager getBonusTypeManager()
   {
     return this.bonusTypeManager;
   }

   public void setBonusTypeManager(IBonusTypeManager bonusTypeManager) {
     this.bonusTypeManager = bonusTypeManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\impl\StorePromotionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */