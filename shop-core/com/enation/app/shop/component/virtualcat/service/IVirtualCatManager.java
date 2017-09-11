package com.enation.app.shop.component.virtualcat.service;

import com.enation.app.shop.component.virtualcat.model.VirtualCat;
import java.util.List;

public abstract interface IVirtualCatManager
{
  public abstract void add(VirtualCat paramVirtualCat);
  
  public abstract void edit(VirtualCat paramVirtualCat);
  
  public abstract VirtualCat get(int paramInt);
  
  public abstract void delete(int paramInt);
  
  public abstract List<VirtualCat> list();
  
  public abstract List<VirtualCat> listChildren(long paramLong);
  
  public abstract List<VirtualCat> getTree();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\virtualcat\service\IVirtualCatManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */