 package com.enation.app.b2b2c.core.service.groupbuy.impl;

 import com.enation.app.b2b2c.core.model.groupbuy.GroupBuyActive;
 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyActiveManager;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import org.springframework.stereotype.Component;







 @Component
 public class GroupBuyActiveManager
   implements IGroupBuyActiveManager
 {
   private IDaoSupport daoSupport;

   public Page list(int pageNo, int pageSize)
   {
     String sql = "select * from es_groupbuy_active order by add_time desc";
     return this.daoSupport.queryForPage(sql, pageNo, pageSize, GroupBuyActive.class, new Object[0]);
   }


   public List<GroupBuyActive> listEnable()
   {
     String sql = "select * from es_groupbuy_active where end_time>=? order by add_time desc";
     long now = DateUtil.getDatelineLong();
     return this.daoSupport.queryForList(sql, GroupBuyActive.class, new Object[] { Long.valueOf(now) });
   }


   public void add(GroupBuyActive groupBuyActive)
   {
     groupBuyActive.setAdd_time(DateUtil.getDatelineLong());
     this.daoSupport.insert("es_groupbuy_active", groupBuyActive);
   }


   public void update(GroupBuyActive groupBuyActive)
   {
     this.daoSupport.update("es_groupbuy_active", groupBuyActive, "act_id=" + groupBuyActive.getAct_id());
   }

   public void delete(Integer[] ids)
   {
     String idstr = StringUtil.arrayToString(ids, ",");
     this.daoSupport.execute("delete from es_groupbuy_active where act_id in (" + idstr + ")", new Object[0]);
   }

   public void delete(int id)
   {
     this.daoSupport.execute("delete from es_groupbuy_active where act_id=?", new Object[] { Integer.valueOf(id) });
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\groupbuy\impl\GroupBuyActiveManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */