package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.SellBackGoodsList;
import com.enation.app.shop.core.model.SellBackList;
import com.enation.app.shop.core.model.SellBackStatus;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface ISellBackManager
{
  public abstract Page list(int paramInt1, int paramInt2);
  
  public abstract Page search(String paramString, int paramInt1, int paramInt2);
  
  public abstract SellBackList get(String paramString);
  
  public abstract SellBackList get(Integer paramInteger);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract Integer save(SellBackList paramSellBackList);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract Integer saveGoodsList(SellBackGoodsList paramSellBackGoodsList);
  
  public abstract SellBackGoodsList getSellBackGoods(Integer paramInteger1, Integer paramInteger2);
  
  public abstract List getGoodsList(Integer paramInteger, String paramString);
  
  public abstract List getGoodsList(Integer paramInteger);
  
  public abstract void saveAccountLog(Map paramMap);
  
  public abstract Integer getRecid(String paramString);
  
  public abstract void editGoodsNum(Map paramMap);
  
  public abstract void editStorageNum(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);
  
  public abstract void delGoods(Integer paramInteger1, Integer paramInteger2);
  
  public abstract List sellBackLogList(Integer paramInteger);
  
  public abstract void syncStore(SellBackList paramSellBackList);
  
  public abstract Integer isAll(int paramInt);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void closePayable(int paramInt, String paramString1, String paramString2);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void saveLog(Integer paramInteger, SellBackStatus paramSellBackStatus, String paramString);
  
  public abstract int searchSn(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\ISellBackManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */