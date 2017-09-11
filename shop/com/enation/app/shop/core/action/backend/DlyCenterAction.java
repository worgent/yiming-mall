 package com.enation.app.shop.core.action.backend;

 import com.enation.app.base.core.service.IRegionsManager;
 import com.enation.app.shop.core.model.DlyCenter;
 import com.enation.app.shop.core.service.IDlyCenterManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;


















 @Component
 @Scope("prototype")
 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Action("dlyCenter")
 @Results({@org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/shop/admin/dlyCenter/edit.html"), @org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/shop/admin/dlyCenter/add.html"), @org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/shop/admin/dlyCenter/list.html")})
 public class DlyCenterAction
   extends WWAction
 {
   private IDlyCenterManager dlyCenterManager;
   private IRegionsManager regionsManager;
   private DlyCenter dlyCenter;
   private Integer dlyCenterId;
   private Integer[] dly_center_id;
   private List<DlyCenter> list;
   private List provinceList;
   private List cityList;
   private List regionList;

   public String add()
   {
     this.provinceList = this.regionsManager.listProvince();
     return "add";
   }







   public String edit()
   {
     this.dlyCenter = this.dlyCenterManager.get(this.dlyCenterId);
     this.provinceList = this.regionsManager.listProvince();
     if (this.dlyCenter.getProvince_id() != null) {
       this.cityList = this.regionsManager.listCity(this.dlyCenter.getProvince_id().intValue());
     }
     if (this.dlyCenter.getCity_id() != null) {
       this.regionList = this.regionsManager.listRegion(this.dlyCenter.getCity_id().intValue());
     }
     return "edit";
   }



   public String list()
   {
     return "list";
   }




   public String listJson()
   {
     this.list = this.dlyCenterManager.list();
     showGridJson(this.list);
     return "json_message";
   }




   public String delete()
   {
     try
     {
       this.dlyCenterManager.delete(this.dly_center_id);
       showSuccessJson("发货信息删除成功");
     } catch (RuntimeException e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug(e);
       }
       showErrorJson("发货信息删除失败" + e.getMessage());
     }

     return "json_message";
   }














   public String saveAdd()
   {
     try
     {
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       String province = request.getParameter("province");
       String city = request.getParameter("city");
       String region = request.getParameter("region");

       String province_id = request.getParameter("province_id");
       String city_id = request.getParameter("city_id");
       String region_id = request.getParameter("region_id");


       this.dlyCenter.setProvince(province);
       this.dlyCenter.setCity(city);
       this.dlyCenter.setRegion(region);

       if (!StringUtil.isEmpty(province_id)) {
         this.dlyCenter.setProvince_id(Integer.valueOf(StringUtil.toInt(province_id, true)));
       }

       if (!StringUtil.isEmpty(city_id)) {
         this.dlyCenter.setCity_id(Integer.valueOf(StringUtil.toInt(city_id, true)));
       }

       if (!StringUtil.isEmpty(province_id)) {
         this.dlyCenter.setRegion_id(Integer.valueOf(StringUtil.toInt(region_id, true)));
       }

       this.dlyCenterManager.add(this.dlyCenter);
       showSuccessJson("发货信息添加成功");
     }
     catch (Exception e) {
       e.printStackTrace();
       showErrorJson("发货信息添加失败");
       this.logger.error("发货信息添加失败", e);
     }
     return "json_message";
   }











   public String saveEdit()
   {
     try
     {
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       String province = request.getParameter("province");
       String city = request.getParameter("city");
       String region = request.getParameter("region");

       String province_id = request.getParameter("province_id");
       String city_id = request.getParameter("city_id");
       String region_id = request.getParameter("region_id");


       this.dlyCenter.setProvince(province);
       this.dlyCenter.setCity(city);
       this.dlyCenter.setRegion(region);

       if (!StringUtil.isEmpty(province_id)) {
         this.dlyCenter.setProvince_id(Integer.valueOf(StringUtil.toInt(province_id, true)));
       }

       if (!StringUtil.isEmpty(city_id)) {
         this.dlyCenter.setCity_id(Integer.valueOf(StringUtil.toInt(city_id, true)));
       }

       if (!StringUtil.isEmpty(province_id)) {
         this.dlyCenter.setRegion_id(Integer.valueOf(StringUtil.toInt(region_id, true)));
       }

       this.dlyCenterManager.edit(this.dlyCenter);
       showSuccessJson("发货信息修改成功");
     }
     catch (Exception e) {
       e.printStackTrace();
       showSuccessJson("发货信息修改失败");
       this.logger.error("发货信息修改失败", e);
     }
     return "json_message";
   }

   public IDlyCenterManager getDlyCenterManager() {
     return this.dlyCenterManager;
   }

   public void setDlyCenterManager(IDlyCenterManager dlyCenterManager) {
     this.dlyCenterManager = dlyCenterManager;
   }

   public DlyCenter getDlyCenter() {
     return this.dlyCenter;
   }

   public void setDlyCenter(DlyCenter dlyCenter) {
     this.dlyCenter = dlyCenter;
   }

   public Integer getDlyCenterId() {
     return this.dlyCenterId;
   }

   public void setDlyCenterId(Integer dlyCenterId) {
     this.dlyCenterId = dlyCenterId;
   }

   public Integer[] getDly_center_id() {
     return this.dly_center_id;
   }

   public void setDly_center_id(Integer[] dly_center_id) {
     this.dly_center_id = dly_center_id;
   }

   public List<DlyCenter> getList() {
     return this.list;
   }

   public void setList(List<DlyCenter> list) {
     this.list = list;
   }

   public IRegionsManager getRegionsManager() {
     return this.regionsManager;
   }

   public void setRegionsManager(IRegionsManager regionsManager) {
     this.regionsManager = regionsManager;
   }

   public List getProvinceList() {
     return this.provinceList;
   }

   public void setProvinceList(List provinceList) {
     this.provinceList = provinceList;
   }

   public List getCityList() {
     return this.cityList;
   }

   public void setCityList(List cityList) {
     this.cityList = cityList;
   }

   public List getRegionList() {
     return this.regionList;
   }

   public void setRegionList(List regionList) {
     this.regionList = regionList;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\DlyCenterAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */