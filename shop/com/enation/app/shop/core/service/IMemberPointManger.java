package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.FreezePoint;
import java.util.List;

public abstract interface IMemberPointManger
{
  public static final String TYPE_REGISTER = "register";
  public static final String TYPE_EMIAL_CHECK = "email_check";
  public static final String TYPE_FINISH_PROFILE = "finish_profile";
  public static final String TYPE_BUYGOODS = "buygoods";
  public static final String TYPE_ONLINEPAY = "onlinepay";
  public static final String TYPE_LOGIN = "login";
  public static final String TYPE_COMMENT = "comment";
  public static final String TYPE_COMMENT_IMG = "comment_img";
  public static final String TYPE_FIRST_COMMENT = "first_comment";
  public static final String TYPE_REGISTER_LINK = "register_link";
  public static final String TYPE_GOODS_POINT = "goods_point";
  
  public abstract void thaw(FreezePoint paramFreezePoint, boolean paramBoolean);
  
  public abstract void thaw(Integer paramInteger);
  
  public abstract List<FreezePoint> listByBeforeDay(int paramInt);
  
  public abstract void addFreezePoint(FreezePoint paramFreezePoint, String paramString);
  
  public abstract int getFreezeMpByMemberId(int paramInt);
  
  public abstract int getFreezePointByMemberId(int paramInt);
  
  public abstract boolean checkIsOpen(String paramString);
  
  public abstract int getItemPoint(String paramString);
  
  public abstract void add(int paramInt1, int paramInt2, String paramString, Integer paramInteger, int paramInt3);
  
  public abstract void deleteByOrderId(Integer paramInteger);
  
  public abstract void useMarketPoint(int paramInt1, int paramInt2, String paramString, Integer paramInteger);
  
  public abstract Double pointToPrice(int paramInt);
  
  public abstract int priceToPoint(Double paramDouble);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IMemberPointManger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */