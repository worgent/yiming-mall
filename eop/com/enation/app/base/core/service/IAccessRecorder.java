package com.enation.app.base.core.service;

import com.enation.eop.resource.model.ThemeUri;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface IAccessRecorder
{
  public abstract int record(ThemeUri paramThemeUri);
  
  public abstract void export();
  
  public abstract Page list(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract List detaillist(String paramString1, String paramString2);
  
  public abstract Map<String, Long> census();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\IAccessRecorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */