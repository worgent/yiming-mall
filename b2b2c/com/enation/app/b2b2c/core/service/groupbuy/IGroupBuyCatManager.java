package com.enation.app.b2b2c.core.service.groupbuy;

import com.enation.app.b2b2c.core.model.groupbuy.GroupBuyCat;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IGroupBuyCatManager
{
  public abstract Page list(int paramInt1, int paramInt2);
  
  public abstract List<GroupBuyCat> listAll();
  
  public abstract GroupBuyCat get(int paramInt);
  
  public abstract void add(GroupBuyCat paramGroupBuyCat);
  
  public abstract void update(GroupBuyCat paramGroupBuyCat);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\groupbuy\IGroupBuyCatManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */