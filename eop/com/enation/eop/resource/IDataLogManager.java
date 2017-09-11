package com.enation.eop.resource;

import com.enation.app.base.core.model.DataLog;
import com.enation.framework.database.Page;

public abstract interface IDataLogManager
{
  public abstract void add(DataLog paramDataLog);
  
  public abstract Page list(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\IDataLogManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */