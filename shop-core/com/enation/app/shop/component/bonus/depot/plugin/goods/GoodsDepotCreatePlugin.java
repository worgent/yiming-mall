 package com.enation.app.shop.component.bonus.depot.plugin.goods;

 import com.enation.app.base.core.service.IShortMsgManager;
 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.base.core.service.auth.IRoleManager;
 import com.enation.app.shop.core.model.Depot;
 import com.enation.app.shop.core.plugin.goods.IGoodsAfterAddEvent;
 import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;







 @Component
 public class GoodsDepotCreatePlugin
   extends AutoRegisterPlugin
   implements IGoodsAfterAddEvent, IGoodsDeleteEvent
 {
   private IDepotManager depotManager;
   private IDaoSupport baseDaoSupport;
   private IShortMsgManager shortMsgManager;
   private IAdminUserManager adminUserManager;
   private IRoleManager roleManager;

   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
     throws RuntimeException
   {
     Integer goodsid = (Integer)goods.get("goods_id");
     List<Depot> depotList = this.depotManager.list();
     for (Depot depot : depotList) {
       this.baseDaoSupport.execute("insert into goods_depot(goodsid,depotid,iscmpl)values(?,?,?)", new Object[] { goodsid, depot.getId(), Integer.valueOf(0) });
     }
   }






   public void onGoodsDelete(Integer[] goodsid)
   {
     if ((goodsid == null) || (goodsid.length == 0)) return;
     String goodsidstr = StringUtil.arrayToString(goodsid, ",");
     this.baseDaoSupport.execute("delete from goods_depot where goodsid in (" + goodsidstr + ")", new Object[0]);
   }

   public IDepotManager getDepotManager()
   {
     return this.depotManager;
   }

   public void setDepotManager(IDepotManager depotManager) {
     this.depotManager = depotManager;
   }

   public IDaoSupport getBaseDaoSupport()
   {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport baseDaoSupport)
   {
     this.baseDaoSupport = baseDaoSupport;
   }

   public IShortMsgManager getShortMsgManager()
   {
     return this.shortMsgManager;
   }

   public void setShortMsgManager(IShortMsgManager shortMsgManager) {
     this.shortMsgManager = shortMsgManager;
   }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public IRoleManager getRoleManager() {
     return this.roleManager;
   }

   public void setRoleManager(IRoleManager roleManager) {
     this.roleManager = roleManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\depot\plugin\goods\GoodsDepotCreatePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */