package com.enation.app.shop.component.bonus;

import com.enation.framework.component.IComponent;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BonusComponent
  implements IComponent
{
  private Logger logger = Logger.getLogger(this.getClass());

  public void install() {
    logger.debug("BonusComponent install");
  }
  
  public void unInstall() {
    logger.debug("BonusComponent unInstall");

  }
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\BonusComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */