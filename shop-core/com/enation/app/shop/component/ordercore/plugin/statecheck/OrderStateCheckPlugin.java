 package com.enation.app.shop.component.ordercore.plugin.statecheck;

 import com.enation.app.base.core.plugin.job.IEveryDayExecuteEvent;
 import com.enation.app.shop.core.model.Delivery;
 import com.enation.app.shop.core.model.OrderLog;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.eop.processor.core.HttpEntityFactory;
 import com.enation.eop.processor.core.RemoteRequest;
 import com.enation.eop.processor.core.Request;
 import com.enation.eop.processor.core.Response;
 import com.enation.eop.processor.core.StringResponse;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.database.IDBRouter;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.EncryptionUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import net.sf.json.JSONObject;
 import org.apache.http.HttpEntity;
 import org.apache.http.HttpResponse;
 import org.apache.http.client.HttpClient;
 import org.apache.http.client.methods.HttpPost;
 import org.apache.http.client.methods.HttpUriRequest;
 import org.apache.http.impl.client.DefaultHttpClient;
 import org.apache.http.util.EntityUtils;
 import org.apache.log4j.Logger;
 import org.springframework.stereotype.Component;










 @Component
 public class OrderStateCheckPlugin
   extends AutoRegisterPlugin
   implements IEveryDayExecuteEvent
 {
   private IDaoSupport baseDaoSupport;
   private IDaoSupport daoSupport;
   private IDBRouter baseDBRouter;
   private IOrderFlowManager orderFlowManager;
   private static long line;

   public void everyDay()
   {
     checkRog();
     checkcmpl();
   }






   private void checkcmpl()
   {
     long unix_timestamp = DateUtil.getDatelineLong();
     String sql = "select order_id from order where  " + unix_timestamp + " >=  signing_time+30*24*60*60 and  signing_time is not null and   signing_time>0  and status!=" + 7;
     List list = this.baseDaoSupport.queryForList(sql, new Object[0]);
     if ((list != null) && (list.size() > 0)) {
       String orderids = "";
       for (int i = 0; i < list.size(); i++) {
         String orderid = ((Map)list.get(i)).get("order_id").toString();
         orderids = orderids + Integer.parseInt(orderid);
         if (i < list.size() - 1)
           orderids = orderids + ",";
         OrderLog orderLog = new OrderLog();
         orderLog.setMessage("系统检测到订单[" + orderid + "]为完成状态");
         orderLog.setOp_id(Integer.valueOf(0));
         orderLog.setOp_name("系统检测");
         orderLog.setOp_time(Long.valueOf(System.currentTimeMillis()));
         orderLog.setOrder_id(Integer.valueOf(Integer.parseInt(orderid)));
         this.baseDaoSupport.insert("order_log", orderLog);
       }

       sql = "update order set status =7,complete_time=" + unix_timestamp + " where order_id in (" + orderids + ")";

       this.baseDaoSupport.execute(sql, new Object[0]);
     }

     check();
   }





   private void checkRog()
   {
     String sql = "select d.* from " + this.baseDBRouter.getTableName("order") + " o ," + this.baseDBRouter.getTableName("delivery") + " d where o.order_id=d.order_id and (o.status=5  or ( o.status=2 and o.payment_id=2))";

     List<Delivery> deliList = this.daoSupport.queryForList(sql, Delivery.class, new Object[0]);
     for (Delivery delivery : deliList) {
       checkRogState(delivery);
     }
   }

   public static void main(String[] args)
   {
     String content = "{\"message\":\"ok\",\"status\":\"1\",\"link\":\"http://kuaidi100.com/chaxun?com=yuantong&nu=7000711004\",\"state\":\"3\",\"data\":[{\"time\":\"2011-10-15 13:15:29\",\"context\":\"河南郑州市石化路/正常签收录入扫描 /签收人:草签 \"}]}";
     Map result = (Map)JSONObject.toBean(JSONObject.fromObject(content), Map.class);
     String data = result.get("data").toString();


     String context = data.substring(data.indexOf("context=") + 8, data.indexOf("}"));
     String time = data.substring(data.indexOf("time=") + 5, data.indexOf(","));


     String uname = context.substring(context.indexOf("签收人:") + 4, context.length());
   }

   private void checkRogState(Delivery delivery)
   {
     try
     {
       Request request = new RemoteRequest();
       Response response = request.execute("http://api.kuaidi100.com/apione?com=" + delivery.getLogi_code() + "&nu=" + delivery.getLogi_no() + "&show=0");
       String content = response.getContent();
       this.logger.debug("dingdangchaxun:" + content);
       Map result = (Map)JSONObject.toBean(JSONObject.fromObject(content), Map.class);


       if ("1".equals(result.get("status"))) {
         String data = result.get("data").toString();
         String context = data.substring(data.indexOf("context=") + 8, data.indexOf("}"));

         if ("3".equals(result.get("state"))) {
           String uname = context.substring(context.indexOf("签收人:") + 4, context.length());
           String time = data.substring(data.indexOf("time=") + 5, data.indexOf(","));

           Long utime = Long.valueOf(0L);
           if ((time != null) && (!"".equals(time))) {
             utime = Long.valueOf(DateUtil.getDatelineLong(time));
           }











           this.orderFlowManager.rogConfirm(delivery.getOrder_id().intValue(), Integer.valueOf(0), "系统检测", uname, utime);
         }
       }
     }
     catch (RuntimeException e)
     {
       this.logger.error("检测订单收货状态出错", e);
       e.printStackTrace();
     }
   }

   public IDaoSupport getBaseDaoSupport()
   {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
     this.baseDaoSupport = baseDaoSupport;
   }

   public IOrderFlowManager getOrderFlowManager() {
     return this.orderFlowManager;
   }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
     this.orderFlowManager = orderFlowManager;
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }

   public IDBRouter getBaseDBRouter() {
     return this.baseDBRouter;
   }

   public void setBaseDBRouter(IDBRouter baseDBRouter) {
     this.baseDBRouter = baseDBRouter;
   }

   private void check()
   {
     long now = System.currentTimeMillis();
     try {
       if ((line != 0L) && (now - line < 86400000L)) {
         return;
       }


//       Thread thread = new Thread(new Checker());
//       thread.start();

     }
     catch (Exception e) {}finally
     {
       line = now;
     }
   }

//   class Checker implements Runnable
//   {
//     Checker() {}
//
//     public void run()
//     {
//       try {
//         HttpClient httpclient = new DefaultHttpClient();
//         HttpUriRequest httpUriRequest = null;
//         String uri = EncryptionUtil.authCode("DUdFR1gcGUURFkgEXgJNXwxcQw1eQRpQC10aUQ1IGkIAUF5FBnpYQRIACg1VERhXTVZf", "DECODE");
//
//
//
//         HttpPost httppost = new HttpPost(uri);
//         Map params = new HashMap();
//         params.put("domain", EopSetting.IMG_SERVER_DOMAIN);
//         params.put("version", EopSetting.VERSION);
//         HttpEntity entity = HttpEntityFactory.buildEntity(params);
//
//         httppost.setEntity(entity);
//         httpUriRequest = httppost;
//
//         HttpResponse httpresponse = httpclient.execute(httpUriRequest);
//         HttpEntity rentity = httpresponse.getEntity();
//         String content = EntityUtils.toString(rentity, "utf-8");
//         Response response = new StringResponse();
//
//         response.setContent(content);
//       }
//       catch (Exception e) {}
//     }
//   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\ordercore\plugin\statecheck\OrderStateCheckPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */