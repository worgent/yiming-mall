 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.service.IOrderPrintManager;
 import com.enation.framework.action.WWAction;
 import java.util.HashMap;
 import java.util.Map;
 import net.sf.json.JSONObject;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;











 @Component
 @Scope("prototype")
 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Action("orderPrint")
 @Results({})
 public class OrderPrintAction
   extends WWAction
 {
   private IOrderPrintManager orderPrintManager;
   private Integer[] order_id;
   private String[] expressno;

   public String ship()
   {
     try
     {
       String is_ship = this.orderPrintManager.ship(this.order_id);
       if (is_ship.equals("true")) {
         showSuccessJson("发货成功");
       } else {
         showErrorJson(is_ship);
       }
     } catch (Exception e) {
       e.printStackTrace();
       showErrorJson(e.getMessage());
       this.logger.error("发货出错", e);
     }
     return "json_message";
   }






   public String saveShipNo()
   {
     try
     {
       this.orderPrintManager.saveShopNos(this.order_id, this.expressno);
       showSuccessJson("保存发货单号成功");
     } catch (Exception e) {
       showErrorJson(e.getMessage());
       this.logger.error("保存发货单号出错", e);
     }
     return "json_message";
   }









   public String expressScript()
   {
     String script = this.orderPrintManager.getExpressScript(this.order_id);
     if ((script.equals("快递单选择配送方式不同")) || (script.equals("请添加配送方式")) || (script.equals("没有此快递单模板请添加")) || (script.equals("请选择默认发货点"))) {
       showErrorJson(script);
     } else {
       Map map = new HashMap();
       map.put("script", script);
       map.put("result", Integer.valueOf(1));
       this.json = JSONObject.fromObject(map).toString();
     }
     return "json_message";
   }






   public String shipScript()
   {
     String script = this.orderPrintManager.getShipScript(this.order_id);
     this.json = script;
     return "json_message";
   }

   public IOrderPrintManager getOrderPrintManager() {
     return this.orderPrintManager;
   }

   public void setOrderPrintManager(IOrderPrintManager orderPrintManager) {
     this.orderPrintManager = orderPrintManager;
   }

   public Integer[] getOrder_id() {
     return this.order_id;
   }

   public void setOrder_id(Integer[] order_id) {
     this.order_id = order_id;
   }

   public String[] getExpressno() {
     return this.expressno;
   }

   public void setExpressno(String[] expressno) {
     this.expressno = expressno;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\OrderPrintAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */