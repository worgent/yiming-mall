 package com.enation.app.base.core.action.api;

 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.framework.action.WWAction;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.ParentPackage;

 @ParentPackage("eop_default")
 @org.apache.struts2.convention.annotation.Namespace("/core/admin")
 @Action("data")
 public class DataApiAction extends WWAction
 {
   private String tb;

   public String export()
   {
     try
     {
       String[] tables = { this.tb };
       this.json = DBSolutionFactory.dbExport(tables, false, "");
     } catch (Exception e) {
       e.printStackTrace();
       showErrorJson("导出失败");
     }


     return "json_message";
   }

   public String getTb() { return this.tb; }

   public void setTb(String tb) {
     this.tb = tb;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\api\DataApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */