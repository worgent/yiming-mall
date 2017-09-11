 package com.enation.framework.jms;

 import com.enation.eop.sdk.context.EopContext;










 public class EopJmsMessage
   implements IEopJmsMessage
 {
   private Object data;
   private String beanid;
   private EopContext context;

   public EopJmsMessage()
   {
     this.context = EopContext.getContext();
   }

   public Object getData()
   {
     return this.data;
   }

   public String getProcessorBeanId()
   {
     return this.beanid;
   }

   public void setData(Object _data) {
     this.data = _data;
   }

   public void setProcessorBeanId(String _beanid) {
     this.beanid = _beanid;
   }



   public EopContext getEopContext()
   {
     return this.context;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\jms\EopJmsMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */