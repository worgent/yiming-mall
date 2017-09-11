 package com.enation.app.shop.core.service.impl;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.model.MemberAddress;
 import com.enation.app.shop.core.plugin.member.MemberPluginBundle;
 import com.enation.app.shop.core.service.IMemberAddressManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;








 public class MemberAddressManager
   extends BaseSupport<MemberAddress>
   implements IMemberAddressManager
 {
   private MemberPluginBundle memberPluginBundle;

   @Transactional(propagation=Propagation.REQUIRED)
   public int addAddress(MemberAddress address)
   {
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     address.setMember_id(member.getMember_id());
     this.baseDaoSupport.insert("member_address", address);
     int addressid = this.baseDaoSupport.getLastId("member_address");
     address.setAddr_id(Integer.valueOf(addressid));
     this.memberPluginBundle.onAddressAdd(address);
     return addressid;
   }

   public void deleteAddress(int addr_id)
   {
     this.baseDaoSupport.execute("delete from member_address where addr_id = ?", new Object[] { Integer.valueOf(addr_id) });
   }


   public MemberAddress getAddress(int addr_id)
   {
     MemberAddress address = (MemberAddress)this.baseDaoSupport.queryForObject("select * from member_address where addr_id = ?", MemberAddress.class, new Object[] { Integer.valueOf(addr_id) });


     return address;
   }

   public List<MemberAddress> listAddress()
   {
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     List<MemberAddress> list = this.baseDaoSupport.queryForList("select * from member_address where member_id = ?", MemberAddress.class, new Object[] { member.getMember_id() });

     return list;
   }

   public void updateAddress(MemberAddress address)
   {
     this.baseDaoSupport.update("member_address", address, "addr_id=" + address.getAddr_id());
   }


   public void updateAddressDefult()
   {
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     this.baseDaoSupport.execute("update member_address set def_addr = 0 where member_id = ?", new Object[] { member.getMember_id() });
   }


   public MemberPluginBundle getMemberPluginBundle()
   {
     return this.memberPluginBundle;
   }

   public void setMemberPluginBundle(MemberPluginBundle memberPluginBundle)
   {
     this.memberPluginBundle = memberPluginBundle;
   }


   public int addressCount(int member_id)
   {
     return this.baseDaoSupport.queryForInt("select count(*) from member_address where member_id=?", new Object[] { Integer.valueOf(member_id) });
   }


   public MemberAddress getMemberDefault(Integer memberid)
   {
     String sql = "select * from es_member_address where member_id=? and def_addr=1";
     MemberAddress address = (MemberAddress)this.baseDaoSupport.queryForObject(sql, MemberAddress.class, new Object[] { memberid });
     return address;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\MemberAddressManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */