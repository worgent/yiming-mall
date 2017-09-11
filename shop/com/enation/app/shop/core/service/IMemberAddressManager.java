package com.enation.app.shop.core.service;

import com.enation.app.base.core.model.MemberAddress;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IMemberAddressManager
{
  public abstract List<MemberAddress> listAddress();
  
  public abstract MemberAddress getAddress(int paramInt);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int addAddress(MemberAddress paramMemberAddress);
  
  public abstract void updateAddress(MemberAddress paramMemberAddress);
  
  public abstract void updateAddressDefult();
  
  public abstract void deleteAddress(int paramInt);
  
  public abstract int addressCount(int paramInt);
  
  public abstract MemberAddress getMemberDefault(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IMemberAddressManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */