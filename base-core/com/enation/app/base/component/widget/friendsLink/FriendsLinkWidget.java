 package com.enation.app.base.component.widget.friendsLink;

 import com.enation.app.base.component.widget.nav.Nav;
 import com.enation.app.base.core.service.IFriendsLinkManager;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;













 @Component("friends_link")
 @Scope("prototype")
 public class FriendsLinkWidget
   extends AbstractWidget
 {
   private IFriendsLinkManager friendsLinkManager;

   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     setPageName("friendsLink");

     List listLink = this.friendsLinkManager.listLink();
     putData("listLink", listLink);

     Nav nav = new Nav();
     nav.setTitle("友情链接 ");
     putNav(nav);
   }

   public IFriendsLinkManager getFriendsLinkManager() {
     return this.friendsLinkManager;
   }

   public void setFriendsLinkManager(IFriendsLinkManager friendsLinkManager) {
     this.friendsLinkManager = friendsLinkManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\friendsLink\FriendsLinkWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */