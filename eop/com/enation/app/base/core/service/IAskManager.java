package com.enation.app.base.core.service;

import com.enation.app.base.core.model.Ask;
import com.enation.app.base.core.model.Reply;
import com.enation.framework.database.Page;
import java.util.Date;

public abstract interface IAskManager
{
  public abstract void add(Ask paramAsk);
  
  public abstract void reply(Reply paramReply);
  
  public abstract Ask get(Integer paramInteger);
  
  public abstract Page listMyAsk(String paramString, Date paramDate1, Date paramDate2, int paramInt1, int paramInt2);
  
  public abstract Page listAllAsk(String paramString, Date paramDate1, Date paramDate2, int paramInt1, int paramInt2);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\IAskManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */