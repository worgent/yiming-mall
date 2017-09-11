package com.enation.app.cms.core.plugin;

import com.enation.app.cms.core.model.DataModel;
import java.util.Map;

public abstract interface IDataSaveEvent
{
  public static final int DATASAVE_ADD = 1;
  public static final int DATASAVE_EDIT = 2;
  
  public abstract void onSave(Map<String, Object> paramMap, DataModel paramDataModel, int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\plugin\IDataSaveEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */