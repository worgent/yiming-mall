package com.enation.eop.sdk;

import com.enation.eop.resource.model.EopSite;

public abstract interface IApp
{
  public abstract void install();
  
  public abstract void saasInstall();
  
  public abstract String dumpXml();
  
  public abstract void sessionDestroyed(String paramString, EopSite paramEopSite);
  
  public abstract String getName();
  
  public abstract String getId();
  
  public abstract String getNameSpace();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\IApp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */