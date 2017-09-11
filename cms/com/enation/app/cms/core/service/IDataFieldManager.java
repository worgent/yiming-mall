package com.enation.app.cms.core.service;

import com.enation.app.cms.core.model.DataField;
import java.util.List;

public abstract interface IDataFieldManager
{
  public abstract Integer add(DataField paramDataField);
  
  public abstract void edit(DataField paramDataField);
  
  public abstract void delete(Integer paramInteger);
  
  public abstract List<DataField> list(Integer paramInteger);
  
  public abstract List<DataField> listIsShow(Integer paramInteger);
  
  public abstract DataField get(Integer paramInteger);
  
  public abstract List<DataField> listByCatId(Integer paramInteger);
  
  public abstract void saveSort(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\service\IDataFieldManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */