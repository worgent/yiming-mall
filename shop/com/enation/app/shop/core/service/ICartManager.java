package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Cart;
import com.enation.app.shop.core.model.support.CartItem;
import com.enation.app.shop.core.model.support.OrderPrice;
import java.util.List;

public abstract interface ICartManager
{
  public abstract int add(Cart paramCart);
  
  public abstract Cart get(int paramInt);
  
  public abstract Integer countItemNum(String paramString);
  
  public abstract boolean checkGoodsInCart(Integer paramInteger);
  
  public abstract Cart getCartByProductId(int paramInt, String paramString);
  
  public abstract Cart getCartByProductId(int paramInt, String paramString1, String paramString2);
  
  public abstract List<CartItem> listGoods(String paramString);
  
  public abstract void clean(String paramString);
  
  public abstract void clean(String paramString, Integer paramInteger1, Integer paramInteger2);
  
  public abstract void updateNum(String paramString, Integer paramInteger1, Integer paramInteger2);
  
  public abstract void delete(String paramString, Integer paramInteger);
  
  public abstract Double countGoodsWeight(String paramString);
  
  public abstract Integer countPoint(String paramString);
  
  public abstract OrderPrice countPrice(List<CartItem> paramList, Integer paramInteger, String paramString);
  
  public abstract Double countGoodsTotal(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\ICartManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */