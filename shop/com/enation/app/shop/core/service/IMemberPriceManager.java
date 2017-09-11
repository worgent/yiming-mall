package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.GoodsLvPrice;
import java.util.List;

public abstract interface IMemberPriceManager
{
  public abstract void save(List<GoodsLvPrice> paramList);
  
  public abstract List<GoodsLvPrice> listPriceByGid(int paramInt);
  
  public abstract List<GoodsLvPrice> listPriceByPid(int paramInt);
  
  public abstract List<GoodsLvPrice> listPriceByLvid(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IMemberPriceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */