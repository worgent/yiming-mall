package com.enation.app.b2b2c.core.service;

import com.enation.app.b2b2c.core.model.MemberCollect;
import com.enation.framework.database.Page;

public abstract interface IStoreCollectManager
{
  public abstract void addCollect(MemberCollect paramMemberCollect);
  
  public abstract void delCollect(Integer paramInteger);
  
  public abstract Page getList(Integer paramInteger, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\IStoreCollectManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */