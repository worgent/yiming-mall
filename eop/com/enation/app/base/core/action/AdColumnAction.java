 package com.enation.app.base.core.action;

 import com.enation.app.base.core.model.AdColumn;
 import com.enation.app.base.core.service.IAdColumnManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;






 public class AdColumnAction
   extends WWAction
 {
   private IAdColumnManager adColumnManager;
   private AdColumn adColumn;
   private Long ac_id;
   private Integer[] acid;

   public String list() { return "list"; }

   public String listJson() {
     this.webpage = this.adColumnManager.pageAdvPos(getPage(), getPageSize());
     showGridJson(this.webpage);
     return "json_message";
   }

   public String detail() {
     this.adColumn = this.adColumnManager.getADcolumnDetail(this.ac_id);
     return "detail";
   }

   public String delete()
   {
     if (EopSetting.IS_DEMO_SITE) {
       for (Integer id : this.acid) {
         if (id.intValue() <= 21) {
           showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
           return "json_message";
         }
       }
     }
     try
     {
       this.adColumnManager.delAdcs(this.acid);
       showSuccessJson("删除成功");
     } catch (RuntimeException e) {
       showErrorJson("删除失败" + e.getMessage());
     }
     return "json_message";
   }

   public String add() {
     return "add";
   }

   public String addSave() {
     this.adColumnManager.addAdvc(this.adColumn);
     showSuccessJson("广告位添加成功");
     return "json_message";
   }

   public String edit() {
     this.adColumn = this.adColumnManager.getADcolumnDetail(this.ac_id);
     return "edit";
   }

   public String editSave() {
     this.adColumnManager.updateAdvc(this.adColumn);
     showSuccessJson("修改广告位成功");
     return "json_message";
   }

   public IAdColumnManager getAdColumnManager() {
     return this.adColumnManager;
   }

   public void setAdColumnManager(IAdColumnManager adColumnManager) {
     this.adColumnManager = adColumnManager;
   }

   public AdColumn getAdColumn() {
     return this.adColumn;
   }

   public void setAdColumn(AdColumn adColumn) {
     this.adColumn = adColumn;
   }


   public Long getAc_id()
   {
     return this.ac_id;
   }

   public void setAc_id(Long ac_id) { this.ac_id = ac_id; }

   public Integer[] getAcid() {
     return this.acid;
   }

   public void setAcid(Integer[] acid) { this.acid = acid; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\AdColumnAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */