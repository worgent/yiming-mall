 package com.enation.app.shop.core.action.api;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.model.SmsMessage;
 import com.enation.app.base.core.service.ISmsManager;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.jms.EmailModel;
 import com.enation.framework.jms.EmailProducer;
 import java.util.Random;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;








 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("findPassword")
 public class FindPasswordAction
   extends WWAction
 {
   private ISmsManager smsManager;
   private IMemberManager memberManager;
   private EmailProducer mailMessageProducer;
   private String mobileNum;
   private String password;

   protected String createRandom()
   {
     Random random = new Random();
     StringBuffer pwd = new StringBuffer();
     for (int i = 0; i < 6; i++) {
       pwd.append(random.nextInt(9));
     }

     return pwd.toString();
   }










   public String sendSmsCode()
   {
     Member member = null;
     int find_type = 0;
     if (this.mobileNum.contains("@")) {
       member = this.memberManager.getMemberByEmail(this.mobileNum);
       find_type = 1;
     }
     else {
       member = this.memberManager.getMemberByUname(this.mobileNum); }
     if (member == null) {
       showErrorJson("没有找到用户");
     } else {
       String code = createRandom();
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       request.getSession().setAttribute("smscode", code);
       request.getSession().setAttribute("smsnum", member.getMember_id());

       if (find_type == 0) {
         SmsMessage message = new SmsMessage();
         message.setUser_id(member.getMember_id().intValue());
         message.setTel(this.mobileNum);
         message.setMessage_info("红酒星空用户，您的验证码是：" + code);




         showSuccessJson("短信发送成功");
       } else {
         EmailModel emailModel = new EmailModel();
         emailModel.setEmail(this.mobileNum);
         emailModel.setTitle("红酒星空找回密码");
         emailModel.setContent("红酒星空用户，您的验证码是：" + code);
         emailModel.setEmail_type("找回密码");
         try {
           this.mailMessageProducer.send(emailModel);

           showSuccessJson("验证码已发送至预留邮箱");
         } catch (Exception e) {
           showErrorJson("邮件发送失败");
         }
       }
     }
     return "json_message";
   }










   public String checkSmsCode()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String code = (String)request.getSession().getAttribute("smscode");
     if (code.equals(this.mobileNum)) {
       request.getSession().setAttribute("smscode", "999");
       showSuccessJson("验证通过");
     } else {
       showErrorJson("验证失败");
     }
     return "json_message";
   }










   public String resetPassword()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String code = (String)request.getSession().getAttribute("smscode");
     Integer memberid = (Integer)request.getSession().getAttribute("smsnum");
     if ((memberid != null) && ("999".equals(code))) {
       try {
         this.memberManager.updatePassword(memberid, this.mobileNum);
         showSuccessJson("新密码设置成功");
       } catch (Exception e) {
         showErrorJson("设置密码出错");
       }

     } else {
       showErrorJson("认证超时，请重新验证");
     }
     return "json_message";
   }

   public String getMobileNum() {
     return this.mobileNum;
   }

   public void setMobileNum(String mobileNum) {
     this.mobileNum = mobileNum;
   }

   public ISmsManager getSmsManager() {
     return this.smsManager;
   }

   public void setSmsManager(ISmsManager smsManager) {
     this.smsManager = smsManager;
   }

   public IMemberManager getMemberManager() {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }

   public String getPassword() {
     return this.password;
   }

   public void setPassword(String password) {
     this.password = password;
   }

   public EmailProducer getMailMessageProducer() {
     return this.mailMessageProducer;
   }

   public void setMailMessageProducer(EmailProducer mailMessageProducer) {
     this.mailMessageProducer = mailMessageProducer;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\FindPasswordAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */