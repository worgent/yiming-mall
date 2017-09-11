package com.enation.app.shop.core.service.batchimport;

import org.apache.poi.ss.usermodel.Sheet;
import org.w3c.dom.Node;

public abstract interface IImportPreprocess
{
  public abstract void preprocess(Sheet paramSheet, Node paramNode);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\batchimport\IImportPreprocess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */