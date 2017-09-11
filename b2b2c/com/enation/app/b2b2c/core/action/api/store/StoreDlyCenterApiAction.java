 package com.enation.app.b2b2c.core.action.api.store;

 import com.enation.app.b2b2c.core.model.StoreDlyCenter;
 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreDlyCenterManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.stereotype.Component;











 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("dlyCenter")
 public class StoreDlyCenterApiAction
   extends WWAction
 {
   private IStoreDlyCenterManager storeDlyCenterManager;
   private IStoreMemberManager storeMemberManager;
   private Integer dly_center_id;
   private String name;
   private String uname;
   private String address;
   private String zip;
   private String phone;
   private String cellphone;

   public String add()
   {
     try
     {
       StoreDlyCenter dlyCenter = createDlyType();
       dlyCenter.setChoose("false");
       this.storeDlyCenterManager.addDlyCenter(dlyCenter);
       showSuccessJson("保存成功！");
     } catch (Exception e) {
       showErrorJson("保存失败！");
     }
     return "json_message";
   }





   public String edit()
   {
     try
     {
       StoreDlyCenter dlyCenter = createDlyType();
       dlyCenter.setDly_center_id(this.dly_center_id);
       this.storeDlyCenterManager.editDlyCenter(dlyCenter);
       showSuccessJson("保存成功！");
     } catch (Exception e) {
       showErrorJson("保存失败！");
     }
     return "json_message";
   }


















   private StoreDlyCenter createDlyType()
   {
     StoreMember member = this.storeMemberManager.getStoreMember();

     StoreDlyCenter dlyCenter = new StoreDlyCenter();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String province = request.getParameter("province");
     String city = request.getParameter("city");
     String region = request.getParameter("region");

     String province_id = request.getParameter("province_id");
     String city_id = request.getParameter("city_id");
     String region_id = request.getParameter("region_id");

     dlyCenter.setName(this.name);
     dlyCenter.setUname(this.uname);
     dlyCenter.setAddress(this.address);
     dlyCenter.setZip(this.zip);
     dlyCenter.setPhone(this.phone);
     dlyCenter.setCellphone(this.cellphone);
     dlyCenter.setProvince(province);
     dlyCenter.setCity(city);
     dlyCenter.setRegion(region);
     dlyCenter.setProvince_id(Integer.valueOf(Integer.parseInt(province_id)));
     dlyCenter.setCity_id(Integer.valueOf(Integer.parseInt(city_id)));
     dlyCenter.setRegion_id(Integer.valueOf(Integer.parseInt(region_id)));
     dlyCenter.setStore_id(member.getStore_id());

     return dlyCenter;
   }





   public String delete()
   {
     try
     {
       this.storeDlyCenterManager.delete(this.dly_center_id);
       showSuccessJson("删除成功！");
     } catch (Exception e) {
       showErrorJson("删除失败！");
     }
     return "json_message";
   }







   public String siteDefault()
   {
     StoreMember member = this.storeMemberManager.getStoreMember();
     try
     {
       this.storeDlyCenterManager.site_default(this.dly_center_id, member.getStore_id());
       showSuccessJson("删除成功！");
     } catch (Exception e) {
       showErrorJson("删除失败！");
     }
     return "json_message";
   }


   public IStoreDlyCenterManager getStoreDlyCenterManager()
   {
     return this.storeDlyCenterManager;
   }

   public void setStoreDlyCenterManager(IStoreDlyCenterManager storeDlyCenterManager)
   {
     this.storeDlyCenterManager = storeDlyCenterManager;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }

   public Integer getDly_center_id() {
     return this.dly_center_id;
   }

   public void setDly_center_id(Integer dly_center_id) {
     this.dly_center_id = dly_center_id;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public String getUname() {
     return this.uname;
   }

   public void setUname(String uname) {
     this.uname = uname;
   }

   public String getAddress() {
     return this.address;
   }

   public void setAddress(String address) {
     this.address = address;
   }

   public String getZip() {
     return this.zip;
   }

   public void setZip(String zip) {
     this.zip = zip;
   }

   public String getPhone() {
     return this.phone;
   }

   public void setPhone(String phone) {
     this.phone = phone;
   }

   public String getCellphone() {
     return this.cellphone;
   }

   public void setCellphone(String cellphone) {
     this.cellphone = cellphone;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\store\StoreDlyCenterApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */