package com.enation.app.base.core.plugin.user;

import com.enation.eop.resource.model.AdminUser;

public abstract interface IAdminUserLoginEvent
{
  public abstract void onLogin(AdminUser paramAdminUser);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\plugin\\user\IAdminUserLoginEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */