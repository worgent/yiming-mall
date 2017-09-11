package com.enation.app.shop.component.spec.service;

import com.enation.app.shop.core.model.SpecValue;
import java.util.List;
import java.util.Map;

public abstract interface ISpecValueManager
{
  public abstract void add(SpecValue paramSpecValue);
  
  public abstract void update(SpecValue paramSpecValue);
  
  public abstract List<SpecValue> list(Integer paramInteger);
  
  public abstract Map get(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\service\ISpecValueManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */