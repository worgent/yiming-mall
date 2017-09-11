 package com.enation.app.base.core.action;

 import com.enation.app.base.core.service.IWidgetCacheManager;
 import com.enation.framework.action.WWAction;










 public class WidgetCacheAction
   extends WWAction
 {
   private IWidgetCacheManager widgetCacheManager;

   public String open()
   {
     try
     {
       this.widgetCacheManager.open();
       this.json = "{result:1}";
     } catch (RuntimeException e) {
       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
     }
     return "json_message";
   }




   public String close()
   {
     try
     {
       this.widgetCacheManager.close();
       this.json = "{result:1}";
     } catch (RuntimeException e) {
       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
     }
     return "json_message";
   }

   public String getState()
   {
     try {
       boolean isOpen = this.widgetCacheManager.isOpen();
       String state = isOpen ? "open" : "close";
       this.json = ("{result:1,state:'" + state + "'}");
     } catch (RuntimeException e) {
       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
     }
     return "json_message";
   }

   public IWidgetCacheManager getWidgetCacheManager()
   {
     return this.widgetCacheManager;
   }

   public void setWidgetCacheManager(IWidgetCacheManager widgetCacheManager)
   {
     this.widgetCacheManager = widgetCacheManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\WidgetCacheAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */