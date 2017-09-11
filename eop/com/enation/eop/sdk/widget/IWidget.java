package com.enation.eop.sdk.widget;

import java.util.Map;

public abstract interface IWidget
{
  public abstract String process(Map<String, String> paramMap);
  
  public abstract String setting(Map<String, String> paramMap);
  
  public abstract void update(Map<String, String> paramMap);
  
  public abstract boolean cacheAble();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\widget\IWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */