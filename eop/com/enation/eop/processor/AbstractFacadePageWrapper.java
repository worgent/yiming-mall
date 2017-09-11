 package com.enation.eop.processor;

 import com.enation.eop.processor.core.Request;








 public abstract class AbstractFacadePageWrapper
   extends AbstractWrapper
 {
   protected FacadePage page;

   public AbstractFacadePageWrapper(FacadePage page, Request request)
   {
     super(request);
     this.page = page;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\AbstractFacadePageWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */