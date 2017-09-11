package com.enation.app.base.core.service.solution;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface ISolutionInstaller
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void install(Integer paramInteger1, Integer paramInteger2, String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\ISolutionInstaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */