package com.enation.app.base.core.service.solution;

import org.dom4j.Document;

public abstract interface ISetupCreator
{
  public abstract void addTable(Document paramDocument, String paramString1, String paramString2);
  
  public abstract Document createSetup(String paramString);
  
  public abstract void save(Document paramDocument, String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\ISetupCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */