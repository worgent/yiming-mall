package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.GoodsComplex;
import java.util.List;
@Deprecated
public abstract interface IGoodsComplexManager
{
  public abstract List listAllComplex(int paramInt);
  
  public abstract List listComplex(int paramInt);
  
  public abstract void addCoodsComplex(GoodsComplex paramGoodsComplex);
  
  public abstract void globalGoodsComplex(int paramInt, List<GoodsComplex> paramList);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IGoodsComplexManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */