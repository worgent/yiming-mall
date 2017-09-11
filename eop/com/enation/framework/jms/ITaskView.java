package com.enation.framework.jms;

public abstract interface ITaskView
{
  public abstract String getTaskId();
  
  public abstract String getTaskName();
  
  public abstract int getState();
  
  public abstract void setState(int paramInt);
  
  public abstract String getErrorMessage();
  
  public abstract void setErrorMessage(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\jms\ITaskView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */