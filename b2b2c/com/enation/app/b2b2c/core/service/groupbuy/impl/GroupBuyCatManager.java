 package com.enation.app.b2b2c.core.service.groupbuy.impl;

 import com.enation.app.b2b2c.core.model.groupbuy.GroupBuyCat;
 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyCatManager;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import org.springframework.stereotype.Component;






 @Component
 public class GroupBuyCatManager
   implements IGroupBuyCatManager
 {
   private IDaoSupport daoSupport;

   public Page list(int pageNo, int pageSize)
   {
     String sql = " select * from es_groupbuy_cat order by cat_order ";
     return this.daoSupport.queryForPage(sql, pageNo, pageSize, GroupBuyCat.class, new Object[0]);
   }


   public List<GroupBuyCat> listAll()
   {
     String sql = " select * from es_groupbuy_cat order by cat_order ";
     return this.daoSupport.queryForList(sql, GroupBuyCat.class, new Object[0]);
   }



   public GroupBuyCat get(int catid)
   {
     return (GroupBuyCat)this.daoSupport.queryForObject("select * from es_groupbuy_cat where catid=?", GroupBuyCat.class, new Object[] { Integer.valueOf(catid) });
   }


   public void add(GroupBuyCat groupBuyCat)
   {
     this.daoSupport.insert("es_groupbuy_cat", groupBuyCat);
   }


   public void update(GroupBuyCat groupBuyCat)
   {
     this.daoSupport.update("es_groupbuy_cat", groupBuyCat, "catid=" + groupBuyCat.getCatid());
   }

   public void delete(Integer[] catid)
   {
     if ((catid == null) || (catid.length == 0)) return;
     String id_str = StringUtil.arrayToString(catid, ",");
     this.daoSupport.execute("delete from es_groupbuy_cat where catid in (" + id_str + ")", new Object[0]);
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\groupbuy\impl\GroupBuyCatManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */