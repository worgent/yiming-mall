package com.enation.app.b2b2c.core.service.cart;

import com.enation.app.b2b2c.core.model.StoreProduct;

public abstract interface IStoreProductManager
{
  public abstract StoreProduct getByGoodsId(Integer paramInteger);
  
  public abstract StoreProduct get(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\cart\IStoreProductManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */