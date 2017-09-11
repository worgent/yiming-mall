 package com.enation.app.shop.component.bonus.depot.plugin.adminuser;

 import com.enation.app.base.core.plugin.user.IAdminUserDeleteEvent;
 import com.enation.app.base.core.plugin.user.IAdminUserInputDisplayEvent;
 import com.enation.app.base.core.plugin.user.IAdminUserLoginEvent;
 import com.enation.app.base.core.plugin.user.IAdminUserOnAddEvent;
 import com.enation.app.base.core.plugin.user.IAdminUserOnEditEvent;
 import com.enation.app.shop.core.model.Depot;
 import com.enation.app.shop.core.model.DepotUser;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.IntegerMapper;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;







 @Component
 public class DepotManagerPlugin
   extends AutoRegisterPlugin
   implements IAdminUserOnAddEvent, IAdminUserOnEditEvent, IAdminUserInputDisplayEvent, IAdminUserDeleteEvent, IAdminUserLoginEvent
 {
   private IDepotManager depotManager;
   private IDaoSupport baseDaoSupport;

   public String getInputHtml(AdminUser user)
   {
     List<Depot> roomList = this.depotManager.list();
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.putData("roomList", roomList);

     if (user != null) {
       Integer depotid = (Integer)this.baseDaoSupport.queryForObject("select depotid from depot_user where userid=?", new IntegerMapper(), new Object[] { user.getUserid() });
       freeMarkerPaser.putData("depotid", depotid);
     }

     return freeMarkerPaser.proessPageContent();
   }

   public void onEdit(Integer userid)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String depotid = request.getParameter("depotid");
     if (!StringUtil.isEmpty(depotid)) {
       String sql = "select count(0) from depot_user where  userid=?";
       int count = this.baseDaoSupport.queryForInt(sql, new Object[] { userid });
       if (count > 0) {
         this.baseDaoSupport.execute("update depot_user set depotid=? where userid=?", new Object[] { depotid, userid });
       } else {
         this.baseDaoSupport.execute("insert into depot_user(userid,depotid)values(?,?)", new Object[] { userid, depotid });
       }
     }
   }

   public void onAdd(Integer userid) {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     if (request != null) {
       String depotid = request.getParameter("depotid");
       if (!StringUtil.isEmpty(depotid)) {
         this.baseDaoSupport.execute("insert into depot_user(userid,depotid)values(?,?)", new Object[] { userid, depotid });
       }
     }
   }


   public void onDelete(int userid)
   {
     this.baseDaoSupport.execute("delete from depot_user where userid=?", new Object[] { Integer.valueOf(userid) });
   }

   public void onLogin(AdminUser user)
   {
     WebSessionContext<AdminUser> sessonContext = ThreadContextHolder.getSessionContext();

     Integer depotid = (Integer)this.baseDaoSupport.queryForObject("select depotid from depot_user where userid=?", new IntegerMapper(), new Object[] { user.getUserid() });
     DepotUser stockUser = new DepotUser();
     stockUser.setFounder(user.getFounder());
     stockUser.setPassword(user.getPassword());
     stockUser.setRealname(user.getRealname());
     stockUser.setRemark(user.getRemark());
     stockUser.setRoleids(user.getRoleids());
     stockUser.setSiteid(user.getSiteid());
     stockUser.setState(user.getState());
     stockUser.setUserdept(user.getUserdept());
     stockUser.setUserid(user.getUserid());
     stockUser.setUsername(user.getUsername());
     stockUser.setUserno(user.getUserno());
     stockUser.setDateline(user.getDateline());
     if (depotid != null)
       stockUser.setDepotid(depotid);
     stockUser.setAuthList(user.getAuthList());
     sessonContext.setAttribute("admin_user_key", stockUser);
   }



   public IDepotManager getDepotManager()
   {
     return this.depotManager;
   }

   public void setDepotManager(IDepotManager depotManager) {
     this.depotManager = depotManager;
   }

   public IDaoSupport getBaseDaoSupport() {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
     this.baseDaoSupport = baseDaoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\depot\plugin\adminuser\DepotManagerPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */