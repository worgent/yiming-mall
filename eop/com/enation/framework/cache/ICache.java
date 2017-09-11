package com.enation.framework.cache;

public abstract interface ICache<T>
{
  public abstract T get(Object paramObject);
  
  public abstract void put(Object paramObject, T paramT);
  
  public abstract void remove(Object paramObject);
  
  public abstract void clear();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\cache\ICache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */