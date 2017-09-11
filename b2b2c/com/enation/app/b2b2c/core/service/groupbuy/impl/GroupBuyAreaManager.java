 package com.enation.app.b2b2c.core.service.groupbuy.impl;

 import com.enation.app.b2b2c.core.model.groupbuy.GroupBuyArea;
 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyAreaManager;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import org.springframework.stereotype.Component;







 @Component
 public class GroupBuyAreaManager
   implements IGroupBuyAreaManager
 {
   private IDaoSupport daoSupport;

   public Page list(int pageNo, int pageSize)
   {
     String sql = " select * from es_groupbuy_area order by area_order ";
     return this.daoSupport.queryForPage(sql, pageNo, pageSize, GroupBuyArea.class, new Object[0]);
   }


   public List<GroupBuyArea> listAll()
   {
     String sql = " select * from es_groupbuy_area order by area_order ";
     return this.daoSupport.queryForList(sql, GroupBuyArea.class, new Object[0]);
   }



   public GroupBuyArea get(int area_id)
   {
     return (GroupBuyArea)this.daoSupport.queryForObject("select * from es_groupbuy_area where area_id=?", GroupBuyArea.class, new Object[] { Integer.valueOf(area_id) });
   }


   public void add(GroupBuyArea groupBuyArea)
   {
     this.daoSupport.insert("es_groupbuy_area", groupBuyArea);
   }


   public void update(GroupBuyArea groupBuyArea)
   {
     this.daoSupport.update("es_groupbuy_area", groupBuyArea, "area_id=" + groupBuyArea.getArea_id());
   }

   public void delete(Integer[] area_id)
   {
     if ((area_id == null) || (area_id.length == 0)) return;
     String id_str = StringUtil.arrayToString(area_id, ",");
     this.daoSupport.execute("delete from es_groupbuy_area where area_id in (" + id_str + ")", new Object[0]);
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\groupbuy\impl\GroupBuyAreaManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */