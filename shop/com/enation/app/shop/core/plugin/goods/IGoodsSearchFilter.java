package com.enation.app.shop.core.plugin.goods;

public abstract interface IGoodsSearchFilter
{
  public abstract String getSelector();
  
  public abstract String getFrom();
  
  public abstract void filter(StringBuffer paramStringBuffer);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\goods\IGoodsSearchFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */