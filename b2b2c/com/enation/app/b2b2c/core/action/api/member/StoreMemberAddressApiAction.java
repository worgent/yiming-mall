 package com.enation.app.b2b2c.core.action.api.member;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreMemberAddressManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.base.core.model.MemberAddress;
 import com.enation.app.shop.core.service.IMemberAddressManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import freemarker.template.TemplateModelException;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.stereotype.Component;










 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("memberAddress")
 public class StoreMemberAddressApiAction
   extends WWAction
 {
   private IMemberAddressManager memberAddressManager;
   private IStoreMemberAddressManager storeMemberAddressManager;
   private IStoreMemberManager storeMemberManager;
   private Integer memberid;
   private Integer addrid;

   public String addNewAddress()
   {
     MemberAddress address = new MemberAddress();
     try {
       address = createAddress();
       this.memberAddressManager.addAddress(address);
       showSuccessJson("添加成功");
       return "json_message";
     } catch (Exception e) {
       this.logger.error("前台添加地址错误", e);

       showErrorJson("添加失败"); }
     return "json_message";
   }














   private MemberAddress createAddress()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     MemberAddress address = new MemberAddress();

     String name = request.getParameter("shipName");
     address.setName(name);

     String tel = request.getParameter("shipTel");
     address.setTel(tel);

     String mobile = request.getParameter("shipMobile");
     address.setMobile(mobile);

     String province_id = request.getParameter("province_id");
     if (province_id != null) {
       address.setProvince_id(Integer.valueOf(province_id));
     }

     String city_id = request.getParameter("city_id");
     if (city_id != null) {
       address.setCity_id(Integer.valueOf(city_id));
     }

     String region_id = request.getParameter("region_id");
     if (region_id != null) {
       address.setRegion_id(Integer.valueOf(region_id));
     }

     String province = request.getParameter("province");
     address.setProvince(province);

     String city = request.getParameter("city");
     address.setCity(city);

     String region = request.getParameter("region");
     address.setRegion(region);

     String addr = request.getParameter("shipAddr");
     address.setAddr(addr);

     String zip = request.getParameter("shipZip");
     address.setZip(zip);

     return address;
   }





   public String setDefAddress()
   {
     try
     {
       StoreMember member = this.storeMemberManager.getStoreMember();
       if (member == null) {
         throw new TemplateModelException("未登陆不能使用此标签[ConsigneeListTag]");
       }
       this.storeMemberAddressManager.updateMemberAddress(member.getMember_id(), this.addrid);
       showSuccessJson("设置成功");
     } catch (Exception e) {
       showErrorJson("修改失败");
     }
     return "json_message";
   }

   public IMemberAddressManager getMemberAddressManager() {
     return this.memberAddressManager;
   }

   public void setMemberAddressManager(IMemberAddressManager memberAddressManager) {
     this.memberAddressManager = memberAddressManager;
   }

   public IStoreMemberAddressManager getStoreMemberAddressManager() {
     return this.storeMemberAddressManager;
   }

   public void setStoreMemberAddressManager(IStoreMemberAddressManager storeMemberAddressManager)
   {
     this.storeMemberAddressManager = storeMemberAddressManager;
   }

   public Integer getMemberid() {
     return this.memberid;
   }

   public void setMemberid(Integer memberid) {
     this.memberid = memberid;
   }

   public Integer getAddrid() {
     return this.addrid;
   }

   public void setAddrid(Integer addrid) {
     this.addrid = addrid;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\member\StoreMemberAddressApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */