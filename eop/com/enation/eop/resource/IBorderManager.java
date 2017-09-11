package com.enation.eop.resource;

import com.enation.eop.resource.model.Border;
import java.util.List;

public abstract interface IBorderManager
{
  public abstract void add(Border paramBorder);
  
  public abstract void update(Border paramBorder);
  
  public abstract void delete(Integer paramInteger);
  
  public abstract List<Border> list();
  
  public abstract void clean();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\IBorderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */