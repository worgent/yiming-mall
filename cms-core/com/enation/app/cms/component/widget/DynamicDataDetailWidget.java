 package com.enation.app.cms.component.widget;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.cms.core.model.DataCat;
 import com.enation.app.cms.core.service.IDataCatManager;
 import com.enation.app.cms.core.service.IDataManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;
















 @Component("dynamicDataDetail")
 @Scope("prototype")
 public class DynamicDataDetailWidget
   extends RequestParamWidget
 {
   private Integer catid;
   private Integer articleid;

   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     Integer[] ids = parseId();
     this.articleid = ids[0];
     this.catid = ids[1];

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

     Map data = this.dataManager.get(this.articleid, this.catid, true);
     putData("catid", this.catid);
     putData("data", data);








     String shownext = (String)params.get("shownext");
     if ("yes".equals(shownext)) {
       int nextid = this.dataManager.getNextId(this.articleid, this.catid);
       int pevid = this.dataManager.getPrevId(this.articleid, this.catid);
       putData("nextid", Integer.valueOf(nextid));
       putData("pevid", Integer.valueOf(pevid));
     } else {
       putData("nextid", Integer.valueOf(0));
       putData("pevid", Integer.valueOf(0));
     }







     List<DataCat> parents = this.dataCatManager.getParents(this.catid);
     DataCat cat = (DataCat)parents.get(parents.size() - 1);

     if ((data.get("page_title") != null) && (!data.get("page_title").equals(""))) {
       putData("pagetitle", data.get("page_title"));
     }
     if ((data.get("page_keywords") != null) && (!data.get("page_keywords").equals(""))) {
       putData("keywords", data.get("page_keywords"));
     }
     if ((data.get("page_description") != null) && (!data.get("page_description").equals(""))) {
       putData("description", data.get("page_description"));
     }
     StringBuffer navBar = new StringBuffer();
     navBar.append("<a href='index.html'>首页</a>");
     for (DataCat c : parents) {
       navBar.append("> <a href='" + c.getUrl() + "'>" + c.getName() + "</a>");
     }

     putData("navbar", navBar.toString());
   }

   public void update(Map<String, String> params)
   {
     Integer[] ids = parseId();
     Integer articleid = ids[0];
     Integer catid = ids[1];
     this.dataManager.updateHit(articleid, catid);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\widget\DynamicDataDetailWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */