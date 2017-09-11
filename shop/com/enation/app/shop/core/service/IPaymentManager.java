package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.PayCfg;
import com.enation.framework.plugin.IPlugin;
import java.util.List;
import java.util.Map;

public abstract interface IPaymentManager
{
  public abstract List list();
  
  public abstract PayCfg get(Integer paramInteger);
  
  public abstract PayCfg get(String paramString);
  
  public abstract Double countPayPrice(Integer paramInteger);
  
  public abstract Integer add(String paramString1, String paramString2, String paramString3, Map<String, String> paramMap);
  
  public abstract void edit(Integer paramInteger, String paramString1, String paramString2, String paramString3, Map<String, String> paramMap);
  
  public abstract Map<String, String> getConfigParams(Integer paramInteger);
  
  public abstract Map<String, String> getConfigParams(String paramString);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
  
  public abstract List<IPlugin> listAvailablePlugins();
  
  public abstract String getPluginInstallHtml(String paramString, Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IPaymentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */