 package com.enation.app.b2b2c.core.service.impl;

 import com.enation.app.b2b2c.core.service.IStoreMemberAddressManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;

 @Component
 public class StoreMemberAddressManager
   extends BaseSupport
   implements IStoreMemberAddressManager
 {
   @Transactional(propagation=Propagation.REQUIRED)
   public void updateMemberAddress(Integer memberid, Integer addr_id)
   {
     this.baseDaoSupport.execute("update es_member_address set def_addr=0 where member_id=?", new Object[] { memberid });
     this.baseDaoSupport.execute("update es_member_address set def_addr=1 where addr_id=?", new Object[] { addr_id });
   }

   public Integer getRegionid(Integer member_id)
   {
     String sql = "select region_id from es_member_address where member_id=? and def_addr=1";
     List<Map> result = this.daoSupport.queryForList(sql, new Object[] { member_id });
     if (result.size() == 0) {
       return Integer.valueOf(0);
     }
     return (Integer)((Map)result.get(0)).get("region_id");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\impl\StoreMemberAddressManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */