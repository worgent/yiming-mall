 package com.enation.framework.jms;

 import javax.jms.Queue;
 import org.springframework.jms.core.JmsTemplate;


 public class EopProducer
 {
   private JmsTemplate template;
   private Queue destination;

   public void setTemplate(JmsTemplate template)
   {
     this.template = template;
   }

   public void setDestination(Queue destination) {
     this.destination = destination;
   }

   public void send(IEopJmsMessage eopJmsMessage)
   {
     if ((eopJmsMessage instanceof ITaskView)) {
       TaskContainer.pushTask((ITaskView)eopJmsMessage);
     }

     this.template.convertAndSend(this.destination, eopJmsMessage);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\jms\EopProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */