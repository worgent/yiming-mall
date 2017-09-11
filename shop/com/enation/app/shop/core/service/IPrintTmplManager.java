package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.PrintTmpl;
import java.util.List;

public abstract interface IPrintTmplManager
{
  public abstract List list();
  
  public abstract List trash();
  
  public abstract List listCanUse();
  
  public abstract void add(PrintTmpl paramPrintTmpl);
  
  public abstract void edit(PrintTmpl paramPrintTmpl);
  
  public abstract PrintTmpl get(int paramInt);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
  
  public abstract void revert(Integer[] paramArrayOfInteger);
  
  public abstract void clean(Integer[] paramArrayOfInteger);
  
  public abstract boolean check(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IPrintTmplManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */