package com.enation.app.cms.core.service;

import com.enation.app.cms.core.model.DataModel;
import java.util.List;

public abstract interface IDataModelManager
{
  public abstract void add(DataModel paramDataModel);
  
  public abstract void edit(DataModel paramDataModel);
  
  public abstract void delete(Integer paramInteger);
  
  public abstract List<DataModel> list();
  
  public abstract DataModel get(Integer paramInteger);
  
  public abstract int checkIfModelInUse(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\service\IDataModelManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */