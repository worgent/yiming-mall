 package com.enation.app.b2b2c;

 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.App;
 import org.apache.log4j.Logger;
 import org.springframework.stereotype.Component;







 @Component("b2b2c")
 public class B2b2cApp
   extends App
 {
   private ICartManager cartManager;

   public String getId()
   {
     return "b2b2c";
   }

   public String getName()
   {
     return "b2b2c应用";
   }

   public String getNameSpace()
   {
     return "/b2b2c";
   }

   public void install()
   {
     doInstall("file:com/enation/app/b2b2c/b2b2c.xml");
   }




   public void saasInstall() {}



   public void sessionDestroyed(String sessionid, EopSite site)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("clean cart...");
     }


     if (site != null) {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug("site get from session is userid[" + site.getUserid() + "]-siteid[" + site.getId() + "]");
       }
       this.cartManager.clean(sessionid, site.getUserid(), site.getId());
     }
     else if (this.logger.isDebugEnabled()) {
       this.logger.debug("site get from session is null");
     }
   }


   public ICartManager getCartManager()
   {
     return this.cartManager;
   }

   public void setCartManager(ICartManager cartManager) {
     this.cartManager = cartManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\B2b2cApp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */