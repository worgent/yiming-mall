package com.enation.app.base.core.service;

import com.enation.app.base.core.model.FriendsLink;
import java.util.List;

public abstract interface IFriendsLinkManager
{
  public abstract FriendsLink get(int paramInt);
  
  public abstract List listLink();
  
  public abstract void add(FriendsLink paramFriendsLink);
  
  public abstract void delete(String paramString);
  
  public abstract void update(FriendsLink paramFriendsLink);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\IFriendsLinkManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */