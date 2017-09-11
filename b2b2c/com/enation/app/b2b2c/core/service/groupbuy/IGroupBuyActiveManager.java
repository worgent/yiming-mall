package com.enation.app.b2b2c.core.service.groupbuy;

import com.enation.app.b2b2c.core.model.groupbuy.GroupBuyActive;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IGroupBuyActiveManager
{
  public abstract Page list(int paramInt1, int paramInt2);
  
  public abstract List<GroupBuyActive> listEnable();
  
  public abstract void add(GroupBuyActive paramGroupBuyActive);
  
  public abstract void update(GroupBuyActive paramGroupBuyActive);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
  
  public abstract void delete(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\groupbuy\IGroupBuyActiveManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */