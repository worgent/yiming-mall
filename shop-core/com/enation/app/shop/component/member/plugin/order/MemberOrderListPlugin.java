 package com.enation.app.shop.component.member.plugin.order;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.plugin.IAjaxExecuteEnable;
 import com.enation.framework.util.StringUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import net.sf.json.JSONArray;
 import org.springframework.stereotype.Component;













 @Component
 public class MemberOrderListPlugin
   extends AutoRegisterPlugin
   implements IMemberTabShowEvent, IAjaxExecuteEnable
 {
   private IOrderManager orderManager;

   public String execute()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     int memberid = StringUtil.toInt(request.getParameter("memberid"), true);


     List orderList = this.orderManager.listOrderByMemberId(memberid);

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.putData("orderList", orderList);
     freeMarkerPaser.setPageName("order_list");
     return freeMarkerPaser.proessPageContent();
   }








   public String onShowMemberDetailHtml(Member member)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     Map statusMap = null;
     String status_Json = null;
     if (statusMap == null) {
       statusMap = new HashMap();
       statusMap = getStatusJson();
       String p = JSONArray.fromObject(statusMap).toString();
       status_Json = p.replace("[", "").replace("]", "");
     }
     freeMarkerPaser.putData("memberid", member.getMember_id());
     freeMarkerPaser.putData("status_Json", status_Json);

     freeMarkerPaser.setPageName("member_order");
     return freeMarkerPaser.proessPageContent();
   }




   private Map getStatusJson()
   {
     Map orderStatus = new HashMap();
     orderStatus.put("0", OrderStatus.getOrderStatusText(0));
     orderStatus.put("9", OrderStatus.getOrderStatusText(9));
     orderStatus.put("2", OrderStatus.getOrderStatusText(2));
     orderStatus.put("4", OrderStatus.getOrderStatusText(4));
     orderStatus.put("5", OrderStatus.getOrderStatusText(5));
     orderStatus.put("6", OrderStatus.getOrderStatusText(6));
     orderStatus.put("-2", OrderStatus.getOrderStatusText(-2));
     orderStatus.put("7", OrderStatus.getOrderStatusText(7));
     orderStatus.put("-1", OrderStatus.getOrderStatusText(-1));
     orderStatus.put("8", OrderStatus.getOrderStatusText(8));
     orderStatus.put("-7", OrderStatus.getOrderStatusText(-7));
     orderStatus.put("-4", OrderStatus.getOrderStatusText(-4));
     orderStatus.put("-3", OrderStatus.getOrderStatusText(-3));
     orderStatus.put("1", OrderStatus.getOrderStatusText(1));
     return orderStatus;
   }


   public String getTabName(Member member)
   {
     return "他的订单";
   }


   public int getOrder()
   {
     return 5;
   }


   public boolean canBeExecute(Member member)
   {
     return true;
   }

   public IOrderManager getOrderManager()
   {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager)
   {
     this.orderManager = orderManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\member\plugin\order\MemberOrderListPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */