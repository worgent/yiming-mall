package com.enation.eop.sdk.webapp.plugin;

public abstract interface IPlugin
{
  public abstract String getType();
  
  public abstract String getId();
  
  public abstract String getName();
  
  public abstract String getVersion();
  
  public abstract String getAuthor();
  
  public abstract void perform(Object... paramVarArgs);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\webapp\plugin\IPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */