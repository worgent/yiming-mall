 package com.enation.app.shop.core.tag.member;

 import com.enation.app.shop.component.orderreturns.service.IReturnsOrderManager;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;









 @Component
 @Scope("prototype")
 public class ReturnOrderListTag
   extends BaseFreeMarkerTag
 {
   private IReturnsOrderManager returnsOrderManager;
   private IOrderManager orderManager;
   private IGoodsManager goodsManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String page = request.getParameter("page");
     page = (page == null) || (page.equals("")) ? "1" : page;

     int pageSize = 10;
     Page returnOrderPage = this.returnsOrderManager.pageReturnOrder(Integer.valueOf(page).intValue(), pageSize);
     Long totalCount = Long.valueOf(returnOrderPage.getTotalCount());
     Map result = new HashMap();
     List returnOrderList = (List)returnOrderPage.getResult();
     returnOrderList = returnOrderList == null ? new ArrayList() : returnOrderList;
     result.put("totalCount", totalCount);
     result.put("pageSize", Integer.valueOf(pageSize));
     result.put("page", page);
     result.put("returnOrderList", returnOrderList);
     return result;
   }

   public IReturnsOrderManager getReturnsOrderManager() {
     return this.returnsOrderManager;
   }

   public void setReturnsOrderManager(IReturnsOrderManager returnsOrderManager) {
     this.returnsOrderManager = returnsOrderManager;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IGoodsManager getGoodsManager() {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\ReturnOrderListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */