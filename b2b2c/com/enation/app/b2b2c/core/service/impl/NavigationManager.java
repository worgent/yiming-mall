 package com.enation.app.b2b2c.core.service.impl;

 import com.enation.app.b2b2c.core.model.Navigation;
 import com.enation.app.b2b2c.core.service.INavigationManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import org.springframework.stereotype.Component;

 @Component
 public class NavigationManager
   extends BaseSupport
   implements INavigationManager
 {
   public List getNavicationList(Integer storeid)
   {
     String sql = "select * from es_navigation where store_id=" + storeid;
     List list = this.baseDaoSupport.queryForList(sql, new Object[0]);
     return list;
   }

   public void save(Navigation navigation)
   {
     this.baseDaoSupport.insert("es_navigation", navigation);
   }


   public void edit(Navigation navigation)
   {
     this.baseDaoSupport.update("es_navigation", navigation, " id=" + navigation.getId() + " and store_id=" + navigation.getStore_id());
   }

   public void delete(Integer id)
   {
     this.baseDaoSupport.execute("delete from es_navigation where id=" + id, new Object[0]);
   }



   public Navigation getNavication(Integer id)
   {
     String sql = "select * from es_navigation where id=" + id;
     List<Navigation> list = this.baseDaoSupport.queryForList(sql, Navigation.class, new Object[0]);
     Navigation navigation = (Navigation)list.get(0);
     return navigation;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\impl\NavigationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */