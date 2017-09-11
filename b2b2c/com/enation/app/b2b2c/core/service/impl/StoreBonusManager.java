 package com.enation.app.b2b2c.core.service.impl;

 import com.enation.app.b2b2c.core.service.IStoreBonusManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import org.springframework.stereotype.Component;









 @Component
 public class StoreBonusManager
   extends BaseSupport
   implements IStoreBonusManager
 {
   public List getBonusList(Integer store_id)
   {
     List list = this.baseDaoSupport.queryForList("select * from es_bonus_type where store_id=?", new Object[] { store_id });
     return list;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\impl\StoreBonusManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */