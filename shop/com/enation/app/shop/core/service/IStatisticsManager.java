package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.statistics.DayAmount;
import com.enation.app.shop.core.model.statistics.MonthAmount;
import java.util.List;
import java.util.Map;

public abstract interface IStatisticsManager
{
  public abstract List<MonthAmount> statisticsMonth_Amount();
  
  public abstract List<MonthAmount> statisticsMonth_Amount(String paramString);
  
  public abstract List<DayAmount> statisticsDay_Amount();
  
  public abstract List<DayAmount> statisticsDay_Amount(String paramString);
  
  public abstract List<Map> orderStatByPayment();
  
  public abstract List<Map> orderStatByShip();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IStatisticsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */