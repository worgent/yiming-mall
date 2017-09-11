package com.enation.framework.context.webcontext;

import java.util.Set;
import javax.servlet.http.HttpSession;

public abstract interface WebSessionContext<T>
{
  public static final String sessionAttributeKey = "EOPSessionKey";
  
  public abstract HttpSession getSession();
  
  public abstract void setSession(HttpSession paramHttpSession);
  
  public abstract void invalidateSession();
  
  public abstract void setAttribute(String paramString, T paramT);
  
  public abstract T getAttribute(String paramString);
  
  public abstract Set<T> getAttributeNames();
  
  public abstract void removeAttribute(String paramString);
  
  public abstract void destory();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\context\webcontext\WebSessionContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */