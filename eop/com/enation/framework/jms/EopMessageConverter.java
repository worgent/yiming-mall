 package com.enation.framework.jms;

 import java.util.HashMap;
 import java.util.Map;
 import javax.jms.JMSException;
 import javax.jms.Message;
 import javax.jms.ObjectMessage;
 import javax.jms.Session;
 import org.apache.activemq.command.ActiveMQObjectMessage;
 import org.springframework.jms.support.converter.MessageConversionException;
 import org.springframework.jms.support.converter.MessageConverter;









 public class EopMessageConverter
   implements MessageConverter
 {
   public Object fromMessage(Message msg)
     throws JMSException, MessageConversionException
   {
     if ((msg instanceof ObjectMessage)) {
       return ((Map)((ObjectMessage)msg).getObjectProperty("Map")).get("eop_message");
     }
     throw new JMSException("Msg:[" + msg + "] is not Map");
   }


   public Message toMessage(Object obj, Session session)
     throws JMSException, MessageConversionException
   {
     if ((obj instanceof IEopJmsMessage)) {
       ActiveMQObjectMessage objMsg = (ActiveMQObjectMessage)session.createObjectMessage();
       Map<String, IEopJmsMessage> map = new HashMap();
       map.put("eop_message", (IEopJmsMessage)obj);
       objMsg.setObjectProperty("Map", map);
       return objMsg;
     }
     throw new JMSException("Object:[" + obj + "] is not Member");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\jms\EopMessageConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */