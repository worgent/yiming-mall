package com.enation.app.b2b2c.core.service.goods;

import com.enation.app.shop.core.model.Tag;
import com.enation.framework.database.Page;

public abstract interface IB2b2cGoodsTagManager
{
  public abstract Page list(int paramInt1, int paramInt2);

  public abstract Tag getById(Integer paramInteger);

  public abstract void add(Tag paramTag);

  public abstract void update(Tag paramTag);

  public abstract void delete(Integer[] paramArrayOfInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\goods\IB2b2cGoodsTagManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */