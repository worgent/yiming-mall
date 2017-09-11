package com.enation.app.b2b2c.core.service;

import com.enation.app.b2b2c.core.model.StoreTemlplate;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IStoreTemplateManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract Integer add(StoreTemlplate paramStoreTemlplate);
  
  public abstract List getTemplateList(Integer paramInteger);
  
  public abstract Integer getLastId();
  
  public abstract Map getTemplae(Integer paramInteger1, Integer paramInteger2);
  
  public abstract void edit(StoreTemlplate paramStoreTemlplate);
  
  public abstract void delete(Integer paramInteger);
  
  public abstract Integer getDefTempid(Integer paramInteger);
  
  public abstract void setDefTemp(Integer paramInteger1, Integer paramInteger2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\IStoreTemplateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */