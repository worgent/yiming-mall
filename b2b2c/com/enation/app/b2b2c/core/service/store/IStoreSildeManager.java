package com.enation.app.b2b2c.core.service.store;

import com.enation.app.b2b2c.core.model.store.StoreSilde;
import java.util.List;

public abstract interface IStoreSildeManager
{
  public abstract List<StoreSilde> list(Integer paramInteger);
  
  public abstract void edit(Integer[] paramArrayOfInteger, String[] paramArrayOfString1, String[] paramArrayOfString2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\store\IStoreSildeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */