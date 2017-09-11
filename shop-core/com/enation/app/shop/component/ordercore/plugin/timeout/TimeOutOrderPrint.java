 package com.enation.app.shop.component.ordercore.plugin.timeout;

 import com.enation.app.base.core.plugin.job.IEveryDayExecuteEvent;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.DateUtil;
 import java.io.PrintStream;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;






 @Component
 public class TimeOutOrderPrint
   extends AutoRegisterPlugin
   implements IEveryDayExecuteEvent
 {
   private IDaoSupport daoSupport;
   private IOrderFlowManager orderFlowManager;

   public void everyDay()
   {
     String sql = "SELECT order_id from es_order  WHERE disabled=0 AND create_time+?<? AND (status=? or status=?) AND create_time>?";
     System.out.println(DateUtil.getDatelineLong());
     List<Map> list = this.daoSupport.queryForList(sql, new Object[] { Integer.valueOf(259200), Long.valueOf(DateUtil.getDatelineLong()), Integer.valueOf(0), Integer.valueOf(9), Integer.valueOf(1398873600) });
     for (Map map : list)
       this.orderFlowManager.cancel(Integer.valueOf(Integer.parseInt(map.get("order_id").toString())), "订单72小时没有进行库款");
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) { this.daoSupport = daoSupport; }

   public IOrderFlowManager getOrderFlowManager() {
     return this.orderFlowManager;
   }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) { this.orderFlowManager = orderFlowManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\ordercore\plugin\timeout\TimeOutOrderPrint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */