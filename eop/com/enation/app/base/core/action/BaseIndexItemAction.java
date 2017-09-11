 package com.enation.app.base.core.action;

 import com.enation.app.base.core.service.IAccessRecorder;
 import com.enation.eop.resource.ISiteManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.framework.action.WWAction;
 import java.util.GregorianCalendar;
 import java.util.Map;




 public class BaseIndexItemAction
   extends WWAction
 {
   private ISiteManager siteManager;
   private IAccessRecorder accessRecorder;
   private Map accessMap;
   private EopSite site;
   private int canget;

   public String base()
   {
     this.site = EopContext.getContext().getCurrentSite();
     return "base";
   }

   public String access() {
     this.accessMap = this.accessRecorder.census();
     return "access";
   }

   public String point() {
     this.site = EopContext.getContext().getCurrentSite();
     long lastgetpoint = this.site.getLastgetpoint();
     GregorianCalendar cal = new GregorianCalendar();
     cal.set(5, 1);
     long month_begin = cal.getTimeInMillis() / 1000L;
     if (lastgetpoint - month_begin > 0L) {
       this.canget = 1;
     } else {
       this.canget = 0;
     }
     return "point";
   }

   public ISiteManager getSiteManager() {
     return this.siteManager;
   }

   public void setSiteManager(ISiteManager siteManager) {
     this.siteManager = siteManager;
   }

   public EopSite getSite() {
     return this.site;
   }

   public void setSite(EopSite site) {
     this.site = site;
   }

   public IAccessRecorder getAccessRecorder() {
     return this.accessRecorder;
   }

   public void setAccessRecorder(IAccessRecorder accessRecorder) {
     this.accessRecorder = accessRecorder;
   }

   public Map getAccessMap() {
     return this.accessMap;
   }

   public void setAccessMap(Map accessMap) {
     this.accessMap = accessMap;
   }

   public int getCanget() {
     return this.canget;
   }

   public void setCanget(int canget) {
     this.canget = canget;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\BaseIndexItemAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */