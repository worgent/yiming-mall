package com.enation.eop.resource;

import com.enation.eop.resource.model.AdminUser;
import com.enation.eop.resource.model.Menu;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IMenuManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract Integer add(Menu paramMenu);
  
  public abstract void edit(Menu paramMenu);
  
  public abstract List<Menu> getMenuList();
  
  public abstract Menu get(Integer paramInteger);
  
  public abstract Menu get(String paramString);
  
  public abstract List<Menu> getMenuTree(Integer paramInteger);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void delete(Integer paramInteger)
    throws RuntimeException;
  
  public abstract void delete(String paramString);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void updateSort(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void clean();
  
  public abstract void move(int paramInt1, int paramInt2, String paramString);
  
  public abstract List<Menu> newMenutree(Integer paramInteger, AdminUser paramAdminUser);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\IMenuManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */