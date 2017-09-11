package com.enation.eop.resource;

import com.enation.eop.resource.model.EopSiteDomain;
import java.util.List;

public abstract interface IDomainManager
{
  public abstract EopSiteDomain get(Integer paramInteger);
  
  public abstract void edit(EopSiteDomain paramEopSiteDomain);
  
  public abstract List<EopSiteDomain> listUserDomain();
  
  public abstract List<EopSiteDomain> listSiteDomain();
  
  public abstract List<EopSiteDomain> listSiteDomain(Integer paramInteger);
  
  public abstract int getDomainCount(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\IDomainManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */