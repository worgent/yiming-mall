 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.model.DlyType;
 import com.enation.app.shop.core.model.support.DlyTypeConfig;
 import com.enation.app.shop.core.model.support.TypeAreaConfig;
 import com.enation.app.shop.core.service.IAreaManager;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.app.shop.core.service.ILogiManager;
 import com.enation.framework.action.WWAction;
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
 @Action("dlyType")
 @Results({@org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/shop/admin/setting/dly_type_add.html"), @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/shop/admin/setting/dly_type_edit.html"), @org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/shop/admin/setting/dly_type_list.html")})
 public class DlyTypeAction
   extends WWAction
 {
   private Integer typeId;
   private Integer[] type_id;
   private List logiList;
   private DlyType type;
   private IDlyTypeManager dlyTypeManager;
   private ILogiManager logiManager;
   private IAreaManager areaManager;
   private Integer firstunit;
   private Integer continueunit;
   private Double[] firstprice;
   private Double[] continueprice;
   private String[] areaGroupName;
   private String[] areaGroupId;
   private Integer[] has_cod;
   private Integer[] useexp;
   private String[] expressions;
   private String exp;
   private Integer defAreaFee;
   private Boolean isEdit;
   private Integer areacount;
   private Integer arealistsize;

   public String add_type()
   {
     this.logiList = this.logiManager.list();
     return "add";
   }








   public String edit()
   {
     this.isEdit = Boolean.valueOf(true);
     this.type = this.dlyTypeManager.getDlyTypeById(this.typeId);
     this.arealistsize = Integer.valueOf(0);
     if (this.type.getTypeAreaList() != null) {
       this.arealistsize = Integer.valueOf(this.type.getTypeAreaList().size());
     }
     this.logiList = this.logiManager.list();
     return "edit";
   }




   public String list()
   {
     this.logiList = this.logiManager.list();
     return "list";
   }






   public String listJson()
   {
     this.webpage = this.dlyTypeManager.pageDlyType(getPage(), getPageSize());
     showGridJson(this.webpage);
     return "json_message";
   }






   public String saveAdd()
   {
     int tid = 0;
     try {
       if (this.type.getIs_same().intValue() == 1) {
         tid = saveSame(false).intValue();
       }
       if (this.type.getIs_same().intValue() == 0) {
         saveDiff(false);
       }
       showSuccessJson("配送方式添加成功");
     } catch (Exception e) {
       showErrorJson("配送方式添加失败");
       this.logger.error("配送方式添加失败", e);
     }
     return "json_message";
   }














   private Integer saveSame(boolean isUpdate)
   {
     DlyTypeConfig config = new DlyTypeConfig();
     config.setFirstunit(this.firstunit);
     config.setContinueunit(this.continueunit);

     config.setFirstprice(this.firstprice[0]);
     config.setContinueprice(this.continueprice[0]);
     if (this.useexp[0] == null) {
       this.useexp[0] = Integer.valueOf(0);
     }

     if (this.useexp[0].intValue() == 1) {
       config.setExpression(this.expressions[0]);
       config.setUseexp(Integer.valueOf(1));
     } else {
       config.setUseexp(Integer.valueOf(0));
     }

     this.type.setHas_cod(Integer.valueOf(0));
     config.setHave_cod(this.type.getHas_cod());
     int tid = 0;
     if (isUpdate) {
       this.dlyTypeManager.edit(this.type, config);
     } else {
       tid = this.dlyTypeManager.add(this.type, config).intValue();
     }

     return Integer.valueOf(tid);
   }











   private void saveDiff(boolean isUpdate)
   {
     DlyTypeConfig config = new DlyTypeConfig();

     config.setFirstunit(this.firstunit);
     config.setContinueunit(this.continueunit);
     config.setDefAreaFee(this.defAreaFee);


     if ((this.defAreaFee != null) && (this.defAreaFee.intValue() == 1)) {
       config.setFirstprice(this.firstprice[1]);
       config.setContinueprice(this.continueprice[1]);
       if (this.useexp[1] == null) {
         this.useexp[1] = Integer.valueOf(0);
       }
       if (this.useexp[1].intValue() == 1) {
         config.setExpression(this.expressions[1]);
         config.setUseexp(Integer.valueOf(1));
       } else {
         config.setUseexp(Integer.valueOf(0));
       }
     }

     if (this.areacount == null) {
       this.areacount = Integer.valueOf(0);
     }
     TypeAreaConfig[] configArray = new TypeAreaConfig[this.areacount.intValue() + 1];
     int price_index = 0;
     HttpServletRequest request = getRequest();

     for (int i = 1; i <= this.areacount.intValue(); i++) {
       if (request.getParameter("areas" + i) != null) {
         String totle_areas = request.getParameter("totle_areas" + i);
         String totle_regions = request.getParameter("totle_regions" + i);

         TypeAreaConfig areaConfig = new TypeAreaConfig();


         areaConfig.setContinueunit(config.getContinueunit());
         areaConfig.setFirstunit(config.getFirstunit());

         String[] areass = request.getParameterValues("areas" + i);
         areaConfig.setUseexp(Integer.valueOf(Integer.parseInt(request.getParameter("useexp" + i))));
         areaConfig.setAreaId(totle_areas + totle_regions);
         areaConfig.setAreaName(areass[0]);


         if (Integer.parseInt(request.getParameter("useexp" + i)) == 1) {
           areaConfig.setExpression(StringUtil.arrayToString(request.getParameterValues("expressions" + i), ","));
           areaConfig.setUseexp(Integer.valueOf(1));
         } else {
           String firstprice = StringUtil.arrayToString(request.getParameterValues("firstprice" + i), ",");
           String continueprice = StringUtil.arrayToString(request.getParameterValues("continueprice" + i), ",");
           areaConfig.setFirstprice(Double.valueOf(Double.parseDouble(firstprice)));
           areaConfig.setContinueprice(Double.valueOf(Double.parseDouble(continueprice)));
           config.setUseexp(Integer.valueOf(0));
         }

         configArray[i] = areaConfig;
       }
     }

     if (isUpdate) {
       this.dlyTypeManager.edit(this.type, config, configArray);
     } else {
       this.dlyTypeManager.add(this.type, config, configArray);
     }
   }






   public String saveEdit()
   {
     try
     {
       if (this.type.getIs_same().intValue() == 1) {
         saveSame(true);
       }

       if (this.type.getIs_same().intValue() == 0) {
         saveDiff(true);
       }

       showSuccessJson("配送方式修改成功");
     } catch (Exception e) {
       showErrorJson("配送方式修改失败");
       this.logger.error("配送方式修改失败", e);
     }

     return "json_message";
   }





   public String delete()
   {
     this.dlyTypeManager.delete(this.type_id);
     showSuccessJson("删除成功");
     return "json_message";
   }


   public IDlyTypeManager getDlyTypeManager()
   {
     return this.dlyTypeManager;
   }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
     this.dlyTypeManager = dlyTypeManager;
   }

   public DlyType getType() {
     return this.type;
   }

   public void setType(DlyType type) {
     this.type = type;
   }



   public List getLogiList()
   {
     return this.logiList;
   }

   public void setLogiList(List logiList) {
     this.logiList = logiList;
   }



   public ILogiManager getLogiManager()
   {
     return this.logiManager;
   }

   public void setLogiManager(ILogiManager logiManager) {
     this.logiManager = logiManager;
   }

   public Integer getFirstunit() {
     return this.firstunit;
   }

   public void setFirstunit(Integer firstunit) {
     this.firstunit = firstunit;
   }

   public Integer getContinueunit() {
     return this.continueunit;
   }

   public void setContinueunit(Integer continueunit) {
     this.continueunit = continueunit;
   }

   public Double[] getFirstprice() {
     return this.firstprice;
   }

   public void setFirstprice(Double[] firstprice) {
     this.firstprice = firstprice;
   }

   public Double[] getContinueprice() {
     return this.continueprice;
   }

   public void setContinueprice(Double[] continueprice) {
     this.continueprice = continueprice;
   }

   public Integer getDefAreaFee() {
     return this.defAreaFee;
   }

   public void setDefAreaFee(Integer defAreaFee) {
     this.defAreaFee = defAreaFee;
   }

   public String[] getAreaGroupName() {
     return this.areaGroupName;
   }

   public void setAreaGroupName(String[] areaGroupName) {
     this.areaGroupName = areaGroupName;
   }

   public String[] getAreaGroupId() {
     return this.areaGroupId;
   }

   public void setAreaGroupId(String[] areaGroupId) {
     this.areaGroupId = areaGroupId;
   }

   public Integer[] getUseexp() {
     return this.useexp;
   }

   public void setUseexp(Integer[] useexp) {
     this.useexp = useexp;
   }

   public String[] getExpressions() {
     return this.expressions;
   }

   public void setExpressions(String[] expressions) {
     this.expressions = expressions;
   }

   public Integer[] getHas_cod() {
     return this.has_cod;
   }

   public void setHas_cod(Integer[] hasCod) {
     this.has_cod = hasCod;
   }

   public Boolean getIsEdit() {
     return this.isEdit;
   }

   public void setIsEdit(Boolean isEdit) {
     this.isEdit = isEdit;
   }

   public String getExp() {
     return this.exp;
   }

   public void setExp(String exp) {
     this.exp = exp;
   }

   public Integer getTypeId() {
     return this.typeId;
   }

   public void setTypeId(Integer typeId) {
     this.typeId = typeId;
   }

   public Integer[] getType_id() {
     return this.type_id;
   }

   public void setType_id(Integer[] type_id) {
     this.type_id = type_id;
   }

   public Integer getAreacount() {
     return this.areacount;
   }

   public void setAreacount(Integer areacount) {
     this.areacount = areacount;
   }

   public Integer getArealistsize() {
     return this.arealistsize;
   }

   public void setArealistsize(Integer arealistsize) {
     this.arealistsize = arealistsize;
   }

   public IAreaManager getAreaManager() {
     return this.areaManager;
   }

   public void setAreaManager(IAreaManager areaManager) {
     this.areaManager = areaManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\DlyTypeAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */