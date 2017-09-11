package com.enation.app.base.core.service;

import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface ISettingService
{
  public abstract void add(String paramString1, String paramString2, String paramString3);
  
  public abstract void save(String paramString1, String paramString2, String paramString3);
  
  public abstract void delete(String paramString);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void save(Map<String, Map<String, String>> paramMap)
    throws SettingRuntimeException;
  
  public abstract Map<String, Map<String, String>> getSetting();
  
  public abstract String getSetting(String paramString1, String paramString2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\ISettingService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */