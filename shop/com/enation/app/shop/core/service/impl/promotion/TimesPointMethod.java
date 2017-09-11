 package com.enation.app.shop.core.service.impl.promotion;

 import com.enation.app.shop.core.model.Promotion;
 import com.enation.app.shop.core.service.promotion.IPromotionMethod;
 import com.enation.app.shop.core.service.promotion.ITimesPointBehavior;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import javax.servlet.http.HttpServletRequest;






 public class TimesPointMethod
   implements IPromotionMethod, ITimesPointBehavior
 {
   public String getInputHtml(Integer pmtid, String solution)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.putData("multiple", solution);
     return freeMarkerPaser.proessPageContent();
   }


   public String getName()
   {
     return "timesPoint";
   }

   public String onPromotionSave(Integer pmtid)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String multiple = request.getParameter("multiple");
     return multiple == null ? "" : multiple;
   }

   public Integer countPoint(Promotion promotion, Integer point)
   {
     String solution = promotion.getPmt_solution();
     Integer multiple = Integer.valueOf(solution);

     return Integer.valueOf(point.intValue() * multiple.intValue());
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\promotion\TimesPointMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */