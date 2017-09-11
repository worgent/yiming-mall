package com.enation.app.b2b2c.core.service.order;

import com.enation.framework.database.Page;
import java.util.Map;

public abstract interface IB2B2cOrderReportManager
{
  public abstract Page listPayment(Map paramMap, int paramInt1, int paramInt2, String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\order\IB2B2cOrderReportManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */