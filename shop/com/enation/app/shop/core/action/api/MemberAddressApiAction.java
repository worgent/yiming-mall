 package com.enation.app.shop.core.action.api;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.model.MemberAddress;
 import com.enation.app.shop.core.service.IMemberAddressManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.JsonMessageUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;


















 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("memberAddress")
 public class MemberAddressApiAction
   extends WWAction
 {
   private IMemberAddressManager memberAddressManager;

   public String list()
   {
     List<MemberAddress> addressList = null;
     MemberAddress defaultAddress = null;
     Member member = UserServiceFactory.getUserService().getCurrentMember();
     if (member != null)
     {
       addressList = this.memberAddressManager.listAddress();
       defaultAddress = getDefaultAddress(addressList);
     } else {
       addressList = new ArrayList();
     }

     Map data = new HashMap();
     data.put("addressList", addressList);
     data.put("defaultAddress", defaultAddress);
     this.json = JsonMessageUtil.getObjectJson(data);
     return "json_message";
   }





















   public String add()
   {
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     if (member == null) {
       showErrorJson("无权访问此api[未登陆或已超时]");
       return "json_message";
     }

     if (this.memberAddressManager.addressCount(member.getMember_id().intValue()) >= 10) {
       showErrorJson("添加失败。原因：最多可以维护10个收货地址。");
     } else {
       MemberAddress address = new MemberAddress();
       try {
         address = fillAddressFromReq(address);
         HttpServletRequest request = ThreadContextHolder.getHttpRequest();
         String def_addr = request.getParameter("def_addr");
         if ("1".equals(def_addr)) {
           address.setDef_addr(Integer.valueOf(def_addr));
           this.memberAddressManager.updateAddressDefult();
         }

         this.memberAddressManager.addAddress(address);
         showSuccessJson("添加成功");
       } catch (Exception e) {
         if (this.logger.isDebugEnabled()) {
           this.logger.error(e.getStackTrace());
         }
         showErrorJson("添加失败[" + e.getMessage() + "]");
       }
     }
     return "json_message";
   }




















   public String edit()
   {
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     if (member == null) {
       showErrorJson("无权访问此api[未登陆或已超时]");
       return "json_message";
     }

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String addr_id = request.getParameter("addr_id");
     MemberAddress address = this.memberAddressManager.getAddress(Integer.valueOf(addr_id).intValue());
     try {
       address = fillAddressFromReq(address);
       String def_addr = request.getParameter("def_addr");
       if ("1".equals(def_addr)) {
         address.setDef_addr(Integer.valueOf(def_addr));
         this.memberAddressManager.updateAddressDefult();
       }
       if ("0".equals(def_addr)) {
         address.setDef_addr(Integer.valueOf(def_addr));
         this.memberAddressManager.updateAddressDefult();
       }
       this.memberAddressManager.updateAddress(address);
       showSuccessJson("修改成功");
     } catch (Exception e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.error(e.getStackTrace());
       }
       showErrorJson("修改失败[" + e.getMessage() + "]");
     }
     return "json_message";
   }







   public String delete()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String addr_id = request.getParameter("addr_id");
     try {
       this.memberAddressManager.deleteAddress(Integer.valueOf(addr_id).intValue());
       showSuccessJson("删除成功");
     } catch (Exception e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.error(e.getStackTrace());
       }
       showErrorJson("删除失败[" + e.getMessage() + "]");
     }
     return "json_message";
   }










   public String defaddr()
   {
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     if (member == null) {
       showErrorJson("无权访问此api[未登陆或已超时]");
       return "json_message";
     }

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String addr_id = request.getParameter("addr_id");
     MemberAddress address = this.memberAddressManager.getAddress(Integer.valueOf(addr_id).intValue());
     address.setDef_addr(Integer.valueOf(1));
     try {
       this.memberAddressManager.updateAddressDefult();
       this.memberAddressManager.updateAddress(address);
       showSuccessJson("设置成功");
     } catch (Exception e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.error(e.getStackTrace());
       }
       showErrorJson("设置失败[" + e.getMessage() + "]");
     }
     return "json_message";
   }







   private MemberAddress getDefaultAddress(List<MemberAddress> addressList)
   {
     if ((addressList != null) && (!addressList.isEmpty())) {
       for (MemberAddress address : addressList) {
         if ((address.getDef_addr() != null) && (address.getDef_addr().intValue() == 1)) {
           address.setDef_addr(Integer.valueOf(1));
           return address;
         }
       }

       MemberAddress defAddress = (MemberAddress)addressList.get(0);
       defAddress.setDef_addr(Integer.valueOf(1));
       return defAddress;
     }
     return null;
   }






   private MemberAddress fillAddressFromReq(MemberAddress address)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String def_addr = request.getParameter("def_addr");
     if ("yes".equals(def_addr)) {
       address.setDef_addr(Integer.valueOf(def_addr));
     }
     String name = request.getParameter("name");
     address.setName(name);
     if ((name == null) || (name.equals(""))) {
       throw new RuntimeException("姓名不能为空！");
     }
     Pattern p = Pattern.compile("^[0-9A-Za-z一-龥]{0,20}$");
     Matcher m = p.matcher(name);

     if (!m.matches()) {
       throw new RuntimeException("收货人格式不正确！");
     }

     String tel = request.getParameter("tel");
     address.setTel(tel);

     String mobile = request.getParameter("mobile");
     address.setMobile(mobile);
     if ((StringUtil.isEmpty(tel)) && (StringUtil.isEmpty(mobile)))
       throw new RuntimeException("联系电话和联系手机必须填写一项！");
     if ((!StringUtil.isEmpty(tel)) && (!isMobile(tel)))
       throw new RuntimeException("电话格式不对！");
     if ((!StringUtil.isEmpty(mobile)) && (!isPhone(mobile))) {
       throw new RuntimeException("手机格式不对！");
     }

     String province_id = request.getParameter("province_id");
     if ((province_id == null) || (province_id.equals(""))) {
       throw new RuntimeException("请选择地区中的省！");
     }
     address.setProvince_id(Integer.valueOf(province_id));

     String city_id = request.getParameter("city_id");
     if ((city_id == null) || (city_id.equals(""))) {
       throw new RuntimeException("请选择地区中的市！");
     }
     address.setCity_id(Integer.valueOf(city_id));

     String region_id = request.getParameter("region_id");
     if ((region_id == null) || (region_id.equals(""))) {
       throw new RuntimeException("请选择地区中的县！");
     }
     address.setRegion_id(Integer.valueOf(region_id));

     String province = request.getParameter("province");
     address.setProvince(province);

     String city = request.getParameter("city");
     address.setCity(city);

     String region = request.getParameter("region");
     address.setRegion(region);

     String addr = request.getParameter("addr");
     if ((addr == null) || (addr.equals(""))) {
       throw new RuntimeException("地址不能为空！");
     }







     address.setAddr(addr);

     String zip = request.getParameter("zip");
     if ((zip == null) || (zip.equals(""))) {
       throw new RuntimeException("邮编不能为空！");
     }
     address.setZip(zip);

     String remark = request.getParameter("remark");
     address.setRemark(remark);

     return address;
   }

   public IMemberAddressManager getMemberAddressManager() {
     return this.memberAddressManager;
   }



   public void setMemberAddressManager(IMemberAddressManager memberAddressManager) { this.memberAddressManager = memberAddressManager; }

   private static boolean isPhone(String str) {
     Pattern p = null;
     Matcher m = null;
     boolean b = false;
     p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
     m = p.matcher(str);
     b = m.matches();
     return b;
   }

   private static boolean isMobile(String str) { Pattern p1 = null;Pattern p2 = null;
     Matcher m = null;
     boolean b = false;
     p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");
     p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");
     if (str.length() > 9) {
       m = p1.matcher(str);
       b = m.matches();
     } else {
       m = p2.matcher(str);
       b = m.matches();
     }
     return b;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\MemberAddressApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */