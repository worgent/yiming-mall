package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.WarnNum;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IGoodsStoreManager
{
  public abstract List<Map> listProductStore(Integer paramInteger);
  
  public abstract List<Map> ListProductDepotStore(Integer paramInteger1, Integer paramInteger2);
  
  public abstract List<Map> listProductAllo(Integer paramInteger1, Integer paramInteger2);
  
  public abstract String getStoreHtml(Integer paramInteger);
  
  public abstract String getWarnHtml(Integer paramInteger);
  
  public abstract void saveWarn(int paramInt);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract String getStockHtml(Integer paramInteger);
  
  public abstract String getShipHtml(Integer paramInteger);
  
  public abstract void saveStore(int paramInt);
  
  public abstract void saveStock(int paramInt);
  
  public abstract void saveShip(int paramInt);
  
  public abstract Integer getbStoreByProId(Integer paramInteger1, Integer paramInteger2);
  
  public abstract void saveCmpl(int paramInt);
  
  public abstract List<Map> getDegreeDepotStore(int paramInt1, int paramInt2);
  
  public abstract List<WarnNum> listWarns(Integer paramInteger);
  
  public abstract Page listGoodsStore(Map paramMap, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3);
  
  public abstract void increaseStroe(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract List getStoreList();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IGoodsStoreManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */