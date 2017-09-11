package com.enation.app.b2b2c.core.service.groupbuy;

import com.enation.app.b2b2c.core.model.groupbuy.GroupBuy;
import com.enation.framework.database.Page;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IGroupBuyManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int add(GroupBuy paramGroupBuy);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void update(GroupBuy paramGroupBuy);
  
  public abstract void delete(int paramInt);
  
  public abstract void auth(int paramInt1, int paramInt2);
  
  public abstract Page listByStoreId(int paramInt1, int paramInt2, int paramInt3, Map paramMap);
  
  public abstract Page listByActId(int paramInt1, int paramInt2, int paramInt3, Integer paramInteger);
  
  public abstract Page search(int paramInt1, int paramInt2, Integer paramInteger1, Double paramDouble1, Double paramDouble2, int paramInt3, int paramInt4, Integer paramInteger2);
  
  public abstract GroupBuy get(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\groupbuy\IGroupBuyManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */