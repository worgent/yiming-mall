package com.enation.app.b2b2c.component.plugin.store;

import com.enation.app.b2b2c.core.model.store.Store;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IAfterStoreApplyEvent
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void IAfterStoreApplyEvent(Store paramStore);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\store\IAfterStoreApplyEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */