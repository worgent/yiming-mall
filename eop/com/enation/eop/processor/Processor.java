package com.enation.eop.processor;

import com.enation.eop.processor.core.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface Processor
{
  public abstract Response process(int paramInt, HttpServletResponse paramHttpServletResponse, HttpServletRequest paramHttpServletRequest);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\Processor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */