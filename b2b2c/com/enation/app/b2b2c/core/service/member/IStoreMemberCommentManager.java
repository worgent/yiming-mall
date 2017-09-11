package com.enation.app.b2b2c.core.service.member;

import com.enation.app.b2b2c.core.model.member.StoreMemberComment;
import com.enation.framework.database.Page;
import java.util.Map;

public abstract interface IStoreMemberCommentManager
{
  public abstract Page getAllComments(int paramInt1, int paramInt2, Map paramMap, Integer paramInteger);
  
  public abstract Map get(Integer paramInteger);
  
  public abstract void edit(Map paramMap, Integer paramInteger);
  
  public abstract void add(StoreMemberComment paramStoreMemberComment);
  
  public abstract Integer getCommentCount(Integer paramInteger1, Integer paramInteger2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\member\IStoreMemberCommentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */