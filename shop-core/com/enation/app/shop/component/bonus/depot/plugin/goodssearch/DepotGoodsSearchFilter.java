 package com.enation.app.shop.component.bonus.depot.plugin.goodssearch;

 import com.enation.app.base.core.service.ISettingService;
 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.shop.core.model.DepotUser;
 import com.enation.app.shop.core.plugin.goods.IGoodsSearchFilter;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.IDBRouter;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;









 @Component
 public class DepotGoodsSearchFilter
   extends AutoRegisterPlugin
   implements IGoodsSearchFilter
 {
   private IDBRouter baseDBRouter;
   private IAdminUserManager adminUserManager;
   private ISettingService settingService;

   public String getSelector()
   {
     return "";
   }

   public IDBRouter getBaseDBRouter() {
     return this.baseDBRouter;
   }

   public void setBaseDBRouter(IDBRouter baseDBRouter) {
     this.baseDBRouter = baseDBRouter;
   }

   public String getFrom()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String optype = request.getParameter("optype");

     AdminUser user = this.adminUserManager.getCurrentUser();
     if ((("mng".equals(optype)) || ("stock".equals(optype))) && (user.getFounder() == 0)) {
       return " inner join " + this.baseDBRouter.getTableName("goods_depot") + " gd on g.goods_id=gd.goodsid ";
     }
     if ("monitor".equals(optype)) {
       return " inner join " + this.baseDBRouter.getTableName("goods_depot") + " gd on g.goods_id=gd.goodsid ";
     }
     return "";
   }

   public void filter(StringBuffer sql)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String optype = request.getParameter("optype");

     AdminUser user = this.adminUserManager.getCurrentUser();
     if ("stock".equals(optype)) {
       if (user.getFounder() == 0) {
         DepotUser depotUser = (DepotUser)user;
         sql.append(" and gd.iscmpl=0 and gd.depotid=" + depotUser.getDepotid());
       } else {
         sql.append(" and g.goods_id not in(select goodsid from " + this.baseDBRouter.getTableName("goods_depot") + " where iscmpl=1)");
       }
     }
     else if (("mng".equals(optype)) && (user.getFounder() == 0)) {
       DepotUser depotUser = (DepotUser)user;

       sql.append(" and gd.iscmpl=1 and gd.depotid=" + depotUser.getDepotid());

     }
     else if ("monitor".equals(optype)) {
       String depotid = request.getParameter("depotid");
       sql.append(" and gd.iscmpl=0 and gd.depotid=" + depotid);
     }
   }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public ISettingService getSettingService() {
     return this.settingService;
   }

   public void setSettingService(ISettingService settingService) {
     this.settingService = settingService;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\depot\plugin\goodssearch\DepotGoodsSearchFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */