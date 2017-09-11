package com.enation.app.b2b2c.core.service;

import com.enation.app.b2b2c.core.model.StoreDlyType;
import com.enation.app.shop.core.model.support.DlyTypeConfig;
import com.enation.app.shop.core.model.support.TypeAreaConfig;
import java.util.List;

public abstract interface IStoreDlyTypeManager
{
  public abstract List getDlyTypeList(Integer paramInteger);
  
  public abstract void add(StoreDlyType paramStoreDlyType, DlyTypeConfig paramDlyTypeConfig, TypeAreaConfig[] paramArrayOfTypeAreaConfig);
  
  public abstract Integer getLastId();
  
  public abstract List getDlyTypeById(String paramString);
  
  public abstract List getDlyTypeAreaList(Integer paramInteger);
  
  public abstract Integer getDefaultDlyId(Integer paramInteger);
  
  public abstract void del_dlyType(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\IStoreDlyTypeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */