 package com.enation.framework.jms;

 import com.enation.eop.sdk.context.EopContext;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.util.StringUtil;
 import org.apache.log4j.Logger;








 public class EopConsumer
 {
   protected final Logger logger = Logger.getLogger(getClass());





   public void execute(IEopJmsMessage message)
   {
     try
     {
       EopContext context = message.getEopContext();
       EopContext.setContext(context);

       Object data = message.getData();
       String processorid = message.getProcessorBeanId();

       IJmsProcessor processor = (IJmsProcessor)SpringContextHolder.getBean(processorid);
       processor.process(data);



       if ((message instanceof ITaskView)) {
         ITaskView task = (ITaskView)message;
         task.setState(1);
         TaskContainer.pushTask(task);
       }
     }
     catch (Exception e) {
       this.logger.error("Jms消息执行出错", e);

       if ((message instanceof ITaskView))
       {
         ITaskView task = (ITaskView)message;
         task.setState(2);
         task.setErrorMessage(StringUtil.getStackTrace(e));
         TaskContainer.pushTask(task);
       }
     }
     finally
     {
       EopContext.remove();
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\jms\EopConsumer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */