 package com.enation.framework.jms;

 import com.enation.app.base.core.model.Smtp;
 import com.enation.app.base.core.service.ISmtpManager;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.eop.sdk.utils.FreeMarkerUtil;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.DateUtil;
 import com.sun.xml.messaging.saaj.util.ByteOutputStream;
 import freemarker.template.Configuration;
 import freemarker.template.Template;
 import java.io.File;
 import java.io.OutputStreamWriter;
 import java.io.Writer;
 import javax.mail.internet.MimeMessage;
 import org.apache.log4j.Logger;
 import org.springframework.mail.javamail.JavaMailSender;
 import org.springframework.mail.javamail.JavaMailSenderImpl;
 import org.springframework.mail.javamail.MimeMessageHelper;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;








 public class EmailProcessor
   extends BaseSupport
   implements IJmsProcessor
 {
   private JavaMailSender mailSender;
   private ISmtpManager smtpManager;

   @Transactional(propagation=Propagation.REQUIRED)
   public void process(Object data)
   {
     EmailModel emailModel = (EmailModel)data;
     int emailid = 0;
     try
     {
       Smtp smtp = this.smtpManager.getCurrentSmtp();
       JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl)this.mailSender;
       javaMailSender.setHost(smtp.getHost());
       javaMailSender.setUsername(smtp.getUsername());
       javaMailSender.setPassword(smtp.getPassword());


       MimeMessage message = this.mailSender.createMimeMessage();
       MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

       helper.setSubject(emailModel.getTitle());
       helper.setTo(emailModel.getEmail());
       helper.setFrom(smtp.getMail_from());

       Configuration cfg = FreeMarkerUtil.getCfg();

       String pageFolder = EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes/";
       cfg.setDirectoryForTemplateLoading(new File(pageFolder));


       Template temp = cfg.getTemplate(emailModel.getTemplate());
       ByteOutputStream stream = new ByteOutputStream();

       Writer out = new OutputStreamWriter(stream);
       temp.process(emailModel.getData(), out);

       out.flush();
       String html = stream.toString();
       emailModel.setContent(html);

       helper.setText(html, true);



       emailid = addEmailList(emailModel);


       javaMailSender.send(message);

       this.smtpManager.sendOneMail(smtp);

     }
     catch (Exception e)
     {

       if (emailid != 0) {
         this.baseDaoSupport.execute("update email_list set is_success=0,error_num=error_num+1 where email_id=?", new Object[] { Integer.valueOf(emailid) });
       }

       this.logger.error("发送邮件出错", e);
     }
   }






   @Transactional(propagation=Propagation.REQUIRED)
   private int addEmailList(EmailModel emailModel)
   {
     emailModel.setIs_success(1);
     emailModel.setLast_send(DateUtil.getDateline());
     this.baseDaoSupport.insert("email_list", emailModel);
     return this.baseDaoSupport.getLastId("email_list");
   }

   public JavaMailSender getMailSender() {
     return this.mailSender;
   }

   public void setMailSender(JavaMailSender mailSender) { this.mailSender = mailSender; }

   public ISmtpManager getSmtpManager() {
     return this.smtpManager;
   }

   public void setSmtpManager(ISmtpManager smtpManager) { this.smtpManager = smtpManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\jms\EmailProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */