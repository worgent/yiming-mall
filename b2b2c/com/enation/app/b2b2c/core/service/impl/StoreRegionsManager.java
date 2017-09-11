 package com.enation.app.b2b2c.core.service.impl;

 import com.enation.app.b2b2c.core.service.IStoreRegionsManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;








 @Component
 public class StoreRegionsManager
   extends BaseSupport
   implements IStoreRegionsManager
 {
   public List getRegionsToAreaList()
   {
     String sql = "select * from es_regions where p_region_id=?";
     List<Map> list = this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(0) });
     for (Map map : list) {
       Integer regionid = (Integer)map.get("region_id");
       List arealist = this.baseDaoSupport.queryForList(sql, new Object[] { regionid });
       map.put("arealist", arealist);
     }
     return list;
   }





   public List getRegionsbyids(String ids)
   {
     String sql = "select * from es_regions where region_id in (" + ids + ")";
     List list = this.baseDaoSupport.queryForList(sql, new Object[0]);
     return list;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\impl\StoreRegionsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */