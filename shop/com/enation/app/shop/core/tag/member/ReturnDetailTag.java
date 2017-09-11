 package com.enation.app.shop.core.tag.member;

 import com.enation.app.shop.component.orderreturns.service.IReturnsOrderManager;
 import com.enation.app.shop.core.model.Goods;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.ReturnsOrder;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;







 @Component
 @Scope("prototype")
 public class ReturnDetailTag
   extends BaseFreeMarkerTag
 {
   private IReturnsOrderManager returnsOrderManager;
   private IOrderManager orderManager;
   private IGoodsManager goodsManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String returnOrderSn = request.getParameter("returnOrderSn");
     if ((returnOrderSn == null) || ("".equals(returnOrderSn))) {
       throw new TemplateModelException("必须传递orderid参数");
     }
     Order order = this.orderManager.get(returnOrderSn);
     String goodsSn = this.returnsOrderManager.getSnByOrderSn(order.getSn());
     String[] goodSn = null;
     List goodIdS = new ArrayList();
     if ((goodsSn != null) && (!goodsSn.equals(""))) {
       goodSn = StringUtils.split(goodsSn, ",");
       for (int j = 0; j < goodSn.length; j++) {
         if (goodSn[j].indexOf("-") != -1) {
           goodSn[j] = goodSn[j].substring(0, goodSn[j].indexOf("-"));
         }
       }
       for (int i = 0; i < goodSn.length; i++) {
         goodIdS.add(this.goodsManager.getGoodBySn(goodSn[i]).getGoods_id());
       }
     }
     Map result = new HashMap();
     List orderItemsList = this.orderManager.listGoodsItems(order.getOrder_id());
     ReturnsOrder tempReturnsOrder = this.returnsOrderManager.getByOrderSn(order.getSn());
     result.put("orders", order);
     result.put("orderItemsList", orderItemsList);
     result.put("goodIdS", goodIdS);
     result.put("returnOrder", tempReturnsOrder);
     return result;
   }

   public IReturnsOrderManager getReturnsOrderManager() { return this.returnsOrderManager; }

   public void setReturnsOrderManager(IReturnsOrderManager returnsOrderManager) {
     this.returnsOrderManager = returnsOrderManager;
   }

   public IOrderManager getOrderManager() { return this.orderManager; }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IGoodsManager getGoodsManager() { return this.goodsManager; }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\ReturnDetailTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */