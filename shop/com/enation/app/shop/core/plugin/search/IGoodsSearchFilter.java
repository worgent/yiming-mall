package com.enation.app.shop.core.plugin.search;

import com.enation.app.shop.core.model.Cat;
import java.util.List;

public abstract interface IGoodsSearchFilter
{
  public abstract List<SearchSelector> createSelectorList(Cat paramCat, String paramString1, String paramString2);
  
  public abstract void filter(StringBuffer paramStringBuffer, Cat paramCat, String paramString);
  
  public abstract String getFilterId();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\search\IGoodsSearchFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */