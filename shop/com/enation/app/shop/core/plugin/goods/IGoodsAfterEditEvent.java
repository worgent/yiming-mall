package com.enation.app.shop.core.plugin.goods;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface IGoodsAfterEditEvent
{
  public abstract void onAfterGoodsEdit(Map paramMap, HttpServletRequest paramHttpServletRequest);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\goods\IGoodsAfterEditEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */