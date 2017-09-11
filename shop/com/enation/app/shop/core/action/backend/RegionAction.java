 package com.enation.app.shop.core.action.backend;

 import com.enation.app.base.core.model.Regions;
 import com.enation.app.base.core.service.IRegionsManager;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import net.sf.json.JSONArray;
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
 @Action("region")
 @Results({@org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/shop/admin/regions/regions.html"), @org.apache.struts2.convention.annotation.Result(name="listChildren", type="freemarker", location="/shop/admin/regions/region_Panel.html"), @org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/shop/admin/regions/region_add.html"), @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/shop/admin/regions/region_edit.html"), @org.apache.struts2.convention.annotation.Result(name="children", type="freemarker", location="/shop/admin/regions/region_children.html")})
 public class RegionAction
   extends WWAction
 {
   private IRegionsManager regionsManager;
   private List listRegion;
   private Integer parentid;
   private Regions regions;
   private Integer region_id;
   private Integer regiongrade;
   private Integer regionid;

   public String list()
   {
     return "list";
   }

   public String listChildren() {
     this.listRegion = this.regionsManager.listChildren(Integer.valueOf(0));
     this.json = JSONArray.fromObject(this.listRegion).toString();
     return "json_message";
   }

   public String add() {
     return "add";
   }

   public String saveAdd() {
     try {
       this.regionsManager.add(this.regions);
       showSuccessJson("地区添加成功");
     } catch (Exception e) {
       e.printStackTrace();
       showErrorJson("地区添加失败");
     }
     return "json_message";
   }

   public String saveAddchildren() {
     try {
       this.regionsManager.add(this.regions);
       showSuccessJson("子地区添加成功");
     } catch (Exception e) {
       e.printStackTrace();
       showErrorJson("子地区添加失败");
     }
     return "json_message";
   }

   public String edit() {
     this.regions = this.regionsManager.get(this.region_id.intValue());
     return "edit";
   }

   public String children() {
     this.regions = this.regionsManager.get(this.region_id.intValue());
     return "children";
   }

   public String saveEdit() {
     try {
       this.regionsManager.update(this.regions);
       showSuccessJson("修改成功");
     }
     catch (Exception e) {
       e.printStackTrace();
       showErrorJson("修改失败");
     }
     return "json_message";
   }

   public String delete() {
     try {
       this.regionsManager.delete(this.region_id.intValue());
       showSuccessJson("删除成功");
     } catch (RuntimeException e) {
       showErrorJson("删除失败");
       e.printStackTrace();
     }
     return "json_message";
   }

   public String reset() {
     try {
       this.regionsManager.reset();
       showSuccessJson("初始化地区成功");
     } catch (RuntimeException e) {
       showErrorJson("初始化地区失败");
       e.printStackTrace();
     }
     return "json_message";
   }

   public String getChildren()
   {
     List list = this.regionsManager.listChildrenByid(this.regionid);
     this.json = JSONArray.fromObject(list).toString();
     return "json_message";
   }

   public IRegionsManager getRegionsManager() {
     return this.regionsManager;
   }

   public void setRegionsManager(IRegionsManager regionsManager) {
     this.regionsManager = regionsManager;
   }

   public List getListRegion() {
     return this.listRegion;
   }

   public void setListRegion(List listRegion) {
     this.listRegion = listRegion;
   }

   public Integer getParentid() {
     return this.parentid;
   }

   public void setParentid(Integer parentid) {
     this.parentid = parentid;
   }

   public Regions getRegions() {
     return this.regions;
   }

   public void setRegions(Regions regions) {
     this.regions = regions;
   }

   public Integer getRegion_id() {
     return this.region_id;
   }

   public void setRegion_id(Integer regionId) {
     this.region_id = regionId;
   }

   public Integer getRegiongrade() {
     return this.regiongrade;
   }

   public void setRegiongrade(Integer regiongrade) {
     this.regiongrade = regiongrade;
   }

   public Integer getRegionid() {
     return this.regionid;
   }

   public void setRegionid(Integer regionid) {
     this.regionid = regionid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\RegionAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */