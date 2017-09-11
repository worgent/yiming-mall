 package com.enation.app.base.core.action;

 import com.enation.eop.resource.IDataLogManager;

 public class DataLogAction extends com.enation.framework.action.WWAction
 {
   private IDataLogManager dataLogManager;
   private String start;
   private String end;
   private Integer[] ids;

   public String list()
   {
     this.webpage = this.dataLogManager.list(this.start, this.end, getPage(), getPageSize());
     return "list";
   }

   public String delete() {
     try {
       this.dataLogManager.delete(this.ids);
       this.json = "{result:0,message:'删除成功'}";
     } catch (RuntimeException e) {
       this.json = ("{result:1,message:'" + e.getMessage() + "'}");
     }
     return "json_message";
   }

   public IDataLogManager getDataLogManager() {
     return this.dataLogManager;
   }

   public void setDataLogManager(IDataLogManager dataLogManager) {
     this.dataLogManager = dataLogManager;
   }

   public String getStart() {
     return this.start;
   }

   public void setStart(String start) {
     this.start = start;
   }

   public String getEnd() {
     return this.end;
   }

   public void setEnd(String end) {
     this.end = end;
   }

   public Integer[] getIds() {
     return this.ids;
   }

   public void setIds(Integer[] ids) {
     this.ids = ids;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\DataLogAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */