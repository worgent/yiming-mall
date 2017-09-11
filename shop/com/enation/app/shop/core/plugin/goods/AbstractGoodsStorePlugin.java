package com.enation.app.shop.core.plugin.goods;

import com.enation.framework.plugin.AutoRegisterPlugin;
import java.util.Map;

public abstract class AbstractGoodsStorePlugin
  extends AutoRegisterPlugin
{
  public abstract String getStoreHtml(Map paramMap);
  
  public abstract String getStockHtml(Map paramMap);
  
  public abstract String getShipHtml(Map paramMap);
  
  public abstract void onStoreSave(Map paramMap);
  
  public abstract void onStockSave(Map paramMap);
  
  public abstract void onShipSave(Map paramMap);
  
  public abstract boolean canBeExecute(Map paramMap);
  
  public abstract String getWarnNumHtml(Map paramMap);
  
  public abstract void onWarnSave(Map paramMap);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\goods\AbstractGoodsStorePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */