package com.enation.app.base.core.service.solution;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;

public abstract interface IInstaller
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void install(String paramString, Node paramNode);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\IInstaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */