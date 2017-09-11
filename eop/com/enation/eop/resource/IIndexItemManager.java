package com.enation.eop.resource;

import com.enation.eop.resource.model.IndexItem;
import java.util.List;

public abstract interface IIndexItemManager
{
  public abstract void add(IndexItem paramIndexItem);
  
  public abstract List<IndexItem> list();
  
  public abstract void clean();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\IIndexItemManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */