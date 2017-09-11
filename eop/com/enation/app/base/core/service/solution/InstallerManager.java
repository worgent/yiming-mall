 package com.enation.app.base.core.service.solution;

 import com.enation.eop.resource.model.EopProduct;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;




 public class InstallerManager
 {
   private IDaoSupport<EopProduct> daoSupport;

   public void add(Installer installer)
   {
     String sql = "select count(0) from eop_installer where domain=?";
     int count = this.daoSupport.queryForInt(sql, new Object[] { installer.getDomain() });
     if (count == 0)
       this.daoSupport.insert("eop_installer", installer);
   }

   public void add1(Installer installer) {
     this.daoSupport.insert("eop_installer", installer);
   }

   public Page list(int pageNo, int pageSize)
   {
     return this.daoSupport.queryForPage("select * from eop_installer order by installtime desc", pageNo, pageSize, new Object[0]);
   }

   public IDaoSupport<EopProduct> getDaoSupport()
   {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport<EopProduct> daoSupport) {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\InstallerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */