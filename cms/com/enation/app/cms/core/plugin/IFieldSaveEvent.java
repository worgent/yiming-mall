package com.enation.app.cms.core.plugin;

import com.enation.app.cms.core.model.DataField;
import java.util.Map;

public abstract interface IFieldSaveEvent
{
  public abstract void onSave(Map paramMap, DataField paramDataField);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\plugin\IFieldSaveEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */