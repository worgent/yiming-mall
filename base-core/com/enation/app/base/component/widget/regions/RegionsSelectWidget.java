 package com.enation.app.base.component.widget.regions;

 import com.enation.app.base.core.service.IRegionsManager;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;














 @Component("regionsSelect")
 @Scope("prototype")
 public class RegionsSelectWidget
   extends AbstractWidget
 {
   private IRegionsManager regionsManager;

   protected void config(Map<String, String> params) {}

   public boolean cacheAble()
   {
     return false;
   }


   protected void display(Map<String, String> params)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     if ("showJson".equals(this.action)) {
       String regionid = request.getParameter("regionid");
       getChildren(Integer.valueOf(regionid));
     } else {
       List provinceList = this.regionsManager.listProvince();

       putData("provinceList", provinceList);
     }
   }

   private void getChildren(Integer regionid) {
     String json = this.regionsManager.getChildrenJson(regionid);
     showJson(json);
   }

   public IRegionsManager getRegionsManager() {
     return this.regionsManager;
   }

   public void setRegionsManager(IRegionsManager regionsManager) {
     this.regionsManager = regionsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\regions\RegionsSelectWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */