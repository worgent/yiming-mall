 package com.enation.app.base.core.action.api;

 import com.enation.app.base.core.service.IRegionsManager;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import net.sf.json.JSONArray;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;










 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/base")
 @Action("region")
 public class RegionApiAction
   extends WWAction
 {
   private IRegionsManager regionsManager;
   private Integer regionid;

   public String getChildren()
   {
     if (this.regionid == null) {
       showErrorJson("缺少参数：regionid");
     } else {
       List list = this.regionsManager.listChildrenByid(this.regionid);
       this.json = JSONArray.fromObject(list).toString();
     }
     return "json_message";
   }

   public IRegionsManager getRegionsManager() {
     return this.regionsManager;
   }

   public void setRegionsManager(IRegionsManager regionsManager) { this.regionsManager = regionsManager; }

   public Integer getRegionid() {
     return this.regionid;
   }

   public void setRegionid(Integer regionid) { this.regionid = regionid; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\api\RegionApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */