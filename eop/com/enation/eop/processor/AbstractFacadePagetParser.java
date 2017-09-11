 package com.enation.eop.processor;

 import com.enation.eop.processor.core.Request;












 public abstract class AbstractFacadePagetParser
   extends AbstractParser
 {
   protected FacadePage page;

   public AbstractFacadePagetParser(FacadePage page, Request request)
   {
     super(request);
     this.page = page;
   }

   public FacadePage getPage() {
     return this.page;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\AbstractFacadePagetParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */