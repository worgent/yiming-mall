package com.enation.app.shop.core.plugin.promotion;

import com.enation.framework.plugin.IPlugin;

public abstract interface IPromotionPlugin
  extends IPlugin
{
  public abstract String[] getConditions();
  
  public abstract String getMethods();
  
  public abstract String getId();
  
  public abstract String getType();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\promotion\IPromotionPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */