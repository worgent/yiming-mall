package com.enation.app.shop.component.spec.service;

import com.enation.app.shop.core.model.SpecValue;
import com.enation.app.shop.core.model.Specification;
import java.util.List;
import java.util.Map;

public abstract interface ISpecManager
{
  public abstract boolean checkUsed(Integer[] paramArrayOfInteger);
  
  public abstract boolean checkUsed(Integer paramInteger);
  
  public abstract List list();
  
  public abstract List<Specification> listSpecAndValue();
  
  public abstract void add(Specification paramSpecification, List<SpecValue> paramList);
  
  public abstract void edit(Specification paramSpecification, List<SpecValue> paramList);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
  
  public abstract Map get(Integer paramInteger);
  
  public abstract List<Map<String, String>> getProSpecList(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\service\ISpecManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */