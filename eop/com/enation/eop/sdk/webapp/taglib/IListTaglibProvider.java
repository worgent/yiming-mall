package com.enation.eop.sdk.webapp.taglib;

import java.util.List;
import javax.servlet.jsp.PageContext;

public abstract interface IListTaglibProvider
{
  public abstract List getData(IListTaglibParam paramIListTaglibParam, PageContext paramPageContext);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\webapp\taglib\IListTaglibProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */