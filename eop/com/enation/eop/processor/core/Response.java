package com.enation.eop.processor.core;

import java.io.InputStream;

public abstract interface Response
{
  public abstract String getContent();
  
  public abstract InputStream getInputStream();
  
  public abstract int getStatusCode();
  
  public abstract String getContentType();
  
  public abstract void setContent(String paramString);
  
  public abstract void setStatusCode(int paramInt);
  
  public abstract void setContentType(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\core\Response.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */