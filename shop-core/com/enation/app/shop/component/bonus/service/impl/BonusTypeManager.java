 package com.enation.app.shop.component.bonus.service.impl;

 import com.enation.app.shop.component.bonus.model.BonusType;
 import com.enation.app.shop.component.bonus.service.IBonusTypeManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;





 @Component
 public class BonusTypeManager
   extends BaseSupport
   implements IBonusTypeManager
 {
   public void add(BonusType bronusType)
   {
     this.baseDaoSupport.insert("bonus_type", bronusType);
   }


   public void update(BonusType bronusType)
   {
     this.baseDaoSupport.update("bonus_type", bronusType, " type_id=" + bronusType.getType_id());
   }


   @Transactional(propagation=Propagation.REQUIRED)
   public void delete(Integer[] bonusTypeId)
   {
     Integer[] arr$ = bonusTypeId;int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { int typeid = arr$[i$].intValue();
       this.baseDaoSupport.execute("delete from member_bonus where bonus_type_id=?", new Object[] { Integer.valueOf(typeid) });
       this.baseDaoSupport.execute("delete from bonus_type where type_id=?", new Object[] { Integer.valueOf(typeid) });
     }
   }

   public Page list(int page, int pageSize)
   {
     String sql = "select * from bonus_type order by type_id desc";
     return this.baseDaoSupport.queryForPage(sql, page, pageSize, BonusType.class, new Object[0]);
   }

   public BonusType get(int typeid)
   {
     String sql = "select * from bonus_type  where type_id =?";
     return (BonusType)this.baseDaoSupport.queryForObject(sql, BonusType.class, new Object[] { Integer.valueOf(typeid) });
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\service\impl\BonusTypeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */