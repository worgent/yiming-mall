package com.enation.eop.processor.core;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface Request
{
  public abstract Response execute(String paramString, HttpServletResponse paramHttpServletResponse, HttpServletRequest paramHttpServletRequest);
  
  public abstract Response execute(String paramString);
  
  public abstract void setExecuteParams(Map<String, String> paramMap);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\core\Request.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */