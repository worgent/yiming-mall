 package com.enation.app.shop.core.action.api;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.jms.EmailModel;
 import com.enation.framework.jms.EmailProducer;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.EncryptionUtil1;
 import com.enation.framework.util.RequestUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.Map;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.stereotype.Component;















 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("findPasswordbyEmail")
 public class MemberFindPasswordAction
   extends WWAction
 {
   private IMemberManager memberManager;
   private EmailProducer mailMessageProducer;
   private String email;
   private Integer memberid;
   private String password;
   private String conpasswd;
   private String s;
   private Integer choose;

   public String find()
   {
     EopSite site = EopContext.getContext().getCurrentSite();



     Member member = new Member();
     if (this.choose.intValue() == 0) {
       if ((this.email == null) || (!StringUtil.validEmail(this.email))) {
         showErrorJson("请输入正确的邮箱地址");
         return "json_message";
       }
       member = this.memberManager.getMemberByEmail(this.email);
     } else {
       member = this.memberManager.getMemberByUname(this.email);
     }
     if (member == null) {
       showErrorJson("[" + this.email + "]不存在!");
       return "json_message";
     }
     String domain = RequestUtil.getDomain();
     String initCode = member.getMember_id() + "," + member.getRegtime();
     String edit_url = domain + "/findPassword.html?s=" + EncryptionUtil1.authcode(initCode, "ENCODE", "", 0);


     EmailModel emailModel = new EmailModel();
     emailModel.getData().put("logo", site.getLogofile());
     emailModel.getData().put("sitename", site.getSitename());
     emailModel.getData().put("username", member.getUname());
     emailModel.getData().put("checkurl", edit_url);
     emailModel.setTitle("找回您的登录密码--" + site.getSitename());
     emailModel.setEmail(member.getEmail());
     emailModel.setTemplate("findpass_email_template.html");
     emailModel.setEmail_type("找回密码");
     this.mailMessageProducer.send(emailModel);

     this.memberManager.updateFindCode(member.getMember_id(), DateUtil.getDateline() + "");
     showSuccessJson("请登录" + member.getEmail() + "查收邮件并完成密码修改。");
     return "json_message";
   }






   public String modify()
   {
     if ((this.email == null) || (!StringUtil.validEmail(this.email))) {
       showErrorJson("邮箱错误,请重试");
       return "json_message";
     }
     if (this.s == null) {
       showErrorJson("非法链接地址,请重新找回");
       return "json_message";
     }
     String str = EncryptionUtil1.authcode(this.s, "DECODE", "", 0);
     String[] array = StringUtils.split(str, ",");
     if (array.length != 2) {
       showErrorJson("验证字串不正确,请重新找回");
       return "json_message";
     }
     int memberid = Integer.valueOf(array[0]).intValue();
     long regtime = Long.valueOf(array[1]).longValue();

     Member member = this.memberManager.get(Integer.valueOf(memberid));
     if ((member == null) || (member.getRegtime().longValue() != regtime)) {
       showErrorJson("验证字串不正确,请重新找回");
       return "json_message";
     }
     if ((member.getFind_code() == null) || ("".equals(member.getFind_code())) || (member.getFind_code().length() != 10)) {
       showErrorJson("地址已经过期,请重新找回");
       return "json_message";
     }
     int time = Integer.parseInt(member.getFind_code()) + 3600;
     if (DateUtil.getDateline() > time) {
       showErrorJson("地址已经过期,请重新找回");
       return "json_message";
     }
     if (!this.password.equals(this.conpasswd)) {
       showErrorJson("密码不同");
       return "json_message";
     }
     this.memberManager.updatePassword(Integer.valueOf(memberid), this.password);
     this.memberManager.updateFindCode(Integer.valueOf(memberid), "");
     this.memberManager.login(member.getUname(), this.password);
     showSuccessJson("修改密码成功");
     return "json_message";
   }


   public IMemberManager getMemberManager()
   {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) { this.memberManager = memberManager; }

   public EmailProducer getMailMessageProducer() {
     return this.mailMessageProducer;
   }

   public void setMailMessageProducer(EmailProducer mailMessageProducer) { this.mailMessageProducer = mailMessageProducer; }

   public String getEmail() {
     return this.email;
   }

   public void setEmail(String email) { this.email = email; }

   public Integer getMemberid() {
     return this.memberid;
   }

   public void setMemberid(Integer memberid) { this.memberid = memberid; }

   public String getPassword() {
     return this.password;
   }

   public void setPassword(String password) { this.password = password; }

   public String getConpasswd() {
     return this.conpasswd;
   }

   public void setConpasswd(String conpasswd) { this.conpasswd = conpasswd; }

   public String getS() {
     return this.s;
   }

   public void setS(String s) { this.s = s; }

   public Integer getChoose() {
     return this.choose;
   }

   public void setChoose(Integer choose) { this.choose = choose; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\MemberFindPasswordAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */