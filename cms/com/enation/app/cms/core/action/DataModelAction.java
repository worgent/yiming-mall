 package com.enation.app.cms.core.action;

 import com.enation.app.cms.core.model.DataModel;
 import com.enation.app.cms.core.service.IDataFieldManager;
 import com.enation.app.cms.core.service.IDataModelManager;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import org.apache.log4j.Logger;




 public class DataModelAction
   extends WWAction
 {
   private IDataModelManager dataModelManager;
   private IDataFieldManager dataFieldManager;
   private Integer modelid;
   private DataModel dataModel;
   private List modelList;
   private List fieldList;

   public String list()
   {
     return "list";
   }

   public String listJson() {
     this.modelList = this.dataModelManager.list();
     showGridJson(this.modelList);
     return "json_message";
   }

   public String add() {
     return "add";
   }

   public String edit() {
     this.dataModel = this.dataModelManager.get(this.modelid);
     this.fieldList = this.dataFieldManager.list(this.modelid);
     return "edit";
   }

   public String fileListJson() { this.fieldList = this.dataFieldManager.list(this.modelid);
     showGridJson(this.fieldList);
     return "json_message";
   }

   public String saveAdd()
   {
     try {
       this.dataModelManager.add(this.dataModel);
       showSuccessJson("模型添加成功");
     } catch (RuntimeException e) {
       showErrorJson("模型添加出现错误");
     }
     return "json_message";
   }

   public String saveEdit() {
     try {
       this.dataModelManager.edit(this.dataModel);
       showSuccessJson("模型修改成功");
     } catch (RuntimeException e) {
       showErrorJson("模型修改出现错误");
     }
     return "json_message";
   }

   public String check() {
     int result = this.dataModelManager.checkIfModelInUse(this.modelid);
     if (result > 0) {
       showErrorJson("模型已经被使用，请先删除对应的数据！");
     } else {
       try {
         delete(this.modelid);
         showSuccessJson("删除成功");
       } catch (Exception e) {
         e.printStackTrace();
         showErrorJson("删除失败");
       }
     }
     return "json_message";
   }

   private void delete(Integer modelid)
   {
     try {
       this.dataModelManager.delete(modelid);
     } catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e);
     }
   }

   public IDataModelManager getDataModelManager()
   {
     return this.dataModelManager;
   }

   public void setDataModelManager(IDataModelManager dataModelManager) {
     this.dataModelManager = dataModelManager;
   }

   public IDataFieldManager getDataFieldManager() {
     return this.dataFieldManager;
   }

   public void setDataFieldManager(IDataFieldManager dataFieldManager) {
     this.dataFieldManager = dataFieldManager;
   }

   public Integer getModelid() {
     return this.modelid;
   }

   public void setModelid(Integer modelid) {
     this.modelid = modelid;
   }

   public DataModel getDataModel() {
     return this.dataModel;
   }

   public void setDataModel(DataModel dataModel) {
     this.dataModel = dataModel;
   }

   public List getModelList() {
     return this.modelList;
   }

   public void setModelList(List modelList) {
     this.modelList = modelList;
   }

   public List getFieldList() {
     return this.fieldList;
   }

   public void setFieldList(List fieldList) {
     this.fieldList = fieldList;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\action\DataModelAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */