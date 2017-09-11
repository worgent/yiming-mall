package com.enation.eop.resource;

import com.enation.eop.resource.model.EopUserDetail;

public abstract interface IUserDetailManager
{
  public abstract EopUserDetail get(Integer paramInteger);
  
  public abstract void add(EopUserDetail paramEopUserDetail);
  
  public abstract void edit(EopUserDetail paramEopUserDetail);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\IUserDetailManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */