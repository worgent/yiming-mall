 package com.enation.app.cms.component.widget;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.cms.core.service.IDataCatManager;
 import com.enation.app.cms.core.service.IDataManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;










 @Component("staticDataDetail")
 @Scope("prototype")
 public class StaticDataDetailWidget
   extends AbstractWidget
 {
   private IDataManager dataManager;
   private IDataCatManager dataCatManager;

   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     String id = (String)params.get("id");
     String catid = (String)params.get("catid");
     String login = (String)params.get("login");

     if ("1".equals(login)) {
       Member member = UserServiceFactory.getUserService().getCurrentMember();
       if (member == null) {
         putData("isLogin", Boolean.valueOf(false));
       } else {
         putData("isLogin", Boolean.valueOf(true));
         putData("member", member);
       }
     }
     Map data = this.dataManager.get(Integer.valueOf(id), Integer.valueOf(catid), true);
     putData("data", data);
   }














   public IDataManager getDataManager()
   {
     return this.dataManager;
   }

   public void setDataManager(IDataManager dataManager) {
     this.dataManager = dataManager;
   }

   public IDataCatManager getDataCatManager() {
     return this.dataCatManager;
   }

   public void setDataCatManager(IDataCatManager dataCatManager) {
     this.dataCatManager = dataCatManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\widget\StaticDataDetailWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */