package com.enation.app.shop.core.service.batchimport;

import com.enation.app.shop.core.model.ImportDataSource;
import java.util.Map;
import org.w3c.dom.Element;

public abstract interface IGoodsDataImporter
{
  public abstract void imported(Object paramObject, Element paramElement, ImportDataSource paramImportDataSource, Map paramMap);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\batchimport\IGoodsDataImporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */