 package com.enation.app.b2b2c.core.action.api.store;

 import com.enation.app.b2b2c.core.model.StoreDlyType;
 import com.enation.app.b2b2c.core.model.StoreTemlplate;
 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreDlyTypeManager;
 import com.enation.app.b2b2c.core.service.IStoreTemplateManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.shop.core.model.support.DlyTypeConfig;
 import com.enation.app.shop.core.model.support.TypeAreaConfig;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.stereotype.Component;



















 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("dlyType")
 @Results({@org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/themes/default/b2b2c/storesite/navication_edit.html")})
 public class StoreDlyTypeApiAction
   extends WWAction
 {
   private IStoreDlyTypeManager storeDlyTypeManager;
   private IStoreTemplateManager storeTemplateManager;
   private IStoreMemberManager storeMemberManager;
   private StoreDlyType storeDlyType;
   private DlyTypeConfig typeConfig;
   private Integer pycount;
   private Integer kdcount;
   private Integer yzcount;
   private String dlyname;
   private Integer tempid;

   public String add()
   {
     StoreMember storeMember = this.storeMemberManager.getStoreMember();
     Integer store_id = storeMember.getStore_id();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String[] tplType = request.getParameterValues("tplType");

     StoreTemlplate storeTemlplate = new StoreTemlplate();
     storeTemlplate.setName(this.dlyname);
     storeTemlplate.setStore_id(storeMember.getStore_id());
     storeTemlplate.setDef_temp(Integer.valueOf(0));

     Integer templateid = this.storeTemplateManager.add(storeTemlplate);

     for (String tpl : tplType) {
       this.storeDlyType = new StoreDlyType();
       this.storeDlyType.setStore_id(store_id);
       this.storeDlyType.setTemplate_id(templateid);
       this.storeDlyType.setIs_same(Integer.valueOf(0));


       if (Integer.valueOf(tpl).intValue() == 1) {
         this.storeDlyType.setName("平邮");
         addType(request, "py", this.pycount);
       }


       if (Integer.valueOf(tpl).intValue() == 2) {
         this.storeDlyType.setName("快递");
         addType(request, "kd", this.kdcount);
       }


       if (Integer.valueOf(tpl).intValue() == 3) {
         this.storeDlyType.setName("邮政");
         addType(request, "yz", this.yzcount);
       }
     }


     Integer temp_id = this.storeTemplateManager.getDefTempid(storeMember.getStore_id());
     if (temp_id == null) {
       this.storeTemplateManager.setDefTemp(templateid, storeMember.getStore_id());
     }

     showSuccessJson("添加成功！");
     return "json_message";
   }












   public String update()
   {
     StoreMember storeMember = this.storeMemberManager.getStoreMember();
     Integer store_id = storeMember.getStore_id();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String[] tplType = request.getParameterValues("tplType");

     StoreTemlplate storeTemlplate = new StoreTemlplate();
     storeTemlplate.setStore_id(storeMember.getStore_id());
     storeTemlplate.setName(this.dlyname);
     storeTemlplate.setId(this.tempid);
     storeTemlplate.setDef_temp(Integer.valueOf(0));
     this.storeTemplateManager.edit(storeTemlplate);
     Integer templateid = this.tempid;
     this.storeDlyTypeManager.del_dlyType(templateid);

     for (String tpl : tplType) {
       this.storeDlyType = new StoreDlyType();
       this.storeDlyType.setStore_id(store_id);
       this.storeDlyType.setTemplate_id(templateid);


       if (Integer.valueOf(tpl).intValue() == 1) {
         this.storeDlyType.setName("平邮");
         addType(request, "py", this.pycount);
       }


       if (Integer.valueOf(tpl).intValue() == 2) {
         this.storeDlyType.setName("快递");
         addType(request, "kd", this.kdcount);
       }


       if (Integer.valueOf(tpl).intValue() == 3) {
         this.storeDlyType.setName("邮政");
         addType(request, "yz", this.yzcount);
       }
     }
     showSuccessJson("修改成功");
     return "json_message";
   }














   private void addType(HttpServletRequest request, String tpl, Integer count)
   {
     String firstunit = request.getParameter("default_firstunit_" + tpl);
     String continueunit = request.getParameter("default_continueunit_" + tpl);
     String firstmoney = request.getParameter("default_firstmoney_" + tpl);
     String continuemoney = request.getParameter("default_continueprice_" + tpl);

     DlyTypeConfig config = new DlyTypeConfig();
     config.setFirstunit(Integer.valueOf(firstunit));
     config.setContinueunit(Integer.valueOf(continueunit == null ? "0" : continueunit));


     config.setFirstprice(Double.valueOf(firstmoney));
     config.setContinueprice(Double.valueOf(continuemoney));

     config.setIs_same(Integer.valueOf(0));
     config.setDefAreaFee(Integer.valueOf(1));
     config.setUseexp(Integer.valueOf(0));

     TypeAreaConfig[] configArray = new TypeAreaConfig[count.intValue()];


     for (int i = 1; i <= count.intValue(); i++) {
       TypeAreaConfig areaConfig = new TypeAreaConfig();
       String firstprice = request.getParameter("express_" + tpl + "_firstmoney_" + i);
       String continueprice = request.getParameter("express_" + tpl + "_continuemoney_" + i);
       String areaids = request.getParameter("express_" + tpl + "_areaids_" + i);
       String areanames = request.getParameter("express_" + tpl + "_areanames_" + i);

       if ((firstprice != null) && (continueprice != null) && (areaids != null) && (areanames != null))
       {
         areaConfig.setFirstprice(Double.valueOf(firstprice));
         areaConfig.setFirstunit(Integer.valueOf(firstunit));

         areaConfig.setContinueprice(Double.valueOf(continueprice));
         areaConfig.setContinueunit(Integer.valueOf(continueunit == null ? "0" : continueunit));
         areaConfig.setUseexp(Integer.valueOf(0));

         if (areaids != null) {
           areaConfig.setAreaId(areaids.substring(0, areaids.length() - 1));
         }

         if (areanames != null) {
           areaConfig.setAreaName(areanames.substring(0, areanames.length() - 1));
         }

         configArray[(i - 1)] = areaConfig;
       }
     }
     this.storeDlyTypeManager.add(this.storeDlyType, config, configArray);
   }

   public IStoreDlyTypeManager getStoreDlyTypeManager()
   {
     return this.storeDlyTypeManager;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }

   public void setStoreDlyTypeManager(IStoreDlyTypeManager storeDlyTypeManager) {
     this.storeDlyTypeManager = storeDlyTypeManager;
   }

   public StoreDlyType getStoreDlyType()
   {
     return this.storeDlyType;
   }

   public void setStoreDlyType(StoreDlyType storeDlyType) {
     this.storeDlyType = storeDlyType;
   }

   public DlyTypeConfig getTypeConfig() {
     return this.typeConfig;
   }

   public void setTypeConfig(DlyTypeConfig typeConfig) {
     this.typeConfig = typeConfig;
   }

   public Integer getPycount() {
     return this.pycount;
   }

   public void setPycount(Integer pycount) {
     this.pycount = pycount;
   }

   public Integer getKdcount() {
     return this.kdcount;
   }

   public void setKdcount(Integer kdcount) {
     this.kdcount = kdcount;
   }

   public Integer getYzcount() {
     return this.yzcount;
   }

   public void setYzcount(Integer yzcount) {
     this.yzcount = yzcount;
   }

   public String getDlyname()
   {
     return this.dlyname;
   }

   public void setDlyname(String dlyname) {
     this.dlyname = dlyname;
   }

   public IStoreTemplateManager getStoreTemplateManager() {
     return this.storeTemplateManager;
   }

   public void setStoreTemplateManager(IStoreTemplateManager storeTemplateManager) {
     this.storeTemplateManager = storeTemplateManager;
   }

   public Integer getTempid() {
     return this.tempid;
   }

   public void setTempid(Integer tempid) {
     this.tempid = tempid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\store\StoreDlyTypeApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */