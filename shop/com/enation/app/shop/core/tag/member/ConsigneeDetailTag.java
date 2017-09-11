 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.MemberAddress;
 import com.enation.app.shop.core.service.IMemberAddressManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;














 @Component
 @Scope("prototype")
 public class ConsigneeDetailTag
   extends BaseFreeMarkerTag
 {
   private IMemberAddressManager memberAddressManager;

   public Object exec(Map arg)
     throws TemplateModelException
   {
     Integer addressid = Integer.valueOf(Integer.parseInt((String)arg.get("addressid")));
     if (addressid == null) {
       throw new TemplateModelException("必须提供收货地址id参数");
     }
     MemberAddress address = this.memberAddressManager.getAddress(addressid.intValue());
     if (address == null) {
       return "0";
     }
     return this.memberAddressManager.getAddress(addressid.intValue());
   }

   public IMemberAddressManager getMemberAddressManager() {
     return this.memberAddressManager;
   }

   public void setMemberAddressManager(IMemberAddressManager memberAddressManager) {
     this.memberAddressManager = memberAddressManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\ConsigneeDetailTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */