 package com.enation.app.shop.core.service.impl.promotion;

 import com.enation.app.shop.core.model.Promotion;
 import com.enation.app.shop.core.service.promotion.IPromotionMethod;
 import com.enation.app.shop.core.service.promotion.IReducePriceBehavior;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.CurrencyUtil;
 import javax.servlet.http.HttpServletRequest;









 public class ReducePriceMethod
   implements IPromotionMethod, IReducePriceBehavior
 {
   public String getInputHtml(Integer pmtid, String solution)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.putData("lessMoney", solution);
     return freeMarkerPaser.proessPageContent();
   }

   public String getName()
   {
     return "reducePrice";
   }

   public String onPromotionSave(Integer pmtid)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String reducePrice = request.getParameter("lessMoney");
     return reducePrice == null ? "" : reducePrice;
   }

   public Double reducedPrice(Promotion pmt, Double price)
   {
     String solution = pmt.getPmt_solution();
     Double lessMoney = Double.valueOf(solution);
     return Double.valueOf(CurrencyUtil.sub(price.doubleValue(), lessMoney.doubleValue()));
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\promotion\ReducePriceMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */