 package com.enation.eop.resource.impl;

 import com.enation.eop.resource.IUserDetailManager;
 import com.enation.eop.resource.model.EopUserDetail;
 import com.enation.framework.database.IDaoSupport;



 public class UserDetailManagerImpl
   implements IUserDetailManager
 {
   private IDaoSupport<EopUserDetail> daoSupport;

   public void add(EopUserDetail eopUserDetail)
   {
     this.daoSupport.insert("eop_userdetail", eopUserDetail);
   }

   public void edit(EopUserDetail eopUserDetail) {
     this.daoSupport.update("eop_userdetail", eopUserDetail, " userid = " + eopUserDetail.getUserid());
   }

   public EopUserDetail get(Integer userid) {
     return (EopUserDetail)this.daoSupport.queryForObject("select * from eop_userdetail where userid = ?", EopUserDetail.class, new Object[] { userid });
   }

   public IDaoSupport<EopUserDetail> getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport<EopUserDetail> daoSupport) {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\impl\UserDetailManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */