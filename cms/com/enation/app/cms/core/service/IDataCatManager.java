package com.enation.app.cms.core.service;

import com.enation.app.cms.core.model.DataCat;
import java.util.List;

public abstract interface IDataCatManager
{
  public abstract void add(DataCat paramDataCat);
  
  public abstract void edit(DataCat paramDataCat);
  
  public abstract int delete(Integer paramInteger);
  
  public abstract DataCat get(Integer paramInteger);
  
  public abstract List<DataCat> listAllChildren(Integer paramInteger);
  
  public abstract List<DataCat> listLevelChildren(Integer paramInteger1, Integer paramInteger2);
  
  public abstract void saveSort(int[] paramArrayOfInt1, int[] paramArrayOfInt2);
  
  public abstract List<DataCat> getParents(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\service\IDataCatManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */