 package com.enation.app.shop.component.point.plugin;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.plugin.member.IMemberEmailCheckEvent;
 import com.enation.app.shop.core.plugin.member.IMemberLoginEvent;
 import com.enation.app.shop.core.plugin.member.IMemberRegisterEvent;
 import com.enation.app.shop.core.service.IMemberPointManger;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.DateUtil;
 import java.util.Date;
 import org.springframework.stereotype.Component;










 @Component
 public class MemberPointPlugin
   extends AutoRegisterPlugin
   implements IMemberRegisterEvent, IMemberLoginEvent, IMemberEmailCheckEvent
 {
   private IDaoSupport baseDaoSupport;
   private IMemberPointManger memberPointManger;

   public void onRegister(Member member)
   {
     int memberid = member.getMember_id().intValue();





     if (this.memberPointManger.checkIsOpen("register")) {
       int point = this.memberPointManger.getItemPoint("register_num");
       int mp = this.memberPointManger.getItemPoint("register_num_mp");
       this.memberPointManger.add(memberid, point, "成功注册", null, mp);
     }
   }








   public void onEmailCheck(Member member)
   {
     if (this.memberPointManger.checkIsOpen("email_check")) {
       int point = this.memberPointManger.getItemPoint("email_check_num");
       int mp = this.memberPointManger.getItemPoint("email_check_num_mp");
       this.memberPointManger.add(member.getMember_id().intValue(), point, "完成邮箱验证", null, mp);
     }
   }



   public void onLogin(Member member, Long upLogintime)
   {
     if ((upLogintime == null) || (upLogintime.longValue() == 0L))
       upLogintime = member.getLastlogin();
     long ldate = upLogintime.longValue() * 1000L;
     Date date = new Date(ldate);
     Date today = new Date();






     if ((!DateUtil.toString(date, "yyyy-MM-dd").equals(DateUtil.toString(today, "yyyy-MM-dd"))) &&
       (this.memberPointManger.checkIsOpen("login"))) {
       int point = this.memberPointManger.getItemPoint("login_num");
       int mp = this.memberPointManger.getItemPoint("login_num_mp");
       this.memberPointManger.add(member.getMember_id().intValue(), point, DateUtil.toString(today, "yyyy年MM月dd日") + "登录", null, mp);
     }
   }




   private boolean isRepeatedIp(String ip, int parentid)
   {
     String sql = "select count(0) from member where parentid=? and registerip=?";
     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(parentid), ip });
     return count > 1;
   }



   public IMemberPointManger getMemberPointManger()
   {
     return this.memberPointManger;
   }

   public void setMemberPointManger(IMemberPointManger memberPointManger)
   {
     this.memberPointManger = memberPointManger;
   }

   public IDaoSupport getBaseDaoSupport()
   {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport baseDaoSupport)
   {
     this.baseDaoSupport = baseDaoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\point\plugin\MemberPointPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */