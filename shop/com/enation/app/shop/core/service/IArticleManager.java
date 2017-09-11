package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Article;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IArticleManager
{
  public abstract void saveAdd(Article paramArticle);
  
  public abstract void saveEdit(Article paramArticle);
  
  public abstract Article get(Integer paramInteger);
  
  public abstract List listByCatId(Integer paramInteger);
  
  public abstract Page pageByCatId(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);
  
  public abstract List topListByCatId(Integer paramInteger1, Integer paramInteger2);
  
  public abstract String getCatName(Integer paramInteger);
  
  public abstract void delete(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IArticleManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */