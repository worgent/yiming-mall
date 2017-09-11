package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.model.PackageProduct;
import java.util.List;

public abstract interface IPackageProductManager
{
  public abstract void add(PackageProduct paramPackageProduct);
  
  public abstract List list(int paramInt);
  
  public abstract void add(Goods paramGoods, int[] paramArrayOfInt1, int[] paramArrayOfInt2);
  
  public abstract void edit(Goods paramGoods, int[] paramArrayOfInt1, int[] paramArrayOfInt2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IPackageProductManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */