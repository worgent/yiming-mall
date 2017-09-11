 package com.enation.app.shop.core.service.impl.promotion;

 import com.enation.app.shop.core.service.promotion.IPromotionMethod;
 import com.enation.app.shop.core.service.promotion.IReduceFreightBehavior;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import javax.servlet.http.HttpServletRequest;






 public class FreeFreightMethod
   implements IPromotionMethod, IReduceFreightBehavior
 {
   public String getInputHtml(Integer pmtid, String solution)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.putData("free", solution);
     return freeMarkerPaser.proessPageContent();
   }


   public String getName()
   {
     return "free";
   }


   public String onPromotionSave(Integer pmtid)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String free = request.getParameter("free");
     return free == null ? "" : free;
   }


   public Double reducedPrice(Double freight)
   {
     return Double.valueOf(0.0D);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\promotion\FreeFreightMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */