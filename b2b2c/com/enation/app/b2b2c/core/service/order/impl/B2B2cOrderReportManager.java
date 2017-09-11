 package com.enation.app.b2b2c.core.service.order.impl;

 import com.enation.app.b2b2c.core.service.order.IB2B2cOrderReportManager;
 import com.enation.app.shop.core.model.PaymentLogType;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.Map;

 @org.springframework.stereotype.Component
 public class B2B2cOrderReportManager extends BaseSupport implements IB2B2cOrderReportManager
 {
   public Page listPayment(Map map, int pageNo, int pageSize, String order)
   {
     String sql = createTempSql(map);
     return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
   }

   private String createTempSql(Map map)
   {
     Integer stype = (Integer)map.get("stype");
     String keyword = (String)map.get("keyword");
     String start_time = (String)map.get("start_time");
     String end_time = (String)map.get("end_time");
     Integer status = (Integer)map.get("status");
     String sn = (String)map.get("sn");
     Integer paystatus = (Integer)map.get("paystatus");
     Integer payment_id = (Integer)map.get("payment_id");

     String sql = "select * from payment_logs where payment_id>0 and type=" + PaymentLogType.receivable.getValue();

     if ((stype != null) && (keyword != null) &&
       (stype.intValue() == 0)) {
       sql = sql + " and sn like '%" + keyword + "%'";
       sql = sql + " or ship_name like '%" + keyword + "%'";
     }


     if ((sn != null) && (!StringUtil.isEmpty(sn))) {
       sql = sql + " and order_sn like '%" + sn + "%'";
     }

     if (paystatus != null) {
       sql = sql + " and status=" + paystatus;
     }

     if (payment_id != null) {
       sql = sql + "and order_id in(select order_id from es_order where payment_id=" + payment_id + ")";
     }

     if ((start_time != null) && (!StringUtil.isEmpty(start_time))) {
       long stime = DateUtil.getDateline(start_time + " 00:00:00");
       sql = sql + " and create_time>" + stime;
     }
     if ((end_time != null) && (!StringUtil.isEmpty(end_time))) {
       long etime = DateUtil.getDateline(end_time + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
       sql = sql + " and create_time<" + etime * 1000L;
     }

     sql = sql + " order by payment_id desc";

     return sql;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\order\impl\B2B2cOrderReportManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */