package com.enation.eop.resource;

import com.enation.eop.resource.model.EopApp;
import java.util.List;

public abstract interface IAppManager
{
  public abstract void add(EopApp paramEopApp);
  
  public abstract List<EopApp> list();
  
  public abstract EopApp get(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\IAppManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */