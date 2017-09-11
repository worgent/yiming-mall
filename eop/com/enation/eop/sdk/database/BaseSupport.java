 package com.enation.eop.sdk.database;

 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.framework.database.IDBRouter;
 import com.enation.framework.database.IDaoSupport;
 import org.apache.log4j.Logger;


 public abstract class BaseSupport<T>
 {
   protected final Logger logger = Logger.getLogger(getClass());


   private IDBRouter baseDBRouter;

   protected IDaoSupport<T> baseDaoSupport;

   protected IDaoSupport<T> daoSupport;


   protected String getTableName(String moude)
   {
     return this.baseDBRouter.getTableName(moude);
   }






   protected void checkIsOwner(Integer userid)
   {
     if (userid == null) {
       throw new PermssionRuntimeException();
     }

     Integer suserid = EopContext.getContext().getCurrentSite().getUserid();

     if (suserid.intValue() != userid.intValue()) {
       throw new PermssionRuntimeException();
     }
   }

   public IDaoSupport<T> getBaseDaoSupport() {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport<T> baseDaoSupport) {
     this.baseDaoSupport = baseDaoSupport;
   }

   public void setDaoSupport(IDaoSupport<T> daoSupport) {
     this.daoSupport = daoSupport;
   }

   public IDBRouter getBaseDBRouter() {
     return this.baseDBRouter;
   }

   public void setBaseDBRouter(IDBRouter baseDBRouter) {
     this.baseDBRouter = baseDBRouter;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\database\BaseSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */