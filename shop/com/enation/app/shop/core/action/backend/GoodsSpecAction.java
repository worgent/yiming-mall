 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.component.spec.service.ISpecManager;
 import com.enation.app.shop.component.spec.service.ISpecValueManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;





 public class GoodsSpecAction
   extends WWAction
 {
   private ISpecManager specManager;
   private IOrderManager orderManager;
   private ISpecValueManager specValueManager;
   private List specList;
   private Integer spec_id;
   private Integer value_id;
   private Map spec;
   private List valueList;

   public String execute()
   {
     this.specList = this.specManager.list();
     return "select";
   }

   public String getValues() {
     this.spec = this.specManager.get(this.spec_id);
     this.valueList = this.specValueManager.list(this.spec_id);
     return "values";
   }

   public String addOne() {
     this.spec = this.specValueManager.get(this.value_id);
     return "add_one";
   }

   public String closeSpec() {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String action = request.getParameter("action");
     if ("check-pro-in-order".equals(action)) {
       int productid = StringUtil.toInt(request.getParameter("productid"), true);
       boolean isinorder = this.orderManager.checkProInOrder(productid);
       if (isinorder) {
         showSuccessJson("有订单使用");
       } else {
         showErrorJson("无订单使用");
       }
     } else if ("check-goods-in-order".equals(action)) {
       int goodsid = StringUtil.toInt(request.getParameter("goodsid"), true);
       boolean isinorder = this.orderManager.checkGoodsInOrder(goodsid);
       if (isinorder) {
         showSuccessJson("有订单使用");
       } else {
         showErrorJson("无订单使用");
       }
     }
     return "json_message";
   }

   public String addAll()
   {
     return "add_all";
   }

   public ISpecManager getSpecManager() {
     return this.specManager;
   }

   public void setSpecManager(ISpecManager specManager) {
     this.specManager = specManager;
   }

   public List getSpecList() {
     return this.specList;
   }

   public void setSpecList(List specList) {
     this.specList = specList;
   }

   public ISpecValueManager getSpecValueManager() {
     return this.specValueManager;
   }

   public void setSpecValueManager(ISpecValueManager specValueManager) {
     this.specValueManager = specValueManager;
   }

   public Map getSpec() {
     return this.spec;
   }

   public void setSpec(Map spec) {
     this.spec = spec;
   }

   public List getValueList() {
     return this.valueList;
   }

   public void setValueList(List valueList) {
     this.valueList = valueList;
   }

   public Integer getSpec_id() {
     return this.spec_id;
   }

   public void setSpec_id(Integer specId) {
     this.spec_id = specId;
   }

   public Integer getValue_id() {
     return this.value_id;
   }

   public void setValue_id(Integer valueId) {
     this.value_id = valueId;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\GoodsSpecAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */