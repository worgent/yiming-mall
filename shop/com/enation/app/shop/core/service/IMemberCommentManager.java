package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.MemberComment;
import com.enation.framework.database.Page;
import java.util.Map;

public abstract interface IMemberCommentManager
{
  public abstract void add(MemberComment paramMemberComment);
  
  public abstract Page getGoodsComments(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract int getGoodsGrade(int paramInt);
  
  public abstract Page getAllComments(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Page getCommentsByStatus(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract MemberComment get(int paramInt);
  
  public abstract void update(MemberComment paramMemberComment);
  
  public abstract int getGoodsCommentsCount(int paramInt);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
  
  public abstract void deletealone(int paramInt);
  
  public abstract Page getMemberComments(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract int getMemberCommentTotal(int paramInt1, int paramInt2);
  
  public abstract Map statistics(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IMemberCommentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */