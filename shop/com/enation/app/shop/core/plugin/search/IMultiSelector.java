package com.enation.app.shop.core.plugin.search;

import com.enation.app.shop.core.model.Cat;
import java.util.List;
import java.util.Map;

public abstract interface IMultiSelector
{
  public abstract Map<String, List<SearchSelector>> createMultiSelector(Cat paramCat, String paramString1, String paramString2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\search\IMultiSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */