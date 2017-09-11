 package com.enation.eop.sdk.widget;

 import com.enation.app.base.core.model.Member;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;










 public abstract class AbstractMemberWidget
   extends AbstractWidget
 {
   public boolean cacheAble()
   {
     return false;
   }


   private boolean showMenu = true;


   protected void showMenu(boolean show)
   {
     this.showMenu = show;
   }

   protected void setMenu(String menuName) {
     putData("menu", menuName);
   }

   public boolean getShowMenu() {
     return this.showMenu;
   }

   protected Member getCurrentMember() {
     Member member = UserServiceFactory.getUserService().getCurrentMember();
     return member;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\widget\AbstractMemberWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */