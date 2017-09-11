package com.enation.framework.jms;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IJmsProcessor
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void process(Object paramObject);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\jms\IJmsProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */