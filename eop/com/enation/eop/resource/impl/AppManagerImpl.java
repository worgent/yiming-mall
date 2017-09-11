 package com.enation.eop.resource.impl;

 import com.enation.eop.resource.IAppManager;
 import com.enation.eop.resource.model.EopApp;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;





 public class AppManagerImpl
   implements IAppManager
 {
   private IDaoSupport<EopApp> daoSupport;

   public EopApp get(String appid)
   {
     String sql = "select * from eop_app where id=?";
     return (EopApp)this.daoSupport.queryForObject(sql, EopApp.class, new Object[] { appid });
   }

   public List<EopApp> list() {
     String sql = "select * from eop_app";
     return this.daoSupport.queryForList(sql, EopApp.class, new Object[0]);
   }

   public IDaoSupport<EopApp> getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport<EopApp> daoSupport) {
     this.daoSupport = daoSupport;
   }

   public void add(EopApp app) {
     this.daoSupport.insert("eop_app", app);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\impl\AppManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */