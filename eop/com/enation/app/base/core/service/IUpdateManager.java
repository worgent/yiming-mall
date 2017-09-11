package com.enation.app.base.core.service;

import com.enation.app.base.core.model.VersionState;

public abstract interface IUpdateManager
{
  public abstract VersionState checkNewVersion();
  
  public abstract void update();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\IUpdateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */