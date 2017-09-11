 package com.enation.framework.jms;



 public class EmailProducer
 {
   private EopProducer eopProducer;


   public void send(EmailModel emailModel)
   {
     EopJmsMessage jmsMessage = new EopJmsMessage();
     jmsMessage.setData(emailModel);
     jmsMessage.setProcessorBeanId("emailProcessor");
     this.eopProducer.send(jmsMessage);
   }

   public EopProducer getEopProducer()
   {
     return this.eopProducer;
   }

   public void setEopProducer(EopProducer eopProducer) { this.eopProducer = eopProducer; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\jms\EmailProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */