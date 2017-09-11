package com.enation.framework.jms;

import com.enation.eop.sdk.context.EopContext;

public abstract interface IEopJmsMessage
{
  public abstract Object getData();
  
  public abstract String getProcessorBeanId();
  
  public abstract EopContext getEopContext();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\jms\IEopJmsMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */