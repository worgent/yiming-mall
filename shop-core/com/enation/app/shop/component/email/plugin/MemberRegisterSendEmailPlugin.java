 package com.enation.app.shop.component.email.plugin;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.plugin.member.IMemberRegisterEvent;
 import com.enation.app.shop.core.service.IMemberPointManger;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.framework.jms.EmailModel;
 import com.enation.framework.jms.EmailProducer;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.EncryptionUtil1;
 import com.enation.framework.util.RequestUtil;
 import java.util.Map;
 import org.springframework.stereotype.Component;








 @Component
 public class MemberRegisterSendEmailPlugin
   extends AutoRegisterPlugin
   implements IMemberRegisterEvent
 {
   private EmailProducer mailMessageProducer;
   private IMemberPointManger memberPointManger;

   public void onRegister(Member member)
   {
     sendRegEmail(member);
   }

   private void sendRegEmail(Member member)
   {
     EopSite site = EopContext.getContext().getCurrentSite();

     String domain = RequestUtil.getDomain();

     String checkurl = domain + "/memberemailcheck.html?s=" + EncryptionUtil1.authcode(new StringBuilder().append(member.getMember_id()).append(",").append(member.getRegtime()).toString(), "ENCODE", "", 0);
     EmailModel emailModel = new EmailModel();
     emailModel.getData().put("username", member.getUname());
     emailModel.getData().put("checkurl", checkurl);
     emailModel.getData().put("sitename", site.getSitename());
     emailModel.getData().put("logo", site.getLogofile());
     emailModel.getData().put("domain", domain);

     if (this.memberPointManger.checkIsOpen("email_check")) {
       int point = this.memberPointManger.getItemPoint("email_check_num");
       int mp = this.memberPointManger.getItemPoint("email_check_num_mp");
       emailModel.getData().put("point", Integer.valueOf(point));
       emailModel.getData().put("mp", Integer.valueOf(mp));
     }

     emailModel.setTitle(member.getUname() + "您好，" + site.getSitename() + "会员注册成功!");
     emailModel.setEmail(member.getEmail());
     emailModel.setTemplate("reg_email_template.html");
     emailModel.setEmail_type("注册成功提示");
     this.mailMessageProducer.send(emailModel);
   }

   public EmailProducer getMailMessageProducer()
   {
     return this.mailMessageProducer;
   }

   public void setMailMessageProducer(EmailProducer mailMessageProducer) {
     this.mailMessageProducer = mailMessageProducer;
   }

   public IMemberPointManger getMemberPointManger() {
     return this.memberPointManger;
   }

   public void setMemberPointManger(IMemberPointManger memberPointManger) {
     this.memberPointManger = memberPointManger;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\email\plugin\MemberRegisterSendEmailPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */