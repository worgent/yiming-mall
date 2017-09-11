package com.enation.app.b2b2c.core.service.goods;

import com.enation.app.b2b2c.core.model.goods.StoreGoods;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface IStoreGoodsManager
{
  public abstract Page storeGoodsList(Integer paramInteger1, Integer paramInteger2, Map paramMap);
  
  public abstract List<Map> storeGoodsList(int paramInt, Map paramMap);
  
  public abstract Page b2b2cGoodsList(Integer paramInteger1, Integer paramInteger2, Map paramMap);
  
  public abstract Page store_searchGoodsList(Integer paramInteger1, Integer paramInteger2, Map paramMap);
  
  public abstract List transactionList(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);
  
  public abstract int transactionCount(Integer paramInteger);
  
  public abstract StoreGoods getGoods(Integer paramInteger);
  
  public abstract void saveGoodsStore(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);
  
  public abstract int getStoreGoodsNum(int paramInt);
  
  public abstract Map getGoodsStore(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\goods\IStoreGoodsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */