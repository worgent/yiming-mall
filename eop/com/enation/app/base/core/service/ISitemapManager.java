package com.enation.app.base.core.service;

import com.enation.app.base.core.model.SiteMapUrl;

public abstract interface ISitemapManager
{
  public abstract String getsitemap();
  
  public abstract void addUrl(SiteMapUrl paramSiteMapUrl);
  
  public abstract void editUrl(String paramString, Long paramLong);
  
  public abstract int delete(String paramString);
  
  public abstract void clean();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\ISitemapManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */