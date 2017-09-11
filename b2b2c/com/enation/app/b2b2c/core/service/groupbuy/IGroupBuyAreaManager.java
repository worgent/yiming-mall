package com.enation.app.b2b2c.core.service.groupbuy;

import com.enation.app.b2b2c.core.model.groupbuy.GroupBuyArea;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IGroupBuyAreaManager
{
  public abstract Page list(int paramInt1, int paramInt2);
  
  public abstract List<GroupBuyArea> listAll();
  
  public abstract GroupBuyArea get(int paramInt);
  
  public abstract void add(GroupBuyArea paramGroupBuyArea);
  
  public abstract void update(GroupBuyArea paramGroupBuyArea);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\groupbuy\IGroupBuyAreaManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */