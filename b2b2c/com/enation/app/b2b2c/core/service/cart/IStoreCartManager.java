package com.enation.app.b2b2c.core.service.cart;

import com.enation.app.b2b2c.core.model.cart.StoreCartItem;
import com.enation.app.shop.core.model.support.OrderPrice;
import java.util.List;
import java.util.Map;

public abstract interface IStoreCartManager
{
  public static final String FILTER_KEY = "cartFilter";
  
  public abstract List<StoreCartItem> listGoods(String paramString);
  
  public abstract OrderPrice countPrice(List<StoreCartItem> paramList, String paramString, Integer paramInteger, Boolean paramBoolean);
  
  public abstract OrderPrice countPrice(List<StoreCartItem> paramList, String paramString, String[] paramArrayOfString, Boolean paramBoolean);
  
  public abstract List<Map> storeListGoods(String paramString);
  
  public abstract void clean(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\cart\IStoreCartManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */