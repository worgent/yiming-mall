package com.enation.app.shop.core.service.promotion;

public abstract interface IPromotionMethod
{
  public abstract String getName();
  
  public abstract String getInputHtml(Integer paramInteger, String paramString);
  
  public abstract String onPromotionSave(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\promotion\IPromotionMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */